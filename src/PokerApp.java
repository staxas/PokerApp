import java.util.*;

class PokerApp {
    static DeckOfCards deckOfCards = new DeckOfCards();
    static HandScorer handScorer = new HandScorer();
    static HandComparer handComparer = new HandComparer();


    public static void main(String[] args) {

        List<Card> deck = deckOfCards.getShuffledDeck();
        int nrOfPlayers = 5;
        List<Card[]> playersHoleCards = new ArrayList<>();
        List<Card[]> playersHands = new ArrayList<>();

        // Generate hands
        for (int j = 0; j < nrOfPlayers; j++) {
            Card[] playerHand = new Card[7];

            Card[] playerHoleCards = new Card[2];

            System.out.print("Player " + (j+1) + ": ");
            for (int i = 0; i < playerHoleCards.length; i++) {
                playerHoleCards[i] = deck.remove(0);
                System.out.print(PrintColored.printCardColors(playerHoleCards[i]) + " ");
                playerHand[i] = playerHoleCards[i];
            }
            playersHoleCards.add(playerHoleCards);
            System.out.println();
        }

        Card[] table = new Card[5];

        for (int i = 0; i < table.length; i++) {
            table[i] = deck.remove(0);
            System.out.print(PrintColored.printCardColors(table[i]) + " ");
        }
        System.out.println();

        for (Card[] playerHoleCards : playersHoleCards) {
            Card[] playerHand = new Card[7];
            for (int j = 0; j < playerHoleCards.length; j++) {
                playerHand[j] = playerHoleCards[j];
            }
            for (int j = 0; j < table.length; j++) {
                playerHand[j + 2] = table[j];
            }
            playersHands.add(playerHand);
        }

        List<List<Integer>> scores = new ArrayList<>();

        for (Card[] hand : playersHands) {
            List<Integer> score = handScorer.calculateHand(hand);
            scores.add(score);
        }

        int i = 0;
        for (List<Integer> score : scores) {
            System.out.println("Player " + (i+1) + " has " + getScoreCards(score));
            i++;
        }

        List<Integer[]> highScoreOrder = handComparer.compareHands(scores);

        int playerIndex = 1;
        for (Integer[] highScores : highScoreOrder) {
            System.out.print("Place " + playerIndex + ":[ ");
            for (Integer highScore : highScores) {
                System.out.print((highScore + 1) + " ");
            }
            playerIndex++;
            System.out.print("] ");
        }
    }

    public static String getScoreCards(List<Integer> score) {
        String[] possibleHands = new String[]{"High Card", "One Pair", "Two Pair", "Three Of A Kind", "Straight", "Flush", "Full House", "Four Of A Kind", "Straight Flush", "Royal Flush"};

        String str = possibleHands[score.get(0)];
        switch (score.get(0)) {
            // High Card, Flush
            case 0:
            case 5: {
                str +=  ": " + getCardName(deckOfCards.RANKS[score.get(1)])+ " high ";
                str += "( ";
                for (int i = 2; i < score.size(); i++) {
                    str += getCardName(deckOfCards.RANKS[score.get(i)]) + " ";
                }
                str += ")";
                break;
            }
            // One Pair, Three Of A Kind, Four Of A Kind
            case 1:
            case 3:
            case 7: {
                str += ": " + getCardName(deckOfCards.RANKS[score.get(1)])+ "s ";
                str += "( ";
                for (int i = 2; i < score.size(); i++) {
                    str += getCardName(deckOfCards.RANKS[score.get(i)]) + " ";
                }
                str += ")";
                break;
            }
            // Two pair
            case 2: {
                str += ": " + getCardName(deckOfCards.RANKS[score.get(1)]) + "s & ";
                str += getCardName(deckOfCards.RANKS[score.get(2)]) + "s ";
                str += "( ";
                for (int i = 3; i < score.size(); i++) {
                    str += getCardName(deckOfCards.RANKS[score.get(i)]) + " ";
                }
                str += ")";
                break;
            }
            // Full House
            case 6: {
                str += ": " + getCardName(deckOfCards.RANKS[score.get(1)]) + "s over ";
                str += getCardName(deckOfCards.RANKS[score.get(2)]) + "s ";
                break;
            }
            // Straight, Straight Flush
            case 4:
            case 8: {
                str += ": " + getCardName(deckOfCards.RANKS[score.get(1)])+ " high ";
                break;
            }
            // Royal Flush
            case 9:
                break;

        }
        return str;
    }
    public static String getCardName(String cardId) {
        String cardName=cardId;
        Map<String, String> cardNames  = new HashMap<String, String>() {{
            put("2", "deuce");
            put("j", "jack");
            put("q", "queen");
            put("k", "king");
            put("a", "ace");
        }};
        if(cardNames.containsKey(cardId)) {
            cardName=cardNames.get(cardId);
        }
        return cardName;
    }
}
