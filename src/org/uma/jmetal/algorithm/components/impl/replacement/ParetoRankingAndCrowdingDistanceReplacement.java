package org.uma.jmetal.algorithm.components.impl.replacement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.uma.jmetal.algorithm.components.ReplacementImplementation;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.CrowdingDistanceComparator;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.CrowdingDistance;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

public class ParetoRankingAndCrowdingDistanceReplacement<S extends Solution<?>> implements ReplacementImplementation<S> {

    private final Ranking<S> dominanceRanking;
    private final CrowdingDistance<S> crowdingDistance;

    public ParetoRankingAndCrowdingDistanceReplacement() {
        this.dominanceRanking = new DominanceRanking<>();
        this.crowdingDistance = new CrowdingDistance<>();
    }

    @Override
    public List<S> replacement(List<S> population, List<S> offspringPopulation, int populationSize) {
        List<S> jointPopulation = new ArrayList<>();
        jointPopulation.addAll(population);
        jointPopulation.addAll(offspringPopulation);

        Ranking<S> ranking = computeRanking(jointPopulation);
        List<S> pop = crowdingDistanceSelection(ranking, populationSize);

        return pop;
    }

    protected Ranking<S> computeRanking(List<S> solutionList) {
        dominanceRanking.computeRanking(solutionList);
        return dominanceRanking;
    }

    protected List<S> crowdingDistanceSelection(Ranking<S> ranking, int populationSize) {
        List<S> population = new ArrayList<>(populationSize);
        int rankingIndex = 0;
        while (populationIsNotFull(population, populationSize)) {
            if (subfrontFillsIntoThePopulation(ranking, rankingIndex, population, populationSize)) {
                addRankedSolutionsToPopulation(ranking, rankingIndex, population);
                rankingIndex++;
            } else {
                crowdingDistance.computeDensityEstimator(ranking.getSubfront(rankingIndex));
                addLastRankedSolutionsToPopulation(ranking, rankingIndex, population, populationSize);
            }
        }
        return population;
    }

    protected boolean populationIsNotFull(List<S> population, int populationSize) {
        return population.size() < populationSize;
    }

    protected boolean subfrontFillsIntoThePopulation(Ranking<S> ranking, int rank, List<S> population, int populationSize) {
        return ranking.getSubfront(rank).size() < (populationSize - population.size());
    }

    protected void addRankedSolutionsToPopulation(Ranking<S> ranking, int rank, List<S> population) {
        List<S> front;

        front = ranking.getSubfront(rank);

        for (S solution : front) {
            population.add(solution);
        }
    }

    protected void addLastRankedSolutionsToPopulation(Ranking<S> ranking, int rank, List<S> population, int populationSize) {
        List<S> currentRankedFront = ranking.getSubfront(rank);

        Collections.sort(currentRankedFront, new CrowdingDistanceComparator<S>());

        int i = 0;
        while (population.size() < populationSize) {
            population.add(currentRankedFront.get(i));
            i++;
        }
    }

}
