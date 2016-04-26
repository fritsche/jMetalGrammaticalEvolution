package org.uma.jmetal.main;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.uma.jmetal.algorithm.builder.DynamicNSGAIIBuilder;
import org.uma.jmetal.algorithm.builder.DynamicSPEA2Builder;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationTwoPointsCrossover;
import org.uma.jmetal.algorithm.components.impl.tracking.ResultToFileOutputTracking;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.algorithm.impl.DynamicSPEA2;
import org.uma.jmetal.experiment.impl.AlgorithmRunner;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.problem.impl.CITOProblem;
import org.uma.jmetal.solution.PermutationSolution;

public class ExperimentAlgorithmsCITO {

    public static void main(String[] args) throws IOException, InterruptedException {
        int executions = 30;
        String inputDir = args[0];
        String outputDir = args[1];
        new File(outputDir).mkdirs();
        ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[2]));
        String[] problems = {"OO_MyBatis",
            "OA_AJHsqldb",
            "OA_AJHotDraw",
            "OO_BCEL",
            "OO_JHotDraw",
            "OA_HealthWatcher",
            "OA_TollSystems",
            "OO_JBoss"};
        for (String problem : problems) {
            CITOProblem cito = new CITOProblem("problems/" + problem + ".txt");
            
            //build and run nsgaii
            DynamicNSGAIIBuilder nsgaiiBuilder = new DynamicNSGAIIBuilder(cito, 300, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation<>(0.02));
            nsgaiiBuilder.setMaxEvaluations(60000);
            
            for (int i = 0; i < executions; i++) {
                DynamicNSGAII nsgaii = nsgaiiBuilder.build();
                new File(outputDir + "/" + problem + "/NSGAII/EXECUTION_" + i).mkdirs();
                nsgaii.setTrackingImplementation(new ResultToFileOutputTracking(outputDir + "/" + problem + "/NSGAII/EXECUTION_" + i));
                AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(nsgaii, outputDir + "/" + problem + "/NSGAII/EXECUTION_" + i, String.valueOf(i));
                threadPool.submit(runner);
            }
            
            //build and run spea2
            DynamicSPEA2Builder spea2Builder = new DynamicSPEA2Builder<>(cito, 300, 300, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation<>(0.02));
            spea2Builder.setMaxEvaluations(60000);
            
            for (int i = 0; i < executions; i++) {
                DynamicSPEA2 spea2 = spea2Builder.build();
                new File(outputDir + "/" + problem + "/SPEA2/EXECUTION_" + i).mkdirs();
                spea2.setTrackingImplementation(new ResultToFileOutputTracking(outputDir + "/" + problem + "/SPEA2/EXECUTION_" + i));
                AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(spea2, outputDir + "/" + problem + "/SPEA2/EXECUTION_" + i, String.valueOf(i));
                threadPool.submit(runner);
            }
            
//                //build and run IBEA
//                DynamicIBEABuilder ibeaBuilder = new DynamicIBEABuilder<>(cito, 50, new PermutationSinglePointCrossover(0.5), new InversionMutation(1.0));
//                ibeaBuilder.setMaxEvaluations(60000);
//
//                for (int i = 0; i < executions; i++) {
//                    DynamicIBEA ibea = ibeaBuilder.build();
//                    new File(outputDir + "/" + problem + "/IBEA/EXECUTION_" + i).mkdirs();
//                    ibea.setTrackingImplementation(new ResultToFileOutputTracking(outputDir + "/" + problem + "/IBEA/EXECUTION_" + i));
//                    AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(ibea, outputDir + "/" + problem + "/IBEA/EXECUTION_" + i, String.valueOf(i));
//                    threadPool.submit(runner);
//                }
//
//                //load grammar
//                GeneticAlgorithmExpressionMapper mapper = new GeneticAlgorithmExpressionMapper(2, 5);
//                mapper.loadGrammar("grammar.bnf");
//
//                //read experiment files
//                List<String> vars = new ArrayList<>();
//                File directory = new File(inputDir);
//                for (File file : directory.listFiles()) {
//                    if (file.getName().startsWith("VAR") && !file.getName().contains("NSGA-II")) {
//                        vars.add(file.getPath());
//                    }
//                }
//
//                //build and run created algorithms
//                for (String var : vars) {
//                    List<Integer> grammarInstance = new ArrayList<>();
//                    String split = var.substring(var.lastIndexOf("VAR_") + 4);
//                    split = split.substring(0, split.indexOf("."));
//                    Scanner scanner = new Scanner(new FileReader(var));
//                    while (scanner.hasNextInt()) {
//                        grammarInstance.add(scanner.nextInt());
//                    }
////                    //print solutions
//                    {
//                        AbstractDynamicGeneticAlgorithm algorithm = mapper.interpret(grammarInstance);
//                        new File(outputDir + "/" + problem + "/ALG_" + split).mkdirs();
//                        try (FileWriter writer = new FileWriter(outputDir + "/" + problem + "/ALG_" + split + "/COMPONENTS.txt")) {
//                            writer.write(algorithm.toString());
//                        }
//                    }
//
//                    for (int i = 0; i < executions; i++) {
//                        AbstractDynamicGeneticAlgorithm algorithm = mapper.interpret(grammarInstance);
//                        algorithm.setTrackingImplementation(new ResultToFileOutputTracking(outputDir + "/" + problem + "/ALG_" + split + "/EXECUTION_" + i));
//                        algorithm.setProblem(cito);
//                        algorithm.getStoppingConditionImplementation().setStoppingCondition(60000);
//                        new File(outputDir + "/" + problem + "/ALG_" + split + "/EXECUTION_" + i).mkdirs();
//                        AlgorithmRunner<PermutationSolution<Integer>> runner = new AlgorithmRunner<>(algorithm, outputDir + "/" + problem + "/ALG_" + split + "/EXECUTION_" + i, String.valueOf(i));
//                        threadPool.submit(runner);
//                    }
//                }
        }
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
}
