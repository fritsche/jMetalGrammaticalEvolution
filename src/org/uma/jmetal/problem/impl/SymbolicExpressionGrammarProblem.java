/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uma.jmetal.problem.impl;

import java.util.List;
import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.grammaticalevolution.mapper.impl.SymbolicExpressionGrammarMapper;
import org.uma.jmetal.solution.IntegerSolution;

/**
 *
 * @author thaina
 */
public class SymbolicExpressionGrammarProblem extends AbstractGrammaticalEvolutionProblem {

    public SymbolicExpressionGrammarProblem(String file) {
        super(new SymbolicExpressionGrammarMapper(), file);
    }

    @Override
    public void evaluate(IntegerSolution s) {
        String function = (String) mapper.interpret((List) s);
        String replace = function.replace("X", "1");
    }

    public void calculate(String function) {
        for (int i = 1; i <= 5; i++) {
        }
    }

}
