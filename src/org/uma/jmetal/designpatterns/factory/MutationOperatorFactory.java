package org.uma.jmetal.designpatterns.factory;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.mutation.InsertMutation;
import org.uma.jmetal.operator.impl.mutation.InversionMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.operator.impl.mutation.ScrambleMutation;

public class MutationOperatorFactory {

    public static final String SWAP = "Swap Mutation";
    public static final String INSERT = "Insert Mutation";
    public static final String SCRAMBLE = "Scramble Mutation";
    public static final String INVERSION = "Inversion Mutation";

    public static MutationOperator createMutationOperator(String mutationOperator, double mutationProbability) {
        switch (mutationOperator) {
            case SWAP:
                return new PermutationSwapMutation(mutationProbability);
            case INSERT:
                return new InsertMutation(mutationProbability);
            case SCRAMBLE:
                return new ScrambleMutation(mutationProbability);
            case INVERSION:
                return new InversionMutation(mutationProbability);
            default:
                return null;
        }
    }

}
