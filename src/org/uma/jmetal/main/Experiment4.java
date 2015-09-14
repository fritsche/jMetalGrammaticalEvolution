package org.uma.jmetal.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.builder.DynamicNSGAIIBuilder;
import org.uma.jmetal.algorithm.builder.GeneratedDynamicGeneticAlgorithmBuilder;
import org.uma.jmetal.algorithm.components.impl.archiving.OutputArchivingProxy;
import org.uma.jmetal.algorithm.components.impl.populationinitialization.RandomPopulationInitialization;
import org.uma.jmetal.algorithm.components.impl.replacement.OutputReplacementProxy;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.algorithm.impl.GeneratedDynamicGeneticAlgorithm;
import org.uma.jmetal.designpatterns.factory.ArchivingImplementationFactory;
import org.uma.jmetal.designpatterns.factory.CrossoverOperatorFactory;
import org.uma.jmetal.designpatterns.factory.SelectionOperatorFactory;
import org.uma.jmetal.measure.HypervolumeCalculator;
import org.uma.jmetal.operator.impl.crossover.PermutationTwoPointsCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.solution.PermutationSolution;
import org.uma.jmetal.util.front.imp.ArrayFront;

public class Experiment4 {

    public static void main(String[] args) {
        try {
            MultiobjectiveTSP tsp = new MultiobjectiveTSP("kroA100.tsp", "kroB100.tsp");

            List<PermutationSolution<Integer>> initialPopulation = new RandomPopulationInitialization().createInitialPopulation(tsp, 100);

            DynamicNSGAIIBuilder builder = new DynamicNSGAIIBuilder(tsp, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation(0.05));
            builder.setMaxEvaluations(60000).setPopulationSize(100);

            DynamicNSGAII nsgaii = builder.build();
            nsgaii.setReplacementImplementation(new OutputReplacementProxy(nsgaii.getReplacementImplementation(), "experiment/NSGAII/"));
            nsgaii.setPopulationInitializationImplementation((Problem problem, int populationSize) -> {
                return initialPopulation;
            });
            nsgaii.run();

            GeneratedDynamicGeneticAlgorithmBuilder gaBuilder = new GeneratedDynamicGeneticAlgorithmBuilder();
            gaBuilder.setProblem(tsp)
                    .setPopulationSize(100)
                    .setMaxEvaluations(60000)
                    .setNumberOfObjectives(tsp.getNumberOfObjectives())
                    .setSelectionOperator(SelectionOperatorFactory.K_TOURNAMENT)
                    .setTournamentSize(8)
                    .setCrossoverOperator(CrossoverOperatorFactory.TWO_POINTS)
                    .setCrossoverProbability(0.95)
                    //                    .setMutationOperator(MutationOperatorFactory.INSERT)
                    //                    .setMutationProbability(1)
                    .setArchivingImplementation(ArchivingImplementationFactory.ADAPTIVE_GRID)
                    .setArchivingSize(100);
            GeneratedDynamicGeneticAlgorithm ga = gaBuilder.buildAlgorithm();
            ga.getArchivingImplementation().updateArchive(initialPopulation);
            ga.setArchivingImplementation(new OutputArchivingProxy(ga.getArchivingImplementation(), "experiment/GA/"));
            ga.setPopulationInitializationImplementation((Problem problem, int populationSize) -> {
                return initialPopulation;
            });
            ga.run();

            HypervolumeCalculator calculator = new HypervolumeCalculator();
            for (int i = 0; i < 598; i++) {
                calculator.addParetoFront(new ArrayFront("experiment/NSGAII/" + i + ".txt"));
                calculator.addParetoFront(new ArrayFront("experiment/GA/" + i + ".txt"));
            }
            calculator.addParetoFront(initialPopulation);

            System.out.println(calculator.calculateHypervolume(initialPopulation));

            try (FileWriter hvWriter = new FileWriter("experiment/NSGAII/hypervolume.txt")) {
                for (int i = 0; i < 598; i++) {
                    hvWriter.write(String.valueOf(calculator.calculateHypervolume(new ArrayFront("experiment/NSGAII/" + i + ".txt"))));
                    hvWriter.write("\n");
                }
            }

            try (FileWriter hvWriter = new FileWriter("experiment/GA/hypervolume.txt")) {
                for (int i = 0; i < 598; i++) {
                    hvWriter.write(String.valueOf(calculator.calculateHypervolume(new ArrayFront("experiment/GA/" + i + ".txt"))));
                    hvWriter.write("\n");
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ExperimentInitialExecution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
