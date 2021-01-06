import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HandComparer {

    private HandScorer handScorer = new HandScorer();

    private SortTwoDimensionalArray sortTwoDimensionalArray = new SortTwoDimensionalArray();

    public List<Integer[]> compareHands(List<List<Integer>> scores) {

//        for (String[][] hand : hands) {
//            List<Integer> score = handScorer.calculateHand(hand);
//            scores.add(score);
//        }

        List<Integer[]> highScoreOrder = new ArrayList<>();
        List<Integer> playerIndexes = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            playerIndexes.add(i);
        }

        highScoreOrder = this.recursiveMethod(scores, playerIndexes, highScoreOrder);

        return highScoreOrder;
    }

    public List<Integer[]> recursiveMethod(List<List<Integer>> scores, List<Integer> playerIndexes, List<Integer[]> scoreOrder) {
        List<Integer> highScorePlayerIndexes = new ArrayList<>();
        List<Integer> lowScorePlayerIndexes = new ArrayList<>();
        Integer[][] comparisons = new Integer[scores.size()][2];
        List<List<Integer>> highScoresToCheck = new ArrayList<>();
        List<List<Integer>> lowScoresToCheck = new ArrayList<>();

        if (scores.size() > 0 && scores.get(0).size() == 1) {
//            System.out.println("Score size 1");
            Integer[][] scoresArray = new Integer[playerIndexes.size()][2];
            for (int i = 0; i < playerIndexes.size(); i++) {
                scoresArray[i][0] = scores.get(i).get(0);
                scoresArray[i][1] = playerIndexes.get(i);
            }
            sortTwoDimensionalArray.quickSort(scoresArray, 0, scoresArray.length-1);
            scoresArray = this.reverseArray(scoresArray);

            int previousScoreChecked = -1;
            int scoreToCheck=0;
            for (int i = 0; i < scoresArray.length; i++) {
                if(scoresArray[i][0] != previousScoreChecked) {
                    scoreToCheck=scoresArray[i][0];
                    List<Integer> indexSet= new ArrayList<>();
                    indexSet.add(scoresArray[i][1]);
                    for (int j = 0; j < scoresArray.length; j++) {
                        if (i != j) {
                            if(scoresArray[j][0] == scoreToCheck) {
                                indexSet.add(scoresArray[j][1]);
                            }
                        }
                    }
                    scoreOrder.add(indexSet.toArray(new Integer[0]));
                }
                previousScoreChecked=scoreToCheck;
            }

            return scoreOrder;
        }
        if (scores.isEmpty()) {
            return scoreOrder;
        }
        int highestScore = -1;
        for (int i = 0; i < scores.size(); i++) {
            comparisons[i][0] = scores.get(i).get(0);
            comparisons[i][1] = playerIndexes.get(i);
            if (comparisons[i][0] > highestScore) {
                highestScore = comparisons[i][0];
            }
        }

        for (int i = 0; i < comparisons.length; i++) {
            if (comparisons[i][0] == highestScore) {
                highScoresToCheck.add(scores.get(i).subList(1, scores.get(i).size()));
                highScorePlayerIndexes.add(comparisons[i][1]);
            } else {
                lowScoresToCheck.add(scores.get(i));
                lowScorePlayerIndexes.add(comparisons[i][1]);
            }
        }

        if (highScorePlayerIndexes.size() == 1) {
//            System.out.println("Adding HIGH single entry " + highScorePlayerIndexes.get(0));
            scoreOrder.add(new Integer[]{highScorePlayerIndexes.get(0)});
        } else {
//            System.out.println("Entering HIGH scores recursive method...");
            recursiveMethod(highScoresToCheck, highScorePlayerIndexes, scoreOrder);
        }
        if (lowScorePlayerIndexes.size() == 1) {
//            System.out.println("Adding LOW single entry " + highScorePlayerIndexes.get(0));
            scoreOrder.add(new Integer[]{lowScorePlayerIndexes.get(0)});
        } else {
//            System.out.println("Entering LOW scores recursive method...");
            recursiveMethod(lowScoresToCheck, lowScorePlayerIndexes, scoreOrder);
        }
//        System.out.println("Reached end of recursive method!");
        return scoreOrder;
    }

    public Integer[][] reverseArray(Integer[][] array) {
        Integer[][] newArray = new Integer[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            newArray[array.length - 1 - i] = array[i];
        }
        return newArray;
    }
}
