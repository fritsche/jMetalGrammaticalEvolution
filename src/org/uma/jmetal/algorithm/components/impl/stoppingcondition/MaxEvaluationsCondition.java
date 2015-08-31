package org.uma.jmetal.algorithm.components.impl.stoppingcondition;

import org.uma.jmetal.algorithm.components.StoppingConditionImplementation;

public class MaxEvaluationsCondition implements StoppingConditionImplementation {

    private int maxEvaluations;

    public MaxEvaluationsCondition(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public boolean isStoppingConditionReached(int progress) {
        return progress >= this.maxEvaluations;
    }

    public int getMaxEvaluations() {
        return maxEvaluations;
    }

    public void setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
    }

}
