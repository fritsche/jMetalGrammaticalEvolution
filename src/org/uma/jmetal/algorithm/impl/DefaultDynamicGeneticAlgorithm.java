package org.uma.jmetal.algorithm.impl;

import java.util.List;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.PopulationInitializationImplementation;
import org.uma.jmetal.algorithm.components.ProgressImplementation;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.algorithm.components.ReproductionImplementation;
import org.uma.jmetal.algorithm.components.SelectionImplementation;
import org.uma.jmetal.algorithm.components.StoppingConditionImplementation;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

public class DefaultDynamicGeneticAlgorithm<S extends Solution<?>> extends AbstractDynamicGeneticAlgorithm<S> {

    public DefaultDynamicGeneticAlgorithm(Problem<S> problem,
            int populationSize,
            ProgressImplementation progressImplementation,
            StoppingConditionImplementation stoppingConditionImplementation,
            PopulationInitializationImplementation<S> populationInitializationImplementation,
            SolutionListEvaluator<S> solutionListEvaluator,
            SelectionImplementation<S> selectionImplementation,
            ReproductionImplementation<S> reproductionImplementation,
            ReplacementImplementation<S> replacementImplementation,
            ArchivingImplementation<S> archivingImplementation,
            SelectionOperator<List<S>, S> selectionOperator,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator) {
        super(problem, populationSize, progressImplementation,
                stoppingConditionImplementation, populationInitializationImplementation,
                solutionListEvaluator, selectionImplementation, reproductionImplementation,
                replacementImplementation, archivingImplementation, selectionOperator,
                crossoverOperator, mutationOperator);
    }

    @Override
    public void run() {
        List<S> offspringPopulation;
        List<S> matingPopulation;

        initProgress();
        setPopulation(evaluatePopulation(createInitialPopulation()));
        updateProgress();
        while (!isStoppingConditionReached()) {
            matingPopulation = selection(getPopulation());
            offspringPopulation = reproduction(matingPopulation);
            offspringPopulation = evaluatePopulation(offspringPopulation);
            setPopulation(replacement(getPopulation(), offspringPopulation));
            archive(offspringPopulation);
            updateProgress();
        }
    }

}
