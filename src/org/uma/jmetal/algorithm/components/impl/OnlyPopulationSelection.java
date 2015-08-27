package org.uma.jmetal.algorithm.components.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.SelectionImplementation;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

public class OnlyPopulationSelection<S extends Solution<?>> implements SelectionImplementation<S> {

    @Override
    public List<S> selection(List<S> population, int matingPopulationSize, List<S> archivePopulation, SelectionOperator<List<S>, S> selectionOperator) {
        List<S> matingPopulation = new ArrayList<>(matingPopulationSize);
        for (int i = 0; i < matingPopulationSize; i++) {
            S solution = selectionOperator.execute(population);
            matingPopulation.add(solution);
        }
        return matingPopulation;
    }

}
