package org.uma.jmetal.algorithm.impl;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.impl.populationinitialization.RandomPopulationInitialization;
import org.uma.jmetal.algorithm.components.impl.progress.EvaluationsCountProgress;
import org.uma.jmetal.algorithm.components.impl.replacement.ParetoRankingAndCrowdingDistanceReplacement;
import org.uma.jmetal.algorithm.components.impl.reproduction.TwoSolutionsReproduction;
import org.uma.jmetal.algorithm.components.impl.selection.OnlyPopulationSelection;
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
                new OnlyPopulationSelection<>(),
                new TwoSolutionsReproduction<>(),
                new ParetoRankingAndCrowdingDistanceReplacement<>(),
                archivingImplementation,
                selectionOperator,
                crossoverOperator,
                mutationOperator);
    }

}
