package org.uma.jmetal.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.builder.DynamicNSGAIIBuilder;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.algorithm.impl.GAGenerationGrammaticalEvolution;
import org.uma.jmetal.measure.HypervolumeCalculator;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationTwoPointsCrossover;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.SinglePointCrossoverVariableLength;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.DuplicationMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.IntegerMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.PruneMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.impl.GAGenerationProblem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

public class ExperimentInitialExecution {

    public static void main(String[] args) throws InterruptedException {
        try {
            MultiobjectiveTSP tsp = new MultiobjectiveTSP("kroA100.tsp", "kroB100.tsp");
            GAGenerationProblem geProblem
                    = new GAGenerationProblem(
                            2000,
                            tsp,
                            5,
                            10,
                            20,
                            "grammar.bnf");

            ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[0]));

            List<GAGenerationGrammaticalEvolution> algorithms = new ArrayList<>();
            for (int execution = 0; execution < 30; execution++) {
                GAGenerationGrammaticalEvolution geAlgorithm
                        = new GAGenerationGrammaticalEvolution(
                                geProblem,
                                60000,
                                100,
                                new SinglePointCrossoverVariableLength(0.9),
                                new IntegerMutation(0.01),
                                new BinaryTournamentSelection(),
                                new PruneMutation(0.01, 5),
                                new DuplicationMutation(0.01),
                                new SequentialSolutionListEvaluator<>());
                algorithms.add(geAlgorithm);
                threadPool.submit(geAlgorithm);
            }

            threadPool.shutdown();
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            DynamicNSGAIIBuilder builder = new DynamicNSGAIIBuilder(tsp, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation(0.05));
            builder.setMaxEvaluations(2000).setPopulationSize(100);
            DynamicNSGAII nsgaii = builder.build();
            nsgaii.run();

            HypervolumeCalculator calculator = new HypervolumeCalculator();
            for (int execution = 0; execution < algorithms.size(); execution++) {
                VariableIntegerSolution solution = algorithms.get(execution).getResult();
                calculator.addParetoFront((List<? extends Solution<?>>) solution.getAttribute("Result"));
            }
            calculator.addParetoFront(nsgaii.getResult());

            SolutionSetOutput.printObjectivesToFile(nsgaii.getResult(), "experiment/NSGAII_FUN.txt");

            for (int execution = 0; execution < algorithms.size(); execution++) {
                VariableIntegerSolution solution = algorithms.get(execution).getResult();
                List<? extends Solution<?>> result = (List<? extends Solution<?>>) solution.getAttribute("Result");
                SolutionSetOutput.printObjectivesToFile(result, "experiment/alg" + execution + "_FUN.txt");
                try (FileWriter writer = new FileWriter("experiment/alg" + execution + "_DESC.txt")) {
                    writer.append("Hypervolume: " + calculator.calculateHypervolume(result) + "\n");
                    writer.append(solution.getAttribute("Algorithm").toString());
                }
            }
            try (FileWriter writer = new FileWriter("experiment/NSGAII_DESC.txt")) {
                writer.append("Hypervolume: " + calculator.calculateHypervolume(nsgaii.getResult()) + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(ExperimentInitialExecution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
