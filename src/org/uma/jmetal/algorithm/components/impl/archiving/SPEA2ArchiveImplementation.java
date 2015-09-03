package org.uma.jmetal.algorithm.components.impl.archiving;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.archive.impl.AbstractBoundedArchive;
import org.uma.jmetal.util.comparator.StrengthFitnessComparator;
import org.uma.jmetal.util.solutionattribute.impl.StrengthRawFitness;

public class SPEA2ArchiveImplementation<S extends Solution<?>> extends AbstractBoundedArchive<S> implements ArchivingImplementation<S> {

    private final StrengthRawFitness<S> strengthRawFitness = new StrengthRawFitness<>();

    public SPEA2ArchiveImplementation(int maxSize) {
        super(maxSize);
    }

    @Override
    public int updateArchive(List<S> population) {
        return population
                .stream()
                .map((solution) -> this.add(solution) ? 1 : 0)
                .reduce(0, Integer::sum);
    }

    @Override
    public List<S> getArchive() {
        return this.getSolutionList();
    }

    @Override
    public void prune() {
        if (list.size() > maxSize) {
            List<S> solutionList = getSolutionList();
            int index = SolutionListUtils.findIndexOfWorstSolution(solutionList, new StrengthFitnessComparator<>());
            solutionList.remove(index);
        }
    }

    @Override
    public String toString() {
        return "SPEA2 (" + getMaxSize() + ")";
    }
}
