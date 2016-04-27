package org.uma.jmetal.algorithm.builder;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.uma.jmetal.algorithm.components.factory.ArchivingImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.CrossoverOperatorFactory;
import org.uma.jmetal.algorithm.components.factory.DiversityFactory;
import org.uma.jmetal.algorithm.components.factory.InitializationImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.MutationOperatorFactory;
import org.uma.jmetal.algorithm.components.factory.RankingFactory;
import org.uma.jmetal.algorithm.components.factory.ReplacementImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.ReproductionImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.SelectionImplementationFactory;
import org.uma.jmetal.algorithm.components.factory.SelectionOperatorFactory;
import org.uma.jmetal.algorithm.components.factory.TrackingImplementationFactory;
import org.uma.jmetal.algorithm.impl.grammatical.GeneratedDynamicGeneticAlgorithm;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;

@Parameters(separators = " =")
public class GeneratedDynamicGeneticAlgorithmBuilder<S extends Solution<?>> {

    private Problem<S> problem;
    @Parameter(names = "--populationSize", description = "Population size (int)", required = true)
    private int populationSize;
    @Parameter(names = "--maxEvaluations", description = "Maximum Evaluations (int)", required = true)
    private int maxEvaluations;
    @Parameter(names = "--initialization", description = "Initialization Component (String)", required = true)
    private String initialization;
    @Parameter(names = "--selectionSource", description = "Selection Source (String)", required = true)
    private String selectionSource;
    @Parameter(names = "--selectionOperator", description = "Selection Operator (String)", required = true)
    private String selectionOperator;
    @Parameter(names = "--solutionsToSelectAtEachGeneration", description = "Number of solutions to select at each generation for mating (int)", hidden = true)
    private int solutionsToSelectAtEachGeneration;
    @Parameter(names = "--tournamentSize", description = "Tournament Size for the K-Tournament Selection Operator (int)")
    private int tournamentSize;
    @Parameter(names = "--selectionRanking", description = "Selection Ranking strategy (String)")
    private String selectionRanking;
    @Parameter(names = "--selectionDiversity", description = "Selection Diversity strategy (String)")
    private String selectionDiversity;
    @Parameter(names = "--reproduction", description = "Reproduction strategy (String)", required = true)
    private String reproduction;
    @Parameter(names = "--crossoverOperator", description = "Crossover Operator (String)")
    private String crossoverOperator;
    @Parameter(names = "--crossoverProbability", description = "Crossover Probability (double)")
    private double crossoverProbability;
    @Parameter(names = "--mutationOperator", description = "Mutation Operator (String)")
    private String mutationOperator;
    @Parameter(names = "--mutationProbability", description = "Mutation Probability (double)")
    private double mutationProbability;
    @Parameter(names = "--replacement", description = "Replacement Component (String)", required = true)
    private String replacement;
    @Parameter(names = "--elitismSize", description = "Elitism Size (int)")
    private int elitismSize;
    @Parameter(names = "--replacementRanking", description = "Replacement Ranking strategy (String)")
    private String replacementRanking;
    @Parameter(names = "--replacementDiversity", description = "Replacement Diversity strategy (String)")
    private String replacementDiversity;
    @Parameter(names = "--archiveRanking", description = "Archive Ranking strategy (String)")
    private String archiveRanking;
    @Parameter(names = "--archiveDiversity", description = "Archive Diversity strategy (String)")
    private String archiveDiversity;
    @Parameter(names = "--archiveSize", description = "Archive Size (int)")
    private int archiveSize;
    @Parameter(names = "--bisections", description = "Bisections (int)", hidden = true)
    private int bisections;
    @Parameter(names = "--numberOfObjectives", description = "Number of Objectives (int)", hidden = true)
    private int numberOfObjectives;
    @Parameter(names = "--outputTrackingFolder", description = "Folder Output Path for tracking population changes (String)", hidden = true)
    private String fileOutputPath;
    @Parameter(names = "--trackingImplementation", description = "Type of tracking (String)", hidden = true)
    private String trackingImplementation;

