package org.uma.jmetal.algorithm.components.impl.populationinitialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uma.jmetal.algorithm.components.PopulationInitializationImplementation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.PermutationSolution;

public class ParallelDiversifiedPopulationInitialization<S extends PermutationSolution<Integer>> implements PopulationInitializationImplementation<S> {

    @Override
    public List<S> createInitialPopulation(Problem<S> problem, int populationSize) {
        Random random = new Random();
        int numberOfVariables = problem.getNumberOfVariables();

        List<Integer> possibleValues = new ArrayList<>();
        for (int i = 0; i < numberOfVariables; i++) {
            possibleValues.add(i);
        }

        List<S> initialPopulation = new ArrayList<>();

        S firstSolution = problem.createSolution();
        initialPopulation.add(firstSolution);

        solutionFor:
        for (int i = 1; i < numberOfVariables; i++) {
            S newSolution = problem.createSolution();
            for (int j = 0; j < numberOfVariables; j++) {
                List<Integer> nowPossibleValues = new ArrayList<>(possibleValues);
                for (int k = 0; k < j; k++) {
                    nowPossibleValues.remove(newSolution.getVariableValue(k));
                }
                for (S existingSolutions : initialPopulation) {
                    nowPossibleValues.remove(existingSolutions.getVariableValue(j));
                }
                if (nowPossibleValues.isEmpty()) {
                    continue solutionFor;
                }
                int value = random.nextInt(nowPossibleValues.size());
                newSolution.setVariableValue(j, nowPossibleValues.get(value));
            }
            initialPopulation.add(newSolution);
        }

        if (initialPopulation.size() > populationSize) {
            return initialPopulation.subList(0, populationSize);
        } else if (initialPopulation.size() < populationSize) {
            for (int i = 0; i < populationSize - initialPopulation.size(); i++) {
                initialPopulation.add(problem.createSolution());
            }
        }

        return initialPopulation;
    }
}
