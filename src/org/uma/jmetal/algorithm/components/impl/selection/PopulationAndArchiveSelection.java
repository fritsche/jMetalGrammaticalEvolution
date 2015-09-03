package org.uma.jmetal.algorithm.components.impl.selection;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;

public class PopulationAndArchiveSelection<S extends Solution<?>> extends OnlyPopulationSelection<S> {

    @Override
    public List<S> selection(List<S> population, int matingPopulationSize, List<S> archivePopulation, SelectionOperator<List<S>, ?> selectionOperator) {
        List<S> union = new ArrayList<>(population.size() + archivePopulation.size());
        union.addAll(population);
        union.addAll(archivePopulation);
        return super.selection(union, matingPopulationSize, archivePopulation, selectionOperator);
    }

}
