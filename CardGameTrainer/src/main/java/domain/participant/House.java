package domain.participant;

import java.util.ArrayList;
import java.util.List;

import domain.decks.Card;

public class House {

    public List<Card> getHand() {
        List<Card> hand = new ArrayList<Card>();
        return hand;
    }

    public boolean wantsNewCard() {
        return false;
    }

    public void receiveCard(Card card) {
        
    }

}
