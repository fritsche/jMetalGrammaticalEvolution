package org.uma.jmetal.algorithm.impl;

import java.util.List;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.InitializationImplementation;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.algorithm.components.ReproductionImplementation;
import org.uma.jmetal.algorithm.components.SelectionImplementation;
import org.uma.jmetal.algorithm.components.impl.progress.EvaluationsCountProgress;
import org.uma.jmetal.algorithm.components.impl.stoppingcondition.MaxEvaluationsCondition;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class GeneratedDynamicGeneticAlgorithm<S extends Solution<?>> extends AbstractDynamicGeneticAlgorithm<S> {

    public GeneratedDynamicGeneticAlgorithm(Problem<S> problem,
            int populationSize,
            int maxEvaluations,
            InitializationImplementation<S> initialization,
            SelectionImplementation<S> selectionSource,
            SelectionOperator<List<S>, List<S>> selectionOperator,
            ReproductionImplementation<S> reproduction,
            CrossoverOperator<S> crossoverOperator,
            MutationOperator<S> mutationOperator,
            ReplacementImplementation<S> replacement,
            ArchivingImplementation<S> archiving) {
        super(problem,
                populationSize,
                new EvaluationsCountProgress(),
                new MaxEvaluationsCondition(maxEvaluations),
                initialization,
                new SequentialSolutionListEvaluator<>(),
                selectionSource,
                reproduction,
                replacement,
                archiving,
                selectionOperator,
                crossoverOperator,
                mutationOperator);
    }

    @Override
    public void run() {
        // Initialization
        initProgress();
        setPopulation(createInitialPopulation());

        // Evaluation
        evaluatePopulation(getPopulation());

        // Archiving
        archive(getPopulation());

        // Update Progress
        updateProgress(getPopulation().size());

        // Generation Loop
        while (!isStoppingConditionReached()) {
            // Selection
            List<S> matingPopulation = selection(getPopulation());

            // Mating
            List<S> offspringPopulation = reproduction(matingPopulation);

            // Evaluation
            offspringPopulation = evaluatePopulation(offspringPopulation);

            // Replacement
            setPopulation(replacement(getPopulation(), offspringPopulation));

            // Archiving
            archive(offspringPopulation);

            // Update Progress
            updateProgress(offspringPopulation.size());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append("Algorithm Configuration:\n")
                .append("\tPopulation Size: ").append(populationSize)
                .append("\n")
                .append("\tInitialization: ").append(getInitializationImplementation().toString())
                .append("\n")
                .append("\tSelection Operator: ").append(selectionOperator.toString())
                .append("\n")
                .append("\tSource: ").append(getSelectionImplementation().toString())
                .append("\n")
                .append("\tReproduction: ").append(getReproductionImplementation().toString())
                .append("\n")
                .append(crossoverOperator != null ? "\tCrossover Operator: " + crossoverOperator.toString() : "")
                .append("\n")
                .append(mutationOperator != null ? "\tMutation Operator: " + mutationOperator.toString() : "")
                .append("\n")
                .append("\tReplacement: ").append(getReplacementImplementation().toString())
                .append("\n")
                .append(archivingImplementation != null ? "\tArchiver: " +getArchivingImplementation().toString() : "")
                .append("\n");
        return builder.toString();
    }

}
