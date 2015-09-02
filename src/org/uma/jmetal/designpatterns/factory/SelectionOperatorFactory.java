package org.uma.jmetal.designpatterns.factory;

import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RouletteWheelSelection;
import org.uma.jmetal.operator.impl.selection.TournamentSelection;

public class SelectionOperatorFactory {

    public static final String K_TOURNAMENT = "K Tournament";
    public static final String RANDOM = "Random";
    public static final String ROULETTE_WHEEL = "Roulete Wheel";
    public static final String RANKING = "Ranking";

    public static SelectionOperator createSelectionOperator(String selectionOperator, int torunamentSize) {
        if (selectionOperator != null) {
            switch (selectionOperator) {
                case K_TOURNAMENT:
                    return new TournamentSelection(torunamentSize);
                case RANDOM:
                    return new RandomSelection();
                case ROULETTE_WHEEL:
                    return new RouletteWheelSelection();
            }
        }
        return null;
    }

}
