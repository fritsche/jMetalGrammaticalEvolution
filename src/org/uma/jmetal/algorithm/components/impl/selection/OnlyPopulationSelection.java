package org.uma.jmetal.algorithm.components.impl.selection;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.SelectionImplementation;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

public class OnlyPopulationSelection<S extends Solution<?>> implements SelectionImplementation<S> {

    @Override
    public List<S> selection(List<S> population, int matingPopulationSize, List<S> archivePopulation, SelectionOperator<List<S>, ?> selectionOperator) {
        List<S> matingPopulation = new ArrayList<>(matingPopulationSize);
        Object solution = selectionOperator.execute(population);
        if (solution instanceof List) {
            return (List<S>) solution;
        } else {
            S execute = (S) solution;
            matingPopulation.add(execute);
            for (int i = 1; i < matingPopulationSize; i++) {
                execute = (S) selectionOperator.execute(population);
                matingPopulation.add(execute);
            }
        }
        return matingPopulation;
    }

}
