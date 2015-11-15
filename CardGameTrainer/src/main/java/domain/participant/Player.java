package domain.participant;

import java.util.ArrayList;
import java.util.List;

import domain.decks.Card;

public class Player {
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }

    public void addCardToHand(Card aCard) {
        hand.add(aCard);
    }

    public List<Card> getHand() {
        return hand;
    }
}
