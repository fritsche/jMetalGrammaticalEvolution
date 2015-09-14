package org.uma.jmetal.algorithm.impl;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.impl.populationinitialization.RandomPopulationInitialization;
import org.uma.jmetal.algorithm.components.impl.progress.EvaluationsCountProgress;
import org.uma.jmetal.algorithm.components.impl.replacement.GenerationalReplacement;
import org.uma.jmetal.algorithm.components.impl.reproduction.TwoSolutionsReproduction;
import org.uma.jmetal.algorithm.components.impl.selection.PopulationAndArchiveSelection;
import org.uma.jmetal.algorithm.components.impl.stoppingcondition.MaxEvaluationsCondition;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class GeneratedDynamicGeneticAlgorithm<S extends Solution<?>> extends DefaultDynamicGeneticAlgorithm<S> {

    public GeneratedDynamicGeneticAlgorithm(Problem<S> problem,
            int populationSize,
            int maxEvaluations,
            ArchivingImplementation<S> archivingImplementation,
            SelectionOperator<List<S>, S> selectionOperator,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator) {
        super(problem,
                populationSize,
                new EvaluationsCountProgress(),
                new MaxEvaluationsCondition(maxEvaluations),
                new RandomPopulationInitialization<>(),
                new SequentialSolutionListEvaluator<>(),
                new PopulationAndArchiveSelection<>(),
                new TwoSolutionsReproduction<>(),
                new GenerationalReplacement<>(),
                archivingImplementation,
                selectionOperator,
                crossoverOperator,
                mutationOperator);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Algorithm Configuration:\n")
                .append("\tSelection Operator: ").append(selectionOperator.toString())
                .append("\n")
                .append(crossoverOperator != null ? "\tCrossover Operator: " + crossoverOperator.toString() : "")
                .append("\n")
                .append(mutationOperator != null ? "\tMutation Operator: " + mutationOperator.toString() : "")
                .append("\n")
                .append("\tArchiver: ").append(getArchivingImplementation().toString());
        return builder.toString();
    }

}
