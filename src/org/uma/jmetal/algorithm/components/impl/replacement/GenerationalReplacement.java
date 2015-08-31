package org.uma.jmetal.algorithm.components.impl.replacement;

import java.util.List;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.solution.Solution;

public class GenerationalReplacement<S extends Solution<?>> implements ReplacementImplementation<S> {

    @Override
    public List<S> replacement(List<S> population, List<S> offspringPopulation, int populationSize) {
        return offspringPopulation;
    }

}
