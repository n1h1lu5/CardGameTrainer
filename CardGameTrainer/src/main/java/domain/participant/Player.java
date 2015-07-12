package domain.participant;

import java.util.ArrayList;
import java.util.List;

import domain.decks.Card;

public class Player {

    public List<Card> getHand() {
        List<Card> hand = new ArrayList<Card>();

        return hand;
    }

    public void receiveCard(int card) {
    }

    public int decideBet() {
        return 1;
    }

    public void receiveGains(int capture) {

    }

    public void loseBet(Integer capture) {

    }

    public boolean wantsANewCard() {
        return false;
    }

}
