package org.uma.jmetal.main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.measure.qualityindicator.HypervolumeCalculator;

public class ComputeHypervolumeCommandLine {

    @Parameter(names = {"--help", "-h"}, description = "Help command.", help = true)
    private boolean help;
    @Parameter(names = {"--front", "-f"}, description = "Path for the front file (String).", required = true)
    private String frontPath;
    @Parameter(names = {"--maximumValues", "--max"}, description = "Maximum values separated by a blank space.", required = true, variableArity = true)
    private List<String> maximumValuesStrings;
    @Parameter(names = {"--minimumValues", "--min"}, description = "Minimum values separated by a blank space.", required = true, variableArity = true)
    private List<String> minimumValuesStrings;

    private double[] minimumValues;
    private double[] maximumValues;

    public static void main(String[] args) {
        try {
            ComputeHypervolumeCommandLine cmd = new ComputeHypervolumeCommandLine();

            JCommander jCommander = new JCommander(cmd, args);
            jCommander.setProgramName(cmd.getClass().getSimpleName());
            if (cmd.help) {
                jCommander.usage();
                return;
            }

            cmd.convertStringsToArrays();

            HypervolumeCalculator hv = new HypervolumeCalculator();
            double hypervolumeValue = hv.calculateHypervolume(cmd.frontPath, cmd.maximumValues, cmd.minimumValues);
            System.out.println(hypervolumeValue);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComputeHypervolumeCommandLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void convertStringsToArrays() {
        maximumValues = maximumValuesStrings
                .stream()
                .mapToDouble(value -> Double.valueOf(value) + 0.1)
                .toArray();

        minimumValues = minimumValuesStrings
                .stream()
                .mapToDouble(value -> Double.valueOf(value))
                .toArray();
    }

}
