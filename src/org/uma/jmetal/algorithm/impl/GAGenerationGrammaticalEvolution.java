package org.uma.jmetal.algorithm.impl;

import java.util.Collections;
import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.mutation.DuplicationMutation;
import org.uma.jmetal.operator.impl.mutation.PruneMutation;
import org.uma.jmetal.problem.impl.GAGenerationProblem;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

public class GAGenerationGrammaticalEvolution extends GrammaticalEvolutionAlgorithm {

    private final GAGenerationProblem problem;

    public GAGenerationGrammaticalEvolution(GAGenerationProblem problem, int maxEvaluations, int populationSize, CrossoverOperator<VariableIntegerSolution> crossoverOperator, MutationOperator<VariableIntegerSolution> mutationOperator, SelectionOperator<List<VariableIntegerSolution>, VariableIntegerSolution> selectionOperator, PruneMutation pruneMutationOperator, DuplicationMutation duplicationMutationOperator, SolutionListEvaluator<VariableIntegerSolution> evaluator) {
        super(problem, maxEvaluations, populationSize, crossoverOperator, mutationOperator, selectionOperator, pruneMutationOperator, duplicationMutationOperator, evaluator);
        this.problem = problem;
    }

    @Override
    public void run() {
        List<VariableIntegerSolution> offspringPopulation;
        List<VariableIntegerSolution> matingPopulation;

        setPopulation(createInitialPopulation());

        //Execute the algorithm
        evaluatePopulation(getPopulation());
        //Calculate the Hypervolume (fitness)
        problem.evaluateAll(getPopulation(), Collections.emptyList());
        
        initProgress();
        while (!isStoppingConditionReached()) {
            matingPopulation = selection(getPopulation());
            offspringPopulation = reproduction(matingPopulation);
            
            //Execute the algorithm
            evaluatePopulation(offspringPopulation);
            //Calculate the Hypervolume (fitness)
            problem.evaluateAll(getPopulation(), offspringPopulation);
            
            setPopulation(replacement(getPopulation(), offspringPopulation));
            updateProgress();
        }
    }

}
