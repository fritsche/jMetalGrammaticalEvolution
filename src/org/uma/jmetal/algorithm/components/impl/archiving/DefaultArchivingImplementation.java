package org.uma.jmetal.algorithm.components.impl.archiving;

import java.util.List;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.solution.Solution;

public class DefaultArchivingImplementation<S extends Solution<?>> implements ArchivingImplementation<S> {

    @Override
    public int updateArchive(List<S> population) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<S> getArchive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
