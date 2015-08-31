package org.uma.jmetal.algorithm.components.impl.archiving;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;

public class CrowdingDistanceArchiveImplementation<S extends Solution<?>> extends CrowdingDistanceArchive<S> implements ArchivingImplementation<S> {

    public CrowdingDistanceArchiveImplementation(int maxSize) {
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

}
