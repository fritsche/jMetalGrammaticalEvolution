package org.uma.jmetal.algorithm.components.impl.archiving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.comparator.impl.RankingAndDiversityComparator;
import org.uma.jmetal.solution.Solution;

public class RankingAndDiversityArchiving<S extends Solution<?>> implements ArchivingImplementation<S> {

    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;
    private int archiveSize;
    private List<S> solutions;

    public RankingAndDiversityArchiving(int archiveSize, Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
        this.ranking = ranking;
        this.diversity = diversity;
        this.archiveSize = archiveSize;
        this.solutions = new ArrayList<>();
    }

    @Override
    public int updateArchive(List<S> population) {
        RankingAndDiversityComparator comparator = new RankingAndDiversityComparator(ranking, diversity);

        List<S> allSolutions = new ArrayList<>();
        allSolutions.addAll(solutions);
        allSolutions.addAll(population);

        if (ranking != null) {
            ranking.computeRanking(allSolutions);
        }
        if (diversity != null) {
            diversity.computeDiversity(allSolutions);
        }

        Collections.sort(allSolutions, comparator);

        solutions.clear();

        int count = 0;
        if (allSolutions.size() <= archiveSize) {
            solutions.addAll(allSolutions);
            count = solutions.size();
        } else {
            for (int i = 0; i < archiveSize; i++) {
                solutions.add(allSolutions.get(i));
                if (population.contains(allSolutions.get(i))) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public List<S> getArchive() {
        return solutions;
    }

    @Override
    public String toString() {
        return "RankingAndDiversityArchiving{" + "ranking=" + ranking + ", diversity=" + diversity + ", size = " + archiveSize + "}";
    }

}
