package org.uma.jmetal.algorithm.components.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.PopulationInitializationImplementation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class RandomPopulationInitialization<S extends Solution<?>> implements PopulationInitializationImplementation<S> {

    @Override
    public List<S> createInitialPopulation(Problem<S> problem, int populationSize) {
        List<S> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            S newIndividual = problem.createSolution();
            population.add(newIndividual);
        }
        return population;
    }

}
