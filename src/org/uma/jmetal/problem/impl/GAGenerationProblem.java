package org.uma.jmetal.problem.impl;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.measure.HypervolumeCalculator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

public class GAGenerationProblem<S extends Solution<?>> extends AbstractGrammaticalEvolutionProblem<AbstractDynamicGeneticAlgorithm<S>> {

    private int maxAlgorithmEvaluations;
    private final List<S> initialPopulation;

    public GAGenerationProblem(int maxAlgorithmEvaluations, List<S> initialPopulation, int minInitialCondons, int maxInitialCondons, String file) {
        super(minInitialCondons, maxInitialCondons, new GeneticAlgorithmMapper(), file);
        this.maxAlgorithmEvaluations = maxAlgorithmEvaluations;
        this.initialPopulation = initialPopulation;
    }

    public int getMaxAlgorithmEvaluations() {
        return maxAlgorithmEvaluations;
    }

    public void setMaxAlgorithmEvaluations(int maxAlgorithmEvaluations) {
        this.maxAlgorithmEvaluations = maxAlgorithmEvaluations;
    }

    @Override
    public void evaluate(VariableIntegerSolution solution) {
        AbstractDynamicGeneticAlgorithm<S> algorithm = mapper.interpret(solution);
        algorithm.getStoppingConditionImplementation().setStoppingCondition(maxAlgorithmEvaluations);
        algorithm.setPopulationInitializationImplementation((Problem<S> problem, int populationSize) -> {
            return initialPopulation;
        });
        algorithm.run();
        solution.setAttribute("AlgorithmRepresentation", algorithm);
    }

    public void evaluateAll(List<VariableIntegerSolution> parents, List<VariableIntegerSolution> offspring) {
        HypervolumeCalculator calculator = new HypervolumeCalculator();
        calculator.addParetoFront(parents);
        calculator.addParetoFront(offspring);

        final Function<? super VariableIntegerSolution, ? extends AbstractDynamicGeneticAlgorithm<S>> algorithmMapper = (solution) -> {
            return (AbstractDynamicGeneticAlgorithm<S>) solution.getAttribute("AlgorithmRepresentation");
        };
        final Consumer<? super AbstractDynamicGeneticAlgorithm<S>> addParetoFront = (algorithm) -> {
            calculator.addParetoFront(algorithm.getResult());
        };
        parents.stream()
                .map(algorithmMapper)
                .forEach(addParetoFront);
        offspring.stream()
                .map(algorithmMapper)
                .forEach(addParetoFront);

        final Consumer<? super VariableIntegerSolution> calculateHypervolumeFunction = (solution) -> {
            solution.setObjective(0, calculator.calculateHypervolume(algorithmMapper.apply(solution).getResult()));
        };
        parents.stream().forEach(calculateHypervolumeFunction);
        offspring.stream().forEach(calculateHypervolumeFunction);
    }

}
