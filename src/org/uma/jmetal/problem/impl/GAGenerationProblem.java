package org.uma.jmetal.problem.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.grammaticalevolution.mapper.impl.GeneticAlgorithmExpressionMapper;
import org.uma.jmetal.measure.HypervolumeCalculator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

public class GAGenerationProblem<S extends Solution<?>> extends AbstractGrammaticalEvolutionProblem<AbstractDynamicGeneticAlgorithm<S>> {

    private int maxAlgorithmEvaluations;
    private int populationSize;
    private List<S> initialPopulation;
    private Problem<S> problem;

    public GAGenerationProblem(int maxAlgorithmEvaluations,
            int populationSize,
            Problem<S> problem,
            List<S> initialPopulation,
            int minInitialCondons,
            int maxInitialCondons,
            String grammarFile) {
        super(minInitialCondons, maxInitialCondons, new GeneticAlgorithmExpressionMapper(populationSize, problem.getNumberOfObjectives()), grammarFile);
        this.maxAlgorithmEvaluations = maxAlgorithmEvaluations;
        this.initialPopulation = initialPopulation;
        this.problem = problem;
        this.populationSize = populationSize;
        
        setName("GA Generation Problem");
        setNumberOfObjectives(1);
        setNumberOfVariables(-1);
    }

    public int getMaxAlgorithmEvaluations() {
        return maxAlgorithmEvaluations;
    }

    public void setMaxAlgorithmEvaluations(int maxAlgorithmEvaluations) {
        this.maxAlgorithmEvaluations = maxAlgorithmEvaluations;
    }

    public List<S> getInitialPopulation() {
        return initialPopulation;
    }

    public void setInitialPopulation(List<S> initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    public Problem<S> getProblem() {
        return problem;
    }

    public void setProblem(Problem<S> problem) {
        this.problem = problem;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        ((GeneticAlgorithmExpressionMapper) mapper).setPopulationSize(populationSize);
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {
        AbstractDynamicGeneticAlgorithm<S> algorithm = mapper.interpret(solution);
        algorithm.setPopulationInitializationImplementation((Problem<S> problem, int populationSize) -> {
            return initialPopulation;
        });
        algorithm.getArchivingImplementation().updateArchive(initialPopulation);
        algorithm.getStoppingConditionImplementation().setStoppingCondition(maxAlgorithmEvaluations);
        algorithm.setProblem(problem);
        algorithm.setPopulationSize(populationSize);
        algorithm.run();
        solution.setAttribute("Algorithm", algorithm);
        solution.setAttribute("Result", algorithm.getResult());
    }

    public void evaluateAll(List<VariableIntegerSolution> parents, List<VariableIntegerSolution> offspring) {
        HypervolumeCalculator calculator = new HypervolumeCalculator();

        final Function<? super VariableIntegerSolution, AbstractDynamicGeneticAlgorithm<S>> algorithmMapper = (solution) -> {
            return (AbstractDynamicGeneticAlgorithm<S>) solution.getAttribute("Algorithm");
        };
        final Consumer<AbstractDynamicGeneticAlgorithm<S>> addParetoFront = (algorithm) -> {
            calculator.addParetoFront(algorithm.getResult());
        };
        parents.stream()
                .map(algorithmMapper)
                .forEach(addParetoFront);
        offspring.stream()
                .map(algorithmMapper)
                .forEach(addParetoFront);

        final Consumer<? super VariableIntegerSolution> calculateHypervolumeFunction = (solution) -> {
            solution.setObjective(0, calculator.calculateHypervolume(algorithmMapper.apply(solution).getResult()) * -1);
        };
        parents.stream().forEach(calculateHypervolumeFunction);
        offspring.stream().forEach(calculateHypervolumeFunction);
    }

}
