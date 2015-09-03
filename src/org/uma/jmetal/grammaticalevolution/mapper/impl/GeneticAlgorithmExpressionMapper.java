package org.uma.jmetal.grammaticalevolution.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.builder.GeneratedDynamicGeneticAlgorithmBuilder;
import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.grammaticalevolution.representation.Expression;
import org.uma.jmetal.grammaticalevolution.representation.Node;
import org.uma.jmetal.solution.Solution;

public class GeneticAlgorithmExpressionMapper<S extends Solution<?>> extends AbstractGrammarMapper<AbstractDynamicGeneticAlgorithm<S>> {

    protected int currentIndex;
    protected int populationSize;
    protected int numberOfObjectives;

    public GeneticAlgorithmExpressionMapper(int populationSize, int numberOfObjectives) {
        this(populationSize, numberOfObjectives, null);
    }

    public GeneticAlgorithmExpressionMapper(int populationSize, int numberOfObjectives, Node rootNode) {
        super(rootNode);
        this.currentIndex = 0;
        this.populationSize = populationSize;
        this.numberOfObjectives = numberOfObjectives;
    }

    private Expression selectExpression(Node node, List<Integer> grammarInstance) {
        Expression expression = node.getExpressions().get(grammarInstance.get(currentIndex % grammarInstance.size()) % node.getExpressions().size());
        if (node.getExpressions().size() > 1) {
            currentIndex++;
        }
        return expression;
    }

    @Override
    public AbstractDynamicGeneticAlgorithm<S> interpret(List<Integer> grammarInstance) {
        currentIndex = 0;
        GeneratedDynamicGeneticAlgorithmBuilder<S> builder = new GeneratedDynamicGeneticAlgorithmBuilder<>();

        Node gaNode = nonTerminalNodes.get("GA"); //ou rootNode

        interpretSelectionOperator(gaNode.getExpressions().get(0).getNodes().get(0), grammarInstance, builder);
        interpretMatingOperators(gaNode.getExpressions().get(0).getNodes().get(1), grammarInstance, builder);
        interpretArchivingImplementation(gaNode.getExpressions().get(0).getNodes().get(2), grammarInstance, builder);

        return builder.buildAlgorithm();
    }

    private void interpretSelectionOperator(Node selectionNode, List<Integer> grammarInstance, GeneratedDynamicGeneticAlgorithmBuilder<S> builder) {
        Expression selectedExpression = selectExpression(selectionNode, grammarInstance);

        builder.setSelectionOperator(selectedExpression.getNodes().get(0).getName());
        builder.setPopulationSize(populationSize);
        if (selectedExpression.getNodes().size() == 2) {
            Node tournamentSizeNode = selectedExpression.getNodes().get(1);
            Node size = selectExpression(tournamentSizeNode, grammarInstance).getNodes().get(0);
            builder.setTournamentSize(Integer.parseInt(size.getName()));
        }
    }

    private void interpretMatingOperators(Node matingNode, List<Integer> grammarInstance, GeneratedDynamicGeneticAlgorithmBuilder<S> builder) {
        Expression selectedExpression = selectExpression(matingNode, grammarInstance);
        if (selectedExpression.getNodes().size() == 4) {
            builder.setCrossoverOperator(selectExpression(selectedExpression.getNodes().get(0), grammarInstance).getNodes().get(0).getName());
            builder.setCrossoverProbability(Double.parseDouble(selectExpression(selectedExpression.getNodes().get(1), grammarInstance).getNodes().get(0).getName()));
            builder.setMutationOperator(selectExpression(selectedExpression.getNodes().get(2), grammarInstance).getNodes().get(0).getName());
            builder.setMutationProbability(Double.parseDouble(selectExpression(selectedExpression.getNodes().get(3), grammarInstance).getNodes().get(0).getName()));
        } else {
            if (selectedExpression.getNodes().get(0).getName().contains("crossover")) {
                builder.setCrossoverOperator(selectExpression(selectedExpression.getNodes().get(0), grammarInstance).getNodes().get(0).getName());
                builder.setCrossoverProbability(Double.parseDouble(selectExpression(selectedExpression.getNodes().get(1), grammarInstance).getNodes().get(0).getName()));
            } else {
                builder.setMutationOperator(selectExpression(selectedExpression.getNodes().get(0), grammarInstance).getNodes().get(0).getName());
                builder.setMutationProbability(Double.parseDouble(selectExpression(selectedExpression.getNodes().get(1), grammarInstance).getNodes().get(0).getName()));
            }
        }
    }

    private void interpretArchivingImplementation(Node archivingNode, List<Integer> grammarInstance, GeneratedDynamicGeneticAlgorithmBuilder<S> builder) {
        try {
            Expression selectedExpression = selectExpression(archivingNode, grammarInstance);

            builder.setArchivingImplementation(selectExpression(selectedExpression.getNodes().get(0), grammarInstance).getNodes().get(0).getName());
            builder.setNumberOfObjectives(numberOfObjectives);

            String name = selectExpression(selectedExpression.getNodes().get(1), grammarInstance).getNodes().get(0).getName();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            String expression = name.replaceAll("N", String.valueOf(populationSize) + ".0");
            double archiveSize = (double) engine.eval(expression);
            builder.setArchivingSize((int) archiveSize);

        } catch (ScriptException ex) {
            Logger.getLogger(GeneticAlgorithmExpressionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }

    public void setNumberOfObjectives(int numberOfObjectives) {
        this.numberOfObjectives = numberOfObjectives;
    }

    public static void main(String[] args) {
        GeneticAlgorithmExpressionMapper mapper = new GeneticAlgorithmExpressionMapper(100, 2);
        mapper.loadGrammar("grammar.bnf");
        List<Integer> grammarInstance = new ArrayList<>();
        grammarInstance.add(202);
        grammarInstance.add(301);
        grammarInstance.add(405);
        grammarInstance.add(500);
        grammarInstance.add(602);
        AbstractDynamicGeneticAlgorithm interpret = mapper.interpret(grammarInstance);
    }

}
