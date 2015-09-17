package org.uma.jmetal.algorithm.components.impl.populationinitialization;

import java.util.List;
import org.uma.jmetal.algorithm.components.PopulationInitializationImplementation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

public class FixedPopulationInitialization<S extends Solution<?>> implements PopulationInitializationImplementation<S> {

    private List<S> initialPopulation;

    public FixedPopulationInitialization(List<S> initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    public List<S> getInitialPopulation() {
        return initialPopulation;
    }

    public void setInitialPopulation(List<S> initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    @Override
    public List<S> createInitialPopulation(Problem<S> problem, int populationSize) {
        return initialPopulation;
    }

}
