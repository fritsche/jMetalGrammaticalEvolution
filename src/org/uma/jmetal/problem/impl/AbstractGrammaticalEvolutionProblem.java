package org.uma.jmetal.problem.impl;

import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;

public abstract class AbstractGrammaticalEvolutionProblem extends AbstractIntegerProblem {

    protected AbstractGrammarMapper mapper;

    public AbstractGrammaticalEvolutionProblem(AbstractGrammarMapper mapper, String file) {
        this.mapper = mapper;
        mapper.loadGrammar(file);
    }

}
