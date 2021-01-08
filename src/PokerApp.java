import java.util.List;
import java.util.ArrayList;

class PokerApp {
    static DeckOfCards deckOfCards = new DeckOfCards();
    static HandScorer handScorer = new HandScorer();
    static HandComparer handComparer = new HandComparer();

    static String[] possibleHands = new String[]{"High Card", "One Pair", "Two Pair", "Three Of A Kind", "Straight", "Flush", "Full House", "Four Of A Kind", "Straight Flush", "Royal Flush"};

    public static void main(String[] args) {

        List<String[]> deck = deckOfCards.getShuffledDeck();
        int nrOfPlayers = 5;
        List<String[][]> playersHoleCards = new ArrayList<>();
        List<String[][]> playersHands = new ArrayList<>();

        // Generate hands
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

        for (String[][] playerHoleCards : playersHoleCards) {
            String[][] playerHand = new String[7][2];
            for (int j = 0; j < playerHoleCards.length; j++) {
                playerHand[j] = playerHoleCards[j];
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

        int i = 0;
        for (List<Integer> score : scores) {
            System.out.print("Player " + i + " score: " + possibleHands[score.get(0)] + ", ");
            for (int singleScore : score) {
                System.out.print(singleScore + " ");
            }
            System.out.println();
            i++;
        }
        System.out.println();

        List<Integer[]> highScoreOrder = handComparer.compareHands(scores);

        int playerIndex = 1;
        for (Integer[] highScores : highScoreOrder) {
            System.out.print("Place " + playerIndex + ":[ ");
            for (Integer highScore : highScores) {
                System.out.print(highScore + " ");
            }
            playerIndex++;
            System.out.print("] ");
        }
    }
}
