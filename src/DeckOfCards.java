import java.util.Random;
import java.util.List;
import java.util.ArrayList;

class Card {
  private String rank;
  private String suite;

  public Card(String rank, String suite) {
    this.rank = rank;
    this.suite = suite;
  }

  public Card(Card card) {
    this.rank = card.getRank();
    this.suite = card.getSuite();
  }

  public String getRank() {
    return rank;
  }

  public String getSuite() {
    return suite;
  }

}

class Deck {
  private Card[] deck;

  public Deck() {
    this.deck = new Card[52];
  }
  
  public Deck(Card[] deck) {
    this.deck = deck;
  }

  public Card[] getDeck() {
    return deck;
  }

  public void setDeck(Card[] deck) {
    this.deck = deck;
  }
}

class DeckOfCards {
  public final String[] RANKS = {"2","3","4","5","6","7","8","9","10","j","q","k","a"};
  public final String[] SUITS = {"c","d","h","s"};

  public Deck getDeck() {
    Deck deck = new Deck();
    int index=0;
    for (int i=0 ; i < SUITS.length ; i++) {
      for (int j=0 ; j < RANKS.length ; j++) {
        deck.getDeck()[index]=new Card(RANKS[j], SUITS[i]);
        index++;
      }
    }
    return deck;
  }

  public List<Card> getDeckList() {
    Deck deck = this.getDeck();
    List<Card> deckList = new ArrayList<>();
    for(Card card: deck.getDeck()) {
      deckList.add(card);
    }
    return deckList;
  }

  public List<Card> getShuffledDeck() {
    List<Card> deckList = new ArrayList<>();

    Deck deck = this.getDeck();
    Random rand = new Random();

    for(int i=0; i < deck.getDeck().length; i++) {
      int randomIndexToSwap = rand.nextInt(deck.getDeck().length);
      Card tempCard = new Card(deck.getDeck()[randomIndexToSwap]);
      deck.getDeck()[randomIndexToSwap]=deck.getDeck()[i];
      deck.getDeck()[i]=tempCard;
    }

    for(Card card : deck.getDeck()) {
      deckList.add(card);
    }
    return deckList;
  }
}
