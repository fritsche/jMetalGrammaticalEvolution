package org.uma.jmetal.problem.impl;

import org.uma.jmetal.grammaticalevolution.mapper.AbstractGrammarMapper;
import org.uma.jmetal.solution.impl.VariableIntegerSolution;

public abstract class AbstractGrammaticalEvolutionProblem extends AbstractGenericProblem<VariableIntegerSolution> {

    protected AbstractGrammarMapper mapper;
    protected int maxCondons;
    protected int minCondons;

    public AbstractGrammaticalEvolutionProblem(int minCondons, int maxCondons, AbstractGrammarMapper mapper, String file) {
        this.mapper = mapper;
        this.minCondons = minCondons;
        this.maxCondons = maxCondons;
        mapper.loadGrammar(file);
    }

    public Integer getLowerBound(int i) {
        return 1;
    }

    public Integer getUpperBound(int i) {
        return Integer.MAX_VALUE;
    }

    public AbstractGrammarMapper getMapper() {
        return mapper;
    }

    public void setMapper(AbstractGrammarMapper mapper) {
        this.mapper = mapper;
    }

    public int getMaxCondons() {
        return maxCondons;
    }

    public void setMaxCondons(int maxCondons) {
        this.maxCondons = maxCondons;
    }

    public int getMinCondons() {
        return minCondons;
    }

    public void setMinCondons(int minCondons) {
        this.minCondons = minCondons;
    }

    @Override
    public VariableIntegerSolution createSolution() {
        return new VariableIntegerSolution(this, minCondons, maxCondons);
    }

}
