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
import org.uma.jmetal.algorithm.impl.grammatical.GAGenerationGrammaticalEvolution;
import org.uma.jmetal.measure.qualityindicator.HypervolumeCalculator;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.PermutationTwoPointsCrossover;
import org.uma.jmetal.algorithm.components.impl.operator.crossover.SinglePointCrossoverVariableLength;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.DuplicationMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.IntegerMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.algorithm.components.impl.operator.mutation.PruneMutation;
import org.uma.jmetal.experiment.impl.AlgorithmRunner;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.impl.CITOProblem;
import org.uma.jmetal.problem.impl.GAGenerationProblem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class TunningNSGAIIAndSPEA2 {

    public static void main(String[] args) throws InterruptedException {
        String outputDirectory = args[0];
        int trainingSize = Integer.parseInt(args[1]);
        int geMaxEvaluations = Integer.parseInt(args[2]);
        ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(args[3]));
        
        CITOProblem problem = new CITOProblem("problems/OA_AJHsqldb.txt");
        GAGenerationProblem nsgaiiProblem
                = new GAGenerationProblem(
                        trainingSize,
                        problem,
                        5,
                        10,
                        20,
                        "grammar_nsgaii.bnf");
        GAGenerationGrammaticalEvolution nsgaiiTrainner
                = new GAGenerationGrammaticalEvolution(
                        nsgaiiProblem,
                        geMaxEvaluations,
                        100,
                        new SinglePointCrossoverVariableLength(0.9),
                        new IntegerMutation(0.01),
                        new BinaryTournamentSelection(),
                        new PruneMutation(0.01, 10),
                        new DuplicationMutation(0.01),
                        new SequentialSolutionListEvaluator<>(),
                        outputDirectory + "/GE_NSGAII.txt");
        AlgorithmRunner<VariableIntegerSolution> nsgaiiRunner = new AlgorithmRunner<>(nsgaiiTrainner, outputDirectory, "NSGA-II");
        
        GAGenerationProblem spea2Problem
                = new GAGenerationProblem(
                        trainingSize,
                        problem,
                        5,
                        10,
                        20,
                        "grammar_spea2.bnf");
        GAGenerationGrammaticalEvolution spea2Trainner
                = new GAGenerationGrammaticalEvolution(
                        spea2Problem,
                        geMaxEvaluations,
                        100,
                        new SinglePointCrossoverVariableLength(0.9),
                        new IntegerMutation(0.01),
                        new BinaryTournamentSelection(),
                        new PruneMutation(0.01, 10),
                        new DuplicationMutation(0.01),
                        new SequentialSolutionListEvaluator<>(),
                        outputDirectory + "/GE_SPEA2.txt");
        AlgorithmRunner<VariableIntegerSolution> spea2Runner = new AlgorithmRunner<>(spea2Trainner, outputDirectory, "SPEA2");

        threadPool.submit(nsgaiiRunner);
        threadPool.submit(spea2Runner);
        threadPool.shutdown();
        threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

}
