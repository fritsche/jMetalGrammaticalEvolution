package org.uma.jmetal.algorithm.components.impl.archiving;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.Diversity;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.solution.Solution;

public class RankingAndDiversityArchiving<S extends Solution<?>> implements ArchivingImplementation<S> {

    private Ranking<S, Integer> ranking;
    private Diversity<S, Double> diversity;

    public RankingAndDiversityArchiving(int archiveSize, Ranking<S, Integer> ranking, Diversity<S, Double> diversity) {
        this.ranking = ranking;
        this.diversity = diversity;
    }

    @Override
    public int updateArchive(List<S> population) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<S> getArchive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
