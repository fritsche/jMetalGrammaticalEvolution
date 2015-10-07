package org.uma.jmetal.algorithm.components.impl.replacement;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.solution.Solution;

public class GenerationalReplacement<S extends Solution<?>> implements ReplacementImplementation<S> {

    private int elitismSize;
    private Ranking ranking;
    private Diversity diversity;

    public GenerationalReplacement(int elitismSize, Ranking ranking, Diversity diversity) {
        this.elitismSize = elitismSize;
        this.ranking = ranking;
        this.diversity = diversity;
    }

    public int getElitismSize() {
        return elitismSize;
    }

    public void setElitismSize(int elitismSize) {
        this.elitismSize = elitismSize;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public Diversity getDiversity() {
        return diversity;
    }

    public void setDiversity(Diversity diversity) {
        this.diversity = diversity;
    }

    @Override
    public List<S> replacement(List<S> population, List<S> offspringPopulation, int populationSize) {
        List<S> replacedPopulation = new ArrayList<>();
        if (elitismSize > 0) {
            if (ranking != null) {
                ranking.computeRanking(population);
            }
            if (diversity != null) {
                diversity.computeDiversity(population);
            }
        }
        
        if(elitismSize >= population.size()){
            System.err.println("Isso não deveria acontecer.");
            System.err.println("Elitismo: " + elitismSize);
            System.err.println("Tamanho População: " + population.size());
            System.err.println("Tamanho Offspring: " + offspringPopulation.size());
            System.err.println("populationSize: " + populationSize);
        }
        
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
