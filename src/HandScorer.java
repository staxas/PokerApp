import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class HandScorer {

    DeckOfCards deckOfCards = new DeckOfCards();
    List<String> ranksList = Arrays.asList(deckOfCards.RANKS);
    List<String> suitsList = Arrays.asList(deckOfCards.SUITS);
    SortTwoDimensionalArray sortTwoDimensionalArray = new SortTwoDimensionalArray();

    public List<Integer> calculateHand(String[][] cards) {
        // Handscores:
        // High card: 0
        // One pair: 1
        // Two pair: 2
        // Three Of A Kind: 3
        // Straight: 4
        // Flush: 5
        // Full house: 6
        // Four Of A Kid: 7
        // Straight flush: 8
        // Royal flush: 9

        int handScore = 0;
        List<Integer> handValue = new ArrayList<>();

        int cardLen = cards.length;
        List<String[]> handSortedByRank = new ArrayList<>();

        Integer[] nrsOfARank = new Integer[13];
        Integer[] nrsOfASuit = new Integer[4];
        Arrays.fill(nrsOfARank, 0);
        Arrays.fill(nrsOfASuit, 0);

        sortTwoDimensionalArray.quickSortCards(cards, 0, cards.length - 1);

        // Find number of cards of each suite
        for (String[] card : cards) {
            if (suitsList.contains(card[1])) {
                nrsOfASuit[suitsList.indexOf(card[1])]++;
            }
        }

        // Find numbers of cards of each rank
        for (String[] card : cards) {
            if (ranksList.contains(card[0])) {
                nrsOfARank[ranksList.indexOf(card[0])]++;
            }
        }

        // Convert to lists to make searchable
        List<Integer> nrsOfASuitList = Arrays.asList(nrsOfASuit);
        List<Integer> nrsOfARankList = Arrays.asList(nrsOfARank);

        // Pre-check for pair(s), sets and full houses
        List<Integer> pairCards = new ArrayList<>();
        List<Integer> setCards = new ArrayList<>();
        int quadCards = -1;
        int nrOfPairs = 0;
        int nrOfSets = 0;
        int nrOfQuads = 0;
        boolean isQuads = false;
        for (int i = 0; i < nrsOfARank.length; i++) {
            if (nrsOfARank[i] == 2) {
                nrOfPairs++;
                pairCards.add(i);
            }
            if (nrsOfARank[i] == 3) {
                nrOfSets++;
                setCards.add(i);
            }
            if (nrsOfARank[i] == 4) {
                isQuads = true;
                quadCards = i;
            }
        }

        Collections.sort(setCards, Collections.reverseOrder());
        Collections.sort(pairCards, Collections.reverseOrder());

        if (nrOfPairs == 1 && nrOfSets <= 0) {
            // One Pair
            handScore = 1;
            handValue = new ArrayList<>();
            handValue.add(pairCards.get(0));
            int j = 1;
            for (int i = cardLen - 1; i >= 0; i--) {
                if (handValue.get(0) != ranksList.indexOf(cards[i][0])) {
                    handValue.add(ranksList.indexOf(cards[i][0]));
                    j++;
                }
                if (j >= 4) {
                    break;
                }
            }
        }
        if (nrOfPairs >= 2 && nrOfSets <= 0) {
            // Two Pair
            handScore = 2;
            handValue = new ArrayList<>();
            handValue.add(pairCards.get(0));
            handValue.add(pairCards.get(1));
            int j = 0;
            for (int i = cardLen - 1; i >= 0; i--) {
                if (handValue.get(0) != ranksList.indexOf(cards[i][0]) && handValue.get(1) != ranksList.indexOf(cards[i][0])) {
                    handValue.add(ranksList.indexOf(cards[i][0]));
                    j++;
                }
                if (j >= 1) {
                    break;
                }

            }
        }

        if (nrOfSets == 1 && nrOfPairs == 0) {
            // Three Of A Kind
            handScore = 3;
            handValue = new ArrayList<>();
            handValue.add(setCards.get(0));
            int j = 0;
            for (int i = cardLen - 1; i >= 0; i--) {
                if (setCards.get(0) != ranksList.indexOf(cards[i][0])) {
                    handValue.add(ranksList.indexOf(cards[i][0]));
                    j++;
                }
                if (j >= 2) {
                    break;
                }

            }
        }

        if (nrOfSets == 1 && nrOfPairs >= 1) {
            // Full House
            handScore = 6;
            handValue = new ArrayList<>();
            handValue.add(setCards.get(0));
            handValue.add(pairCards.get(0));
        }
        if (nrOfSets == 2) {
            // Full House
            handScore = 6;
            handValue = new ArrayList<>();
            Collections.sort(setCards, Collections.reverseOrder());
            handValue.add(setCards.get(0));
            handValue.add(setCards.get(1));
        }

        // Check for quads
        if (isQuads) {
            // Four Of A Kind
            handScore = 7;
            handValue = new ArrayList<>();
            handValue.add(quadCards);
            int j = 0;
            for (int i = cardLen - 1; i >= 0; i--) {
                if (handValue.get(0) != ranksList.indexOf(cards[i][0])) {
                    handValue.add(ranksList.indexOf(cards[i][0]));
                    j++;
                }
                if (j >= 1) {
                    break;
                }

            }
        }

        if (nrsOfASuitList.contains(5) || nrsOfASuitList.contains(6) || nrsOfASuitList.contains(7)) {
            // Flush
            handScore = 5;
            handValue = getFlushHighCards(nrsOfASuitList, cards);
        }

        List<Integer> straightCards = new ArrayList<>();

        String[] lastCard = cards[0];
        straightCards.add(ranksList.indexOf(lastCard[0]));

        int cardIter = 1;

        int startingIndex = 1;
        // If there is an ace in the hand, start straight search on that card
        if (cards[cards.length - 1][0].equals(ranksList.get(ranksList.size() - 1))) {
            lastCard = cards[cards.length - 1];
            startingIndex = 0;
        }

        // Start the search
        Integer[] suitsForStraightFlush = new Integer[4];
        Arrays.fill(suitsForStraightFlush, 0);
        suitsForStraightFlush[suitsList.indexOf(lastCard[1])]++;

        for (int i = startingIndex; i < cards.length; i++) {

            if (ranksList.indexOf(cards[i][0]) == (ranksList.indexOf(lastCard[0]) + 1)) {
                cardIter++;
                straightCards.add(ranksList.indexOf(cards[i][0]));
                suitsForStraightFlush[suitsList.indexOf(cards[i][1])]++;
            }
            if (ranksList.indexOf(cards[i][0]) == (ranksList.indexOf(lastCard[0]))) {
                suitsForStraightFlush[suitsList.indexOf(cards[i][1])]++;
            }
            // of the previous card is not the same as the current card, and no iterating card is found, reset counter
            if (ranksList.indexOf(cards[i][0]) != (ranksList.indexOf(lastCard[0])) && ranksList.indexOf(cards[i][0]) != (ranksList.indexOf(lastCard[0]) + 1)) {
                cardIter = 0;
            }
            // If five or more consecutive cards have been found
            if (cardIter >= 5) {

                handValue = new ArrayList<>();
                // If last card was an ace, and there are 5 suited cards the hand is a royal flush
                if (Arrays.asList(suitsForStraightFlush).contains(5) || Arrays.asList(suitsForStraightFlush).contains(6) || Arrays.asList(suitsForStraightFlush).contains(7)) {

                    if (ranksList.indexOf(cards[i][0]) == 12) {
                        // Royal Flush
                        handScore = 9;
                        handValue = getFlushHighCards(nrsOfASuitList, cards);
                    } else {
                        // Straight Flush
                        handScore = 8;
                        handValue = getFlushHighCards(nrsOfASuitList, cards);
                    }
                } else {
                    // Straight
                    handScore = 4;
                    int k = 0;
                    Collections.sort(straightCards);
                    Collections.reverse(straightCards);
                    for (Integer straightCard : straightCards) {
                        handValue.add(straightCard);
                        if (k == 4) {
                            break;
                        }
                        k++;
                    }
                }
            }
            lastCard = cards[i];
        }

        // In case no hands are made, the five highest cards make up the hand
        if (handScore == 0) {
            // High Card
            for (int i = cardLen - 1; i >= cardLen - 5; i--) {
                handValue.add(ranksList.indexOf(cards[i][0]));
            }
        }

        handValue.add(0, handScore);

        return handValue;
    }

    private List<Integer> getFlushHighCards(List<Integer> nrsOfASuitList, String[][] cards) {
        List<Integer> handValue = new ArrayList<>();
        cards = reverseArray(cards);
        int suitOfFlush = -1;
        for (int j = 5; j < 7; j++) {
            if (nrsOfASuitList.contains(j)) {
                suitOfFlush = nrsOfASuitList.indexOf(j);
            }
        }
        for (String[] card : cards) {
            if (suitsList.indexOf(card[1]) == suitOfFlush) {
                handValue.add(ranksList.indexOf(card[0]));
            }
        }
        return handValue;
    }

    public String[][] reverseArray(String[][] array) {
        String[][] newArray = new String[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            newArray[array.length - 1 - i] = array[i];
        }
        return newArray;
    }
}
