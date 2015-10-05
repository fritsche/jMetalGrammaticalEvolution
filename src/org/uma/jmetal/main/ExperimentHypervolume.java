package org.uma.jmetal.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.uma.jmetal.measure.HypervolumeCalculator;

public class ExperimentHypervolume {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        args = new String[]{"experiment/final", "60"};

        int generations = Integer.parseInt(args[1]);
        try (FileWriter tableWriter = new FileWriter(args[0] + "/TABLE.txt")) {
            //problem instance
            String[] problems = {"kroA100.tsp", "kroA150.tsp", "kroA200.tsp"};

            tableWriter.write("Problem & ");
            File inputDir = new File(args[0] + "/" + problems[0]);
            tableWriter.append(
                    Arrays.asList(inputDir.listFiles(file -> file.isDirectory()))
                    .stream()
                    .map(file -> file.getName())
                    .collect(Collectors.joining(" & ")));
            tableWriter.write(" \\\\\n");

            for (String problem : problems) {
                tableWriter.append(problem + " & ");
                inputDir = new File(args[0] + "/" + problem);
                HypervolumeCalculator calculator = new HypervolumeCalculator();
                HypervolumeCalculator generationalCalculator = new HypervolumeCalculator();

                List<File> vars = new ArrayList<>();
                for (File file : inputDir.listFiles(file -> file.isDirectory())) {
                    vars.add(file);
                }

                for (File algorithmDir : vars) {
                    for (File executionDir : algorithmDir.listFiles(file -> file.isDirectory())) {
                        File fun = executionDir.listFiles((dir, fileName) -> fileName.startsWith("FUN"))[0];
                        calculator.addParetoFront(fun.getPath());

                        File[] fronts = executionDir.listFiles((dir, fileName) -> fileName.endsWith(".txt") && !fileName.contains("VAR") && !fileName.contains("TIME") && !fileName.contains("FUN"));
                        for (File front : fronts) {
                            generationalCalculator.addParetoFront(front.getPath());
                        }
                    }
                }

                HashMap<String, Double[]> values = new HashMap<>();
                HashMap<String, Double> means = new HashMap<>();
                for (File algorithmDir : vars) {
                    List<Double> hypervolumes = new ArrayList<>();
                    for (File executionDir : algorithmDir.listFiles(file -> file.isDirectory())) {
                        File fun = executionDir.listFiles((dir, fileName) -> fileName.startsWith("FUN"))[0];
                        hypervolumes.add(calculator.calculateHypervolume(fun.getPath()));

                        try (FileWriter hvWriter = new FileWriter(executionDir.getPath() + "/GEN_HV.txt")) {
                            for (int i = 0; i < generations; i++) {
                                hvWriter.write(String.valueOf(generationalCalculator.calculateHypervolume(executionDir.getPath() + "/" + i + ".txt")));
                                hvWriter.write("\n");
                            }
                        }
                    }
                    Double mean = hypervolumes.stream().reduce(Double::sum).get() / hypervolumes.size();
                    means.put(algorithmDir.getName(), mean);
                    try (FileWriter hvWriter = new FileWriter(algorithmDir.getPath() + "/HYPERVOLUME.txt")) {
                        hvWriter.write(String.valueOf(mean));
                        hvWriter.write("\n");
                    }
                    Double[] hypervolumeArray = new Double[hypervolumes.size()];
                    values.put(algorithmDir.getName(), hypervolumes.toArray(hypervolumeArray));
                }

                HashMap<String, HashMap<String, Boolean>> result = KruskalWallisTest.test(values);

                Double maxMean = Double.NEGATIVE_INFINITY;
                String maxMeanAlgorithm = null;
                for (File algorithmDir : vars) {
                    Double mean = means.get(algorithmDir.getName());
                    if (mean > maxMean) {
                        maxMean = mean;
                        maxMeanAlgorithm = algorithmDir.getName();
                    }
                }
                final String reallyMaxMeanAlgorithm = maxMeanAlgorithm;

                DecimalFormat df = new DecimalFormat("0.00E0");

                String tableLine = vars.stream()
                        .map(algorithmDir -> {
                            String value = "";
                            boolean bold = reallyMaxMeanAlgorithm.equals(algorithmDir.getName()) || !result.get(reallyMaxMeanAlgorithm).get(algorithmDir.getName());
                            if (bold) {
                                value += "\\textbf{";
                            }
                            value += df.format(means.get(algorithmDir.getName()));
                            if (bold) {
                                value += "}";
                            }
                            return value;
                        })
                        .collect(Collectors.joining(" & "));
                tableWriter.write(tableLine + " \\\\\n");
            }
        }
    }
}
