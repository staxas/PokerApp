import java.util.List;
import java.util.ArrayList;

class PokerApp {


    public static void main(String[] args) {
        DeckOfCards deckOfCards = new DeckOfCards();
        List<String[]> deck = deckOfCards.getShuffledDeck();
//        List<String[]> fullDeck = deckOfCards.getDeckList();
//        int x = 0;
//        for (String[] card : fullDeck) {
//            if (x % 13 == 0) {
//                System.out.println();
//            }
//            System.out.print(x + ":" + card[0] + card[1] + " ");
//            x++;
//        }
//        System.out.println();

        int nrOfPlayers = 2;
        List<String[][]> playersHoleCards = new ArrayList<>();
        List<String[][]> playersHands = new ArrayList<>();

//        playersHands.add(new String[][]{fullDeck.get(33), fullDeck.get(34), fullDeck.get(45), fullDeck.get(47), fullDeck.get(48), fullDeck.get(23), fullDeck.get(24)});

        for (int j = 0; j < nrOfPlayers; j++) {
            String[][] playerHand = new String[7][2];

            String[][] playerHoleCards = new String[2][2];

            System.out.println("Player " + j + ": ");
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
//        for (String[] card : playersHands.get(0)) {
//            System.out.print(PrintColored.printCardColors(card) + " ");
//        }

        HandScorer handScorer = new HandScorer();
        for (int i = 0; i < nrOfPlayers; i++) {
            System.out.print("Player " + i + " score: ");

            List<Integer> handScore = handScorer.calculateHand(playersHands.get(i));
//        List<Integer> handScore = handScorer.calculateHand(playersHands.get(0));
            for (int score : handScore) {
                System.out.print(score + " ");
            }
            System.out.println();
        }
    }
}
