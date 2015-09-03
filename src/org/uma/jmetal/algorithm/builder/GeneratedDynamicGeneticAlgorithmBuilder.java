package org.uma.jmetal.algorithm.builder;

import org.uma.jmetal.algorithm.impl.GeneratedDynamicGeneticAlgorithm;
import org.uma.jmetal.designpatterns.factory.ArchivingImplementationFactory;
import org.uma.jmetal.designpatterns.factory.CrossoverOperatorFactory;
import org.uma.jmetal.designpatterns.factory.MutationOperatorFactory;
import org.uma.jmetal.designpatterns.factory.SelectionOperatorFactory;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class GeneratedDynamicGeneticAlgorithmBuilder<S extends Solution<?>> {

    private Problem<S> problem = null;
    private int populationSize = 0;
    private int maxEvaluations = 0;
    private String archivingImplementation;
    private int archivingSize;
    private String selectionOperator;
    private int tournamentSize;
    private String crossoverOperator;
    private double crossoverProbability;
    private String mutationOperator;
    private double mutationProbability;
    private int numberOfObjectives;

    public GeneratedDynamicGeneticAlgorithmBuilder() {
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setProblem(Problem<S> problem) {
        this.problem = problem;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchivingImplementation(String archivingImplementation) {
        this.archivingImplementation = archivingImplementation;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionOperator(String selectionOperator) {
        this.selectionOperator = selectionOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setCrossoverOperator(String crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMutationOperator(String mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchivingSize(int archivingSize) {
        this.archivingSize = archivingSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setNumberOfObjectives(int numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithm<S> buildAlgorithm() {
        return new GeneratedDynamicGeneticAlgorithm(
                problem,
                populationSize,
                maxEvaluations,
                ArchivingImplementationFactory.createArchivingImplementation(archivingImplementation, archivingSize, numberOfObjectives),
                SelectionOperatorFactory.createSelectionOperator(selectionOperator, tournamentSize, populationSize),
                CrossoverOperatorFactory.createCrossoverOperator(crossoverOperator, crossoverProbability),
                MutationOperatorFactory.createMutationOperator(mutationOperator, mutationProbability));
    }

}
