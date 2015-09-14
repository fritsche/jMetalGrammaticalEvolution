package org.uma.jmetal.algorithm.components.impl.archiving;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.algorithm.components.ArchivingImplementation;
import org.uma.jmetal.algorithm.components.impl.replacement.OutputReplacementProxy;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.fileoutput.SolutionSetOutput;

public class OutputArchivingProxy<S extends Solution<?>> implements ArchivingImplementation<S> {

    private final ArchivingImplementation<S> proxy;
    private int count;
    private final String outputFolder;

    public OutputArchivingProxy(ArchivingImplementation<S> proxy, String outputFolder) {
        this.proxy = proxy;
        this.count = 0;
        this.outputFolder = outputFolder;
    }

    @Override
    public int updateArchive(List<S> population) {
        int returnValue = proxy.updateArchive(population);
        try {
            SolutionSetOutput.printObjectivesToFile(getArchive(), outputFolder + "/" + (this.count++) + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(OutputReplacementProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }

    @Override
    public List<S> getArchive() {
        return proxy.getArchive();
    }

}
