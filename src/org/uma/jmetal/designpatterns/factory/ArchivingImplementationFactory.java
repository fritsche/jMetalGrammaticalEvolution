package org.uma.jmetal.designpatterns.factory;

import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.impl.archiving.AdaptiveGridArchiveImplementation;
import org.uma.jmetal.algorithm.components.impl.archiving.CrowdingDistanceArchiveImplementation;
import org.uma.jmetal.algorithm.components.impl.archiving.SPEA2ArchiveImplementation;

public class ArchivingImplementationFactory {
    
    public static final String NSGA_II = "NSGA-II";
    public static final String SPEA2 = "SPEA2";
    public static final String ADAPTIVE_GRID = "Adaptive Grid Archiver";

    public static ArchivingImplementation createArchivingImplementation(String archivingImplementation, int archiveSize) {
        switch (archivingImplementation) {
            case NSGA_II:
                return new CrowdingDistanceArchiveImplementation(archiveSize);
            case SPEA2:
                return new SPEA2ArchiveImplementation(archiveSize);
            case ADAPTIVE_GRID:
                return new AdaptiveGridArchiveImplementation(archiveSize, archiveSize, archiveSize);
            default:
                return null;
        }
    }

}