    //<editor-fold desc="Setters">
    public GeneratedDynamicGeneticAlgorithmBuilder() {
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setProblem(Problem<S> problem) {
        this.problem = problem;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setInitialization(String initialization) {
        this.initialization = initialization;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionSource(String selectionSource) {
        this.selectionSource = selectionSource;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionOperator(String selectionOperator) {
        this.selectionOperator = selectionOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSolutionsToSelectAtEachGeneration(int solutionsToSelectAtEachGeneration) {
        this.solutionsToSelectAtEachGeneration = solutionsToSelectAtEachGeneration;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionRanking(String selectionRanking) {
        this.selectionRanking = selectionRanking;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setSelectionDiversity(String selectionDiversity) {
        this.selectionDiversity = selectionDiversity;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReproduction(String reproduction) {
        this.reproduction = reproduction;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setCrossoverOperator(String crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMutationOperator(String mutationOperator) {
        this.mutationOperator = mutationOperator;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReplacement(String replacement) {
        this.replacement = replacement;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReplacementRanking(String replacementRanking) {
        this.replacementRanking = replacementRanking;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setReplacementDiversity(String replacementDiversity) {
        this.replacementDiversity = replacementDiversity;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchiveRanking(String archiveRanking) {
        this.archiveRanking = archiveRanking;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchiveDiversity(String archiveDiversity) {
        this.archiveDiversity = archiveDiversity;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setBisections(int bisections) {
        this.bisections = bisections;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setElitismSize(int elitismSize) {
        this.elitismSize = elitismSize;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setNumberOfObjectives(int numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTrackingImplementation(String trackingImplementation) {
        this.trackingImplementation = trackingImplementation;
        return this;
    }

    public GeneratedDynamicGeneticAlgorithmBuilder<S> setTrackingFileOutputPath(String fileOutputPath) {
        this.fileOutputPath = fileOutputPath;
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Getters">
    public Problem<S> getProblem() {
        return problem;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public String getInitialization() {
        return initialization;
    }

    public String getSelectionSource() {
        return selectionSource;
    }

    public String getSelectionOperator() {
        return selectionOperator;
    }

    public int getSolutionsToSelectAtEachGeneration() {
        return solutionsToSelectAtEachGeneration;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public String getSelectionRanking() {
        return selectionRanking;
    }

    public String getSelectionDiversity() {
        return selectionDiversity;
    }

    public String getReproduction() {
        return reproduction;
    }

    public String getCrossoverOperator() {
        return crossoverOperator;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public String getMutationOperator() {
        return mutationOperator;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public String getReplacement() {
        return replacement;
    }

    public int getElitismSize() {
        return elitismSize;
    }

    public String getReplacementRanking() {
        return replacementRanking;
    }

    public String getReplacementDiversity() {
        return replacementDiversity;
    }

    public String getArchiveRanking() {
        return archiveRanking;
    }

    public String getArchiveDiversity() {
        return archiveDiversity;
    }

    public int getArchiveSize() {
        return archiveSize;
    }

    public int getBisections() {
        return bisections;
    }

    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }

    public String getFileOutputPath() {
        return fileOutputPath;
    }

    public String getTrackingImplementation() {
        return trackingImplementation;
    }
    //</editor-fold>    
    
    public GeneratedDynamicGeneticAlgorithm<S> buildAlgorithm() {

//        System.out.println("Number of Objectives: " + numberOfObjectives);
//        System.out.println("Bisections: " + bisections);
//        System.out.println("Population Size: " + populationSize);
//        System.out.println("Initialization: " + initialization);
//        System.out.println("Selection Source: " + selectionSource);
//        System.out.println("Selection Operator: " + selectionOperator);
//        System.out.println("Tournament Size: " + tournamentSize);
//        System.out.println("Selection Ranking: " + selectionRanking);
//        System.out.println("Selection Diversity: " + selectionDiversity);
//        System.out.println("Mating Strategy: " + reproduction);
//        System.out.println("Solutions to Select: " + solutionsToSelectAtEachGeneration);
//        System.out.println("Crossover Operator: " + crossoverOperator);
//        System.out.println("Crossover Probability: " + crossoverProbability);
//        System.out.println("Mutation Operator: " + mutationOperator);
//        System.out.println("Mutation Probability: " + mutationProbability);
//        System.out.println("Replacement: " + replacement);
//        System.out.println("Elitism: " + elitismSize);
//        System.out.println("Replacement Ranking: " + replacementRanking);
//        System.out.println("Replacemente Diversity: " + replacementDiversity);
//        System.out.println("Archive Ranking: " + archiveRanking);
//        System.out.println("Archive Diversity: " + archiveDiversity);
//        System.out.println("Archive Size: " + archiveSize);
        return new GeneratedDynamicGeneticAlgorithm(
                problem,
                populationSize,
                maxEvaluations,
                InitializationImplementationFactory.createInitializationImplementation(initialization),
                SelectionImplementationFactory.createSelectionImplementation(selectionSource),
                SelectionOperatorFactory.createSelectionOperator(selectionOperator, tournamentSize, solutionsToSelectAtEachGeneration,
                        RankingFactory.createRanking(selectionRanking),
                        DiversityFactory.createRanking(selectionDiversity, populationSize, archiveSize, bisections, numberOfObjectives)),
                ReproductionImplementationFactory.createSelectionImplementation(reproduction),
                CrossoverOperatorFactory.createCrossoverOperator(crossoverOperator, crossoverProbability),
                MutationOperatorFactory.createMutationOperator(mutationOperator, mutationProbability),
                ReplacementImplementationFactory.createReplacementImplementation(replacement, elitismSize,
                        RankingFactory.createRanking(replacementRanking),
                        DiversityFactory.createRanking(replacementDiversity, populationSize, archiveSize, bisections, numberOfObjectives)),
                ArchivingImplementationFactory.createArchivingImplementation(archiveSize,
                        RankingFactory.createRanking(archiveRanking),
                        DiversityFactory.createRanking(archiveDiversity, populationSize, archiveSize, bisections, numberOfObjectives)),
                TrackingImplementationFactory.createTracking(trackingImplementation, fileOutputPath));
    }

}
