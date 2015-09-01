package org.uma.jmetal.grammaticalevolution.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.grammaticalevolution.representation.Expression;
import org.uma.jmetal.grammaticalevolution.representation.Node;
import org.uma.jmetal.solution.Solution;

public class GeneticAlgorithmExpressionMapper<S extends Solution<?>> extends AbstractGrammarMapper<AbstractDynamicGeneticAlgorithm<S>> {

    protected static int currentIndex;
    protected static int numberOfWraps;
    protected static int currentDepth;
    protected static List<Node> visitedNodes;

    public GeneticAlgorithmExpressionMapper() {
    }

    public GeneticAlgorithmExpressionMapper(Node rootNode) {
        super(rootNode);
    }

    @Override
    public AbstractDynamicGeneticAlgorithm<S> interpret(List<Integer> grammarInstance) {
        currentIndex = 0;
        numberOfWraps = 0;
        currentDepth = 1;
        visitedNodes = new ArrayList<>();
        return null;
    }

    public static void getNodeValue(Node node, List<Integer> grammarInstance) {

            int numberOfExpressions = node.getExpressions().size();
            //updateCurrentIndex(numberOfExpressions, grammarInstance);

            System.out.println("Number of Expressions" + numberOfExpressions);
            getExpression(grammarInstance, node);

//            while (currentDepth > maxDepth) {
//                currentDepth--;
//                grammarInstance.set(currentIndex, grammarInstance.get(currentIndex) + 1);
//                indexToGet = grammarInstance.get(currentIndex) % numberOfExpressions;
//                expression = node.getExpressions().get(indexToGet);
//                for (Node childNode : expression.getNodes()) {
//                    if (visitedNodes.contains(childNode)) {
//                        currentDepth++;
//                        break;
//                    }
//                }
//            }
//
//            String result = expression
//                    .getNodes()
//                    .stream()
//                    .map(childNode -> getNodeValue(childNode, grammarInstance))
//                    .collect(Collectors.joining(" "));
            visitedNodes.remove(node);
            //return result;
    }

    private static void updateCurrentIndex(int numberOfExpressions, List<Integer> grammarInstance) {
        if (numberOfExpressions > 1) {
            currentIndex++;
            if (currentIndex >= grammarInstance.size()) {
                currentIndex = 0;
                numberOfWraps++;
            }
        }
    }

    private static void getExpression(List<Integer> grammarInstance, Node node) {
        int indexToGet = grammarInstance.get(currentIndex) % node.getExpressions().size();
        updateCurrentIndex(node.getExpressions().size(), grammarInstance);
        Expression expression = node.getExpressions().get(indexToGet);
        System.out.println("Expression: " + expression);
        for (Node n : expression.getNodes()) {
            System.out.println("Node: " + n);
            if (!n.isTerminal() || !n.getExpressions().isEmpty()) {
                getExpression(grammarInstance, n);
            }
        }
    }

    public static void main(String[] args) {
        currentIndex = 0;
        numberOfWraps = 0;
        currentDepth = 1;
        visitedNodes = new ArrayList<>();
        GeneticAlgorithmExpressionMapper mapper = new GeneticAlgorithmExpressionMapper();
        mapper.loadGrammar("grammar.bnf");
        List<Integer> grammarInstance = new ArrayList<>();
        grammarInstance.add(200);
        grammarInstance.add(300);
        grammarInstance.add(400);
        grammarInstance.add(500);
        grammarInstance.add(600);
        getNodeValue(rootNode, new ArrayList<>(grammarInstance));
    }

}
