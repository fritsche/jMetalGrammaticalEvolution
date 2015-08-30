package org.uma.jmetal.problem.impl;

import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

public abstract class AbstractGrammaticalEvolutionProblem extends AbstractGenericProblem<VariableIntegerSolution> {

    protected AbstractGrammarMapper mapper;

    public AbstractGrammaticalEvolutionProblem(AbstractGrammarMapper mapper, String file) {
        this.mapper = mapper;
        mapper.loadGrammar(file);
    }

    public Integer getLowerBound(int i) {
        return 1;
    }

    public Integer getUpperBound(int i) {
        return Integer.MAX_VALUE;
    }

}
