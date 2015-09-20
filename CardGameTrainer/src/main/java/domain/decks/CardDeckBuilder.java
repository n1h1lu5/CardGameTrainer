package domain.decks;

import java.util.ArrayList;
import java.util.List;

public class CardDeckBuilder {
    private List<Card> deck;

    public CardDeckBuilder() {
        deck = new ArrayList<Card>();
    }

    public void buildStandardCards() {
        for(Card.Type type : Card.Type.values()) {
            for(Card.Value value : Card.Value.values()) {
                deck.add(new Card(value, type));
            }
        }
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void clearDeck() {
        deck.clear();
    }

}
