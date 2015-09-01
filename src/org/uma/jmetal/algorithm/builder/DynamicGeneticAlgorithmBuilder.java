package org.uma.jmetal.algorithm.builder;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.impl.DynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.impl.DynamicNSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;

public class DynamicGeneticAlgorithmBuilder<S extends Solution<?>> implements AlgorithmBuilder<DynamicGeneticAlgorithm<S>> {

    /**
     * GeneticAlgorithmBuilder class
     */
    private final Problem<S> problem;
    private int populationSize;
    private int maxEvaluations;
    private SelectionOperator<List<S>, S> selectionOperator;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    private ArchivingImplementation<S> archivingImplementation;

    /**
     * GeneticAlgorithmBuilder constructor
     *
     * @param problem
     * @param populationSize
     * @param maxEvaluations
     * @param selectionOperator
     * @param crossoverOperator
     * @param mutationOperator
     * @param archivingImplementation
     */
    public DynamicGeneticAlgorithmBuilder(Problem<S> problem,
            int populationSize,
            int maxEvaluations,
            SelectionOperator<List<S>, S> selectionOperator,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator,
            ArchivingImplementation<S> archivingImplementation) {
        this.problem = problem;
        this.maxEvaluations = maxEvaluations;
        this.populationSize = populationSize;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.selectionOperator = selectionOperator;
        this.archivingImplementation = archivingImplementation;

    }

    public DynamicGeneticAlgorithmBuilder<S> setMaxEvaluations(int maxEvaluations) {
        if (maxEvaluations < 0) {
            throw new JMetalException("maxIterations is negative: " + maxEvaluations);
        }
        this.maxEvaluations = maxEvaluations;

        return this;
    }

    public DynamicGeneticAlgorithmBuilder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

    public DynamicGeneticAlgorithmBuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
        if (selectionOperator == null) {
            throw new JMetalException("selectionOperator is null");
        }
        this.selectionOperator = selectionOperator;

        return this;
    }

    public DynamicGeneticAlgorithmBuilder<S> setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
        if (crossoverOperator == null) {
            throw new JMetalException("crossoverOperator is null");
        }
        this.crossoverOperator = crossoverOperator;

        return this;
    }

    public DynamicGeneticAlgorithmBuilder<S> setMutationOperator(MutationOperator<S> mutationOperator) {
        if (mutationOperator == null) {
            throw new JMetalException("mutationOperator is null");
        }
        this.mutationOperator = mutationOperator;

        return this;
    }

    public DynamicGeneticAlgorithmBuilder<S> setArchivingImplementation(ArchivingImplementation<S> archivingImplementation) {
        if (archivingImplementation == null) {
            throw new JMetalException("archivingImplementation is null");
        }
        this.archivingImplementation = archivingImplementation;

        return this;
    }

    /* Getters */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public SelectionOperator<List<S>, S> getSelectionOperator() {
        return selectionOperator;
    }

    public ArchivingImplementation<S> getArchivingImplementation() {
        return archivingImplementation;
    }

    @Override
    public DynamicGeneticAlgorithm<S> build() {
        DynamicGeneticAlgorithm<S> algorithm = null;
        algorithm = new DynamicGeneticAlgorithm<>(problem, populationSize, maxEvaluations, archivingImplementation, selectionOperator, crossoverOperator, mutationOperator);
        return algorithm;
    }

}
