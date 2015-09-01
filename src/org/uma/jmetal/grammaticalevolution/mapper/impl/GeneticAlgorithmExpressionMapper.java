package org.uma.jmetal.grammaticalevolution.mapper.impl;

import java.util.List;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.algorithm.builder.GeneratedDynamicGeneticAlgorithmBuilder;
import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.grammaticalevolution.representation.Expression;
import org.uma.jmetal.grammaticalevolution.representation.Node;
import org.uma.jmetal.solution.Solution;

public class GeneticAlgorithmExpressionMapper<S extends Solution<?>> extends AbstractGrammarMapper<AbstractDynamicGeneticAlgorithm<S>> {

    protected int currentIndex;
    protected int numberOfWraps;

    public GeneticAlgorithmExpressionMapper() {
        this(null);
    }

    public GeneticAlgorithmExpressionMapper(Node rootNode) {
        super(rootNode);
        this.currentIndex = 0;
        this.numberOfWraps = 0;
    }

    private Expression selectExpression(Node node, List<Integer> grammarInstance) {
        Expression expression = node.getExpressions().get(grammarInstance.get(currentIndex) % node.getExpressions().size());
        if (node.getExpressions().size() > 1) {
            currentIndex++;
            if (currentIndex >= grammarInstance.size()) {
                currentIndex = 0;
                numberOfWraps++;
            }
        }
        return expression;
    }

    @Override
    public AbstractDynamicGeneticAlgorithm<S> interpret(List<Integer> grammarInstance) {
        currentIndex = 0;
        numberOfWraps = 0;
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
        if (selectedExpression.getNodes().size() == 2) {
            Node tournamentSizeNode = selectedExpression.getNodes().get(1);
            Node size = selectExpression(tournamentSizeNode, grammarInstance).getNodes().get(0);
            builder.setTournamentSize(Integer.parseInt(size.getName()));
        }
    }

    private void interpretMatingOperators(Node matingNode, List<Integer> grammarInstance, GeneratedDynamicGeneticAlgorithmBuilder<S> builder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void interpretArchivingImplementation(Node archivingNode, List<Integer> grammarInstance, GeneratedDynamicGeneticAlgorithmBuilder<S> builder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
