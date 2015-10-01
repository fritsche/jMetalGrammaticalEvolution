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
            //ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[0]));
            ExecutorService threadPool = Executors.newFixedThreadPool(1);
            //problem instance
            MultiobjectiveTSP tsp = new MultiobjectiveTSP("kroA100.tsp", "kroB100.tsp");

            //build and run nsgaii
            new File("results/NSGAII").mkdirs();
            DynamicNSGAIIBuilder builder = new DynamicNSGAIIBuilder(tsp, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation(0.05));
            builder.setMaxEvaluations(60000).setPopulationSize(100);

            for (int i = 0; i < executions; i++) {
                DynamicNSGAII nsgaii = builder.build();
                nsgaii.setReplacementImplementation(new OutputReplacementProxy(nsgaii.getReplacementImplementation(), "results/NSGAII"));
                AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(nsgaii, "results/NSGAII", String.valueOf(i));
                threadPool.submit(runner);
            }

            //load grammar
            GeneticAlgorithmExpressionMapper mapper = new GeneticAlgorithmExpressionMapper(2, 5);
            mapper.loadGrammar("grammar.bnf");

            //read experiment files
            List<String> vars = new ArrayList<>();
            File directory = new File("experiment/");
            for (File file : directory.listFiles()) {
                if (file.getName().startsWith("VAR") && !file.getName().contains("NSGA-II")) {
                    vars.add(file.getPath());
                }
            }

            //build and run created algorithms
            for (String var : vars) {
                List<Integer> grammarInstance = new ArrayList<>();
                String split[] = var.split("_");
                new File("results/ALG" + split[1].charAt(0)).mkdir();
                Scanner scanner = new Scanner(new FileReader(var));
                while (scanner.hasNextInt()) {
                    grammarInstance.add(scanner.nextInt());
                }
                //print solutions
                {
                    AbstractDynamicGeneticAlgorithm algorithm = mapper.interpret(grammarInstance);
                    try (FileWriter writer = new FileWriter("results/ALG" + split[1].charAt(0) + "/COMPONENTS")) {
                        writer.write(algorithm.toString());
                    }
                }

                for (int i = 0; i < executions; i++) {
                    AbstractDynamicGeneticAlgorithm algorithm = mapper.interpret(grammarInstance);
                    algorithm.setProblem(tsp);
                    algorithm.getStoppingConditionImplementation().setStoppingCondition(100);
                    AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(algorithm, "results/ALG" + split[1].charAt(0), String.valueOf(i));
                    threadPool.submit(runner);
                }
            }
            threadPool.shutdown();
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

//            HypervolumeCalculator calculator = new HypervolumeCalculator();
//            for (int i = 0; i < 598; i++) {
//                calculator.addParetoFront(new ArrayFront("experiment/NSGAII/" + i + ".txt"));
//                calculator.addParetoFront(new ArrayFront("experiment/GA/" + i + ".txt"));
//            }
//            calculator.addParetoFront(initialPopulation);
//
//            System.out.println(calculator.calculateHypervolume(initialPopulation));
//
//            try (FileWriter hvWriter = new FileWriter("experiment/NSGAII/hypervolume.txt")) {
//                for (int i = 0; i < 598; i++) {
//                    hvWriter.write(String.valueOf(calculator.calculateHypervolume(new ArrayFront("experiment/NSGAII/" + i + ".txt"))));
//                    hvWriter.write("\n");
//                }
//            }
//
//            try (FileWriter hvWriter = new FileWriter("experiment/GA/hypervolume.txt")) {
//                for (int i = 0; i < 598; i++) {
//                    hvWriter.write(String.valueOf(calculator.calculateHypervolume(new ArrayFront("experiment/GA/" + i + ".txt"))));
//                    hvWriter.write("\n");
//                }
//            }
        } catch (IOException ex) {
            Logger.getLogger(ExperimentInitialExecution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
