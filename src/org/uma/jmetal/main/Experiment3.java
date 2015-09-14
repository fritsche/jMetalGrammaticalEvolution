package org.uma.jmetal.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.IntFunction;
import org.uma.jmetal.measure.HypervolumeCalculator;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;

public class Experiment3 {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        int executions = 30;
        String[] problems = new String[]{"kro100", "kro150", "kro200"};
        for (String problem : problems) {
            HypervolumeCalculator calculator = new HypervolumeCalculator();

            List<Front> nsgaFronts = new ArrayList<>();
            List<Front> gaFronts = new ArrayList<>();
            for (int i = 0; i < executions; i++) {
                nsgaFronts.add(new ArrayFront("experiment/" + problem + "/NSGAII_" + i + "_FUN.txt"));
                gaFronts.add(new ArrayFront("experiment/" + problem + "/ALG_" + i + "_FUN.txt"));
            }

            for (Front nsgaFront : nsgaFronts) {
                calculator.addParetoFront(nsgaFront);
            }
            for (Front gaFront : gaFronts) {
                calculator.addParetoFront(gaFront);
            }

            double[] nsgaHypervolumes = new double[executions];
            double[] gaHypervolumes = new double[executions];
            for (int i = 0; i < nsgaFronts.size(); i++) {
                Front get = nsgaFronts.get(i);
                nsgaHypervolumes[i] = calculator.calculateHypervolume(get);
            }
            for (int i = 0; i < gaFronts.size(); i++) {
                Front get = gaFronts.get(i);
                gaHypervolumes[i] = calculator.calculateHypervolume(get);
            }

            HashMap<String, double[]> values = new HashMap<>();
            values.put("NSGA-II", nsgaHypervolumes);
            values.put("Generated GA", gaHypervolumes);
            HashMap<String, HashMap<String, Boolean>> test = KruskalWallisTest.test(values);
            
            double nsgaAverage = Arrays.stream(nsgaHypervolumes).average().getAsDouble();
            double gaAverage = Arrays.stream(gaHypervolumes).average().getAsDouble();
            
            System.out.println(problem + " & " + nsgaAverage + " & " + gaAverage + " & " + test.get("NSGA-II").get("Generated GA") + "\\\\");

        }
    }
}
