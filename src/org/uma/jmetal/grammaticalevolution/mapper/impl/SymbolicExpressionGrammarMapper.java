package org.uma.jmetal.grammaticalevolution.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.grammaticalevolution.representation.Expression;
import org.uma.jmetal.grammaticalevolution.representation.Node;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

public class SymbolicExpressionGrammarMapper extends AbstractGrammarMapper<String> {

    protected int currentIndex;
    protected int numberOfWraps;
    protected int currentDepth;
    protected List<Node> visitedNodes;

    protected int maxDepth;

    public SymbolicExpressionGrammarMapper() {
        super();
        maxDepth = 10;
    }

    public SymbolicExpressionGrammarMapper(Node rootNode) {
        super(rootNode);
    }

    public SymbolicExpressionGrammarMapper(int maxNumberOfSelfLoops) {
        this.maxDepth = maxNumberOfSelfLoops;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getNumberOfWraps() {
        return numberOfWraps;
    }

    @Override
    public String interpret(List<Integer> grammarInstance) {
        currentIndex = 0;
        numberOfWraps = 0;
        currentDepth = 1;
        visitedNodes = new ArrayList<>();
        return getNodeValue(rootNode, new ArrayList<>(grammarInstance));
    }

    private String getNodeValue(Node node, List<Integer> grammarInstance) {
        if (node.isTerminal()) {
            return node.getName();
        } else {
            visitedNodes.add(node);

            int expressionsSize = node.getExpressions().size();
            if (expressionsSize > 1) {
                currentIndex++;
                if (currentIndex >= grammarInstance.size()) {
                    currentIndex = 0;
                    numberOfWraps++;
                }
            }

            int indexToGet = grammarInstance.get(currentIndex) % expressionsSize;
            Expression expression = node.getExpressions().get(indexToGet);
            for (Node childNode : expression.getNodes()) {
                if (visitedNodes.contains(childNode)) {
                    currentDepth++;
                    break;
                }
            }
            while (currentDepth > maxDepth) {
                currentDepth--;
                grammarInstance.set(currentIndex, grammarInstance.get(currentIndex) + 1);
                indexToGet = grammarInstance.get(currentIndex) % expressionsSize;
                expression = node.getExpressions().get(indexToGet);
                for (Node childNode : expression.getNodes()) {
                    if (visitedNodes.contains(childNode)) {
                        currentDepth++;
                        break;
                    }
                }
            }

            String result = expression
                    .getNodes()
                    .stream()
                    .map(childNode -> getNodeValue(childNode, grammarInstance))
                    .collect(Collectors.joining(" "));

            visitedNodes.remove(node);

            return result;
        }
    }

    public static void main(String[] args) {
        SymbolicExpressionGrammarMapper mapper = new SymbolicExpressionGrammarMapper();
        mapper.loadGrammar("symbolicexpression.bnf");

        List<Integer> integerList = new ArrayList<>();
        JMetalRandom random = JMetalRandom.getInstance();
        for (int i = 0; i < 20; i++) {
            integerList.add(random.nextInt(0, 1000));
        }

        System.out.println(mapper.interpret(integerList));
    }

}
