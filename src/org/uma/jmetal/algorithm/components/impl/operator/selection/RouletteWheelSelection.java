//  RankingAndCrowdingSelection.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.
package org.uma.jmetal.algorithm.components.impl.operator.selection;

import java.util.ArrayList;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;

import java.util.List;
import org.uma.jmetal.algorithm.components.Ranking;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 * This class implements a selection for selecting a number of solutions from a solution list. The solutions are taken by mean of its ranking and crowding distance values.
 */
public class RouletteWheelSelection<S extends Solution<?>>
        implements SelectionOperator<List<S>, List<S>> {

    private final JMetalRandom randomGenerator;

    private Ranking<S, Integer> ranking;
    private int solutionsToSelect;

    public RouletteWheelSelection(int solutionsToSelect, Ranking<S, Integer> ranking) {
        this.randomGenerator = JMetalRandom.getInstance();
        this.ranking = ranking;
        this.solutionsToSelect = solutionsToSelect;
    }

    public int getSolutionsToSelect() {
        return solutionsToSelect;
    }

    public void setSolutionsToSelect(int solutionsToSelect) {
        this.solutionsToSelect = solutionsToSelect;
    }

    public Ranking<S, Integer> getRanking() {
        return ranking;
    }

    public void setRanking(Ranking<S, Integer> ranking) {
        this.ranking = ranking;
    }

    @Override
    public List<S> execute(List<S> solutionList) throws JMetalException {
        if (null == solutionList) {
            throw new JMetalException("The solution list is null");
        } else if (solutionList.isEmpty()) {
            throw new JMetalException("The solution list is empty");
        }

        if (ranking == null) {
            return SolutionListUtils.selectNRandomDifferentSolutions(solutionsToSelect, solutionList);
        }

        if (ranking != null) {
            ranking.computeRanking(solutionList);
        }

        Integer rankingSum = solutionList
                .stream()
                .mapToInt(solution -> ranking.getAttribute(solution) * -1)
                .sum();
        rankingSum = StrictMath.abs(rankingSum);

        List<Double> solutionValues = new ArrayList<>();

        for (S solution : solutionList) {
            double rankingValue = StrictMath.abs((double) ranking.getAttribute(solution) / (double) rankingSum);
            solutionValues.add(rankingValue);
        }

        List<S> selectedSolutions = new ArrayList<>();

        for (int i = 0; i < solutionsToSelect; i++) {
            double randomDouble = randomGenerator.nextDouble();
            double cumulativeSum = 0.0;
            int index = -1;
            do {
                index++;
                cumulativeSum += solutionValues.get(index);
            } while (cumulativeSum < randomDouble);
            selectedSolutions.add(solutionList.get(index));
        }
        return selectedSolutions;
    }

    @Override
    public String toString() {
        return "Roulette Wheel Selection";
    }

}
