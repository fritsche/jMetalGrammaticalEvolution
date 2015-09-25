package org.uma.jmetal.algorithm.components.impl.replacement;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.solution.Solution;

public class GenerationalReplacement<S extends Solution<?>> implements ReplacementImplementation<S> {

    private int elitismSize;

    public GenerationalReplacement(int elitismSize) {
        this.elitismSize = elitismSize;
    }

    public int getElitismSize() {
        return elitismSize;
    }

    public void setElitismSize(int elitismSize) {
        this.elitismSize = elitismSize;
    }

    @Override
    public List<S> replacement(List<S> population, List<S> offspringPopulation, int populationSize) {
        List<S> replacedPopulation = new ArrayList<>();
        for (int i = 0; i < elitismSize; i++) {
            replacedPopulation.add(population.get(i));
        }

        for (int i = 0; replacedPopulation.size() < populationSize && i < offspringPopulation.size(); i++) {
            replacedPopulation.add(offspringPopulation.get(i));
        }

        if (replacedPopulation.size() < populationSize) {
            for (int i = elitismSize; replacedPopulation.size() < populationSize; i++) {
                replacedPopulation.add(population.get(i));
            }
        }

        return replacedPopulation;
    }

    @Override
    public String toString() {
        return "GenerationalReplacement{" + "elitismSize=" + elitismSize + '}';
    }

}
