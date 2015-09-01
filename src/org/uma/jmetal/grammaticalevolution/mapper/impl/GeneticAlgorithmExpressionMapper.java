package org.uma.jmetal.grammaticalevolution.mapper.impl;

import java.util.List;
import org.uma.jmetal.algorithm.AbstractDynamicGeneticAlgorithm;
import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.solution.Solution;

public class GeneticAlgorithmExpressionMapper<S extends Solution<?>> extends AbstractGrammarMapper<AbstractDynamicGeneticAlgorithm<S>> {

    @Override
    public AbstractDynamicGeneticAlgorithm<S> interpret(List<Integer> grammarInstance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
