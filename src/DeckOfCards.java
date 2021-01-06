import java.util.Random;
import java.util.List;
import java.util.ArrayList;

class DeckOfCards {
  public final String[] RANKS = {"2","3","4","5","6","7","8","9","10","j","q","k","a"};
  public final String[] SUITS = {"c","d","h","s"};

  public String[][] getDeck() {
    String[][] deck = new String[52][2];
    int index=0;
    for (int i=0 ; i < SUITS.length ; i++) {
      for (int j=0 ; j < RANKS.length ; j++) {
        deck[index][0]=RANKS[j];
        deck[index][1]=SUITS[i];
        index++;
      }
    }
    return deck;
  }

  public List<String[]> getDeckList() {
    String[][] deck = this.getDeck();
    List<String[]> deckList = new ArrayList<>();
    for(String[] card: deck) {
      deckList.add(card);
    }
    return deckList;
  }

  public List<String[]> getShuffledDeck() {
    List<String[]> deckList = new ArrayList<>();

    String[][] deck = this.getDeck();

    Random rand = new Random();

    for(int i=0; i < deck.length; i++) {
      int randeomIndexToSwap = rand.nextInt(deck.length);
      String tempRank=deck[randeomIndexToSwap][0];
      String tempSuit=deck[randeomIndexToSwap][1];
      deck[randeomIndexToSwap][0]=deck[i][0];
      deck[randeomIndexToSwap][1]=deck[i][1];
      deck[i][0]=tempRank;
      deck[i][1]=tempSuit;
    }

    for(String[] card : deck) {
      deckList.add(card);
    }
    return deckList;
  }
}
