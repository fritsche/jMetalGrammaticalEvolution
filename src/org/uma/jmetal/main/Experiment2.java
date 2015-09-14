package org.uma.jmetal.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.builder.DynamicNSGAIIBuilder;
import org.uma.jmetal.algorithm.builder.GeneratedDynamicGeneticAlgorithmBuilder;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.algorithm.impl.GeneratedDynamicGeneticAlgorithm;
import org.uma.jmetal.designpatterns.factory.ArchivingImplementationFactory;
import org.uma.jmetal.designpatterns.factory.CrossoverOperatorFactory;
import org.uma.jmetal.designpatterns.factory.SelectionOperatorFactory;
import org.uma.jmetal.operator.impl.crossover.PermutationTwoPointsCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.problem.multiobjective.MultiobjectiveTSP;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

public class Experiment2 {

    public static void main(String[] args) {
        try {
            MultiobjectiveTSP tsp = new MultiobjectiveTSP("kroA100.tsp", "kroB100.tsp");

            DynamicNSGAIIBuilder builder = new DynamicNSGAIIBuilder(tsp, new PermutationTwoPointsCrossover(0.95), new PermutationSwapMutation(0.05));
            builder.setMaxEvaluations(60000).setPopulationSize(100);
            for (int execution = 0; execution < 30; execution++) {
                System.out.println("Running NSGAII -- Execution " + execution);
                DynamicNSGAII nsgaii = builder.build();
                nsgaii.run();
                SolutionSetOutput.printObjectivesToFile(nsgaii.getResult(), "experiment/kro200/NSGAII_" + execution + "_FUN.txt");
            }

            GeneratedDynamicGeneticAlgorithmBuilder gaBuilder = new GeneratedDynamicGeneticAlgorithmBuilder();
            gaBuilder.setProblem(tsp)
                    .setPopulationSize(100)
                    .setMaxEvaluations(60000)
                    .setNumberOfObjectives(tsp.getNumberOfObjectives())
                    .setSelectionOperator(SelectionOperatorFactory.K_TOURNAMENT)
                    .setTournamentSize(10)
                    .setCrossoverOperator(CrossoverOperatorFactory.TWO_POINTS)
                    .setCrossoverProbability(1.0)
                    .setArchivingImplementation(ArchivingImplementationFactory.SPEA2)
                    .setArchivingSize(100);
            for (int execution = 0; execution < 30; execution++) {
                System.out.println("Running Custom GA -- Execution " + execution);
                GeneratedDynamicGeneticAlgorithm ga = gaBuilder.buildAlgorithm();
                ga.run();
                SolutionSetOutput.printObjectivesToFile(ga.getResult(), "experiment/kro200/ALG_" + execution + "_FUN.txt");
            }
        } catch (IOException ex) {
            Logger.getLogger(ExperimentInitialExecution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
