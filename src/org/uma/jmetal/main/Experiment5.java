package org.uma.jmetal.main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.builder.DynamicNSGAIIBuilder;
import org.uma.jmetal.algorithm.components.impl.archiving.OutputArchivingProxy;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationTwoPointsCrossover;
import org.uma.jmetal.algorithm.components.impl.replacement.OutputReplacementProxy;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.experiment.impl.AlgorithmRunner;
import org.uma.jmetal.grammaticalevolution.mapper.impl.GeneticAlgorithmExpressionMapper;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.solution.PermutationSolution;

public class Experiment5 {

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            int executions = 30;
            String outputDir = args[0];
            new File(outputDir).mkdirs();
            ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[1]));
            //problem instance
            String[] problems = {"kroA100.tsp", "kroA150.tsp", "kroA200.tsp"};
            for (String problem : problems) {
                MultiobjectiveTSP tsp = new MultiobjectiveTSP(problem, problem.replaceAll("A", "B"));

                //build and run nsgaii
                DynamicNSGAIIBuilder builder = new DynamicNSGAIIBuilder(tsp, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation(0.05));
                builder.setMaxEvaluations(60000).setPopulationSize(100);

                for (int i = 0; i < executions; i++) {
                    DynamicNSGAII nsgaii = builder.build();
                    new File(outputDir + "/NSGAII/EXECUTION_" + i).mkdirs();
                    nsgaii.setReplacementImplementation(new OutputReplacementProxy(nsgaii.getReplacementImplementation(), outputDir + "/NSGAII/EXECUTION_" + i));
                    AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(nsgaii, outputDir + "/NSGAII/EXECUTION_" + i, String.valueOf(i));
                    threadPool.submit(runner);
                }

                //load grammar
                GeneticAlgorithmExpressionMapper mapper = new GeneticAlgorithmExpressionMapper(2, 5);
                mapper.loadGrammar("grammar.bnf");

                //read experiment files
                List<String> vars = new ArrayList<>();
                File directory = new File("experiment/experiment/");
                for (File file : directory.listFiles()) {
                    if (file.getName().startsWith("VAR") && !file.getName().contains("NSGA-II")) {
                        vars.add(file.getPath());
                    }
                }

                //build and run created algorithms
                for (String var : vars) {
                    List<Integer> grammarInstance = new ArrayList<>();
                    String split[] = var.split("_");
                    Scanner scanner = new Scanner(new FileReader(var));
                    while (scanner.hasNextInt()) {
                        grammarInstance.add(scanner.nextInt());
                    }
                    //print solutions
                    {
                        AbstractDynamicGeneticAlgorithm algorithm = mapper.interpret(grammarInstance);
                        new File(outputDir + "/ALG_" + split[1].charAt(0)).mkdirs();
                        try (FileWriter writer = new FileWriter(outputDir + "/ALG_" + split[1].charAt(0) + "/COMPONENTS.txt")) {
                            writer.write(algorithm.toString());
                        }
                    }

                    for (int i = 0; i < executions; i++) {
                        AbstractDynamicGeneticAlgorithm algorithm = mapper.interpret(grammarInstance);
                        algorithm.setArchivingImplementation(new OutputArchivingProxy(algorithm.getArchivingImplementation(), outputDir + "/ALG_" + split[1].charAt(0) + "/EXECUTION_" + i));
                        algorithm.setProblem(tsp);
                        algorithm.getStoppingConditionImplementation().setStoppingCondition(60000);
                        new File(outputDir + "/ALG_" + split[1].charAt(0) + "/EXECUTION_" + i).mkdirs();
                        AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(algorithm, outputDir + "/ALG_" + split[1].charAt(0) + "/EXECUTION_" + i, String.valueOf(i));
                        threadPool.submit(runner);
                    }
                }
            }
            threadPool.shutdown();
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (IOException ex) {
            Logger.getLogger(ExperimentInitialExecution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
