import java.util.List;
import java.util.ArrayList;

class PokerApp {
    static DeckOfCards deckOfCards = new DeckOfCards();
    static HandScorer handScorer = new HandScorer();
    static HandComparer handComparer = new HandComparer();
    
    public static void main(String[] args) {

        List<String[]> deck = deckOfCards.getShuffledDeck();
        int nrOfPlayers = 5;
        List<String[][]> playersHoleCards = new ArrayList<>();
        List<String[][]> playersHands = new ArrayList<>();

        for (int j = 0; j < nrOfPlayers; j++) {
            String[][] playerHand = new String[7][2];

            String[][] playerHoleCards = new String[2][2];

            System.out.print("Player " + j + ": ");
            for (int i = 0; i < playerHoleCards.length; i++) {
                playerHoleCards[i] = deck.remove(0);
                System.out.print(PrintColored.printCardColors(playerHoleCards[i]) + " ");
                playerHand[i] = playerHoleCards[i];
            }
            playersHoleCards.add(playerHoleCards);
            System.out.println();
        }

        String[][] table = new String[5][2];

        for (int i = 0; i < table.length; i++) {
            table[i] = deck.remove(0);
            System.out.print(PrintColored.printCardColors(table[i]) + " ");
        }
        System.out.println();

        for (int i = 0; i < nrOfPlayers; i++) {
            String[][] playerHand = new String[7][2];
            for (int j = 0; j < playersHoleCards.get(i).length; j++) {
                playerHand[j] = playersHoleCards.get(i)[j];
            }
            for (int j = 0; j < table.length; j++) {
                playerHand[j + 2] = table[j];
            }
            playersHands.add(playerHand);
        }

        List<List<Integer>> scores = new ArrayList<>();

        for (String[][] hand : playersHands) {
            List<Integer> score = handScorer.calculateHand(hand);
            scores.add(score);
        }

        int i=0;
        for(List<Integer> score : scores) {
            System.out.print("Player " + i + " score: ");
            for (int singleScore : score) {
                System.out.print(singleScore + " ");
            }
            System.out.println();
            i++;
        }

        List<Integer[]> highScoreOrder = handComparer.compareHands(scores);

        for (Integer[] highScores : highScoreOrder) {
            for (Integer highScore : highScores) {
                System.out.print(highScore + " ");
            }
            System.out.println();
        }
    }
}
