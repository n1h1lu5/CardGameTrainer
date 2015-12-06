package domain.participant;

import java.util.ArrayList;
import java.util.List;

import domain.decks.Card;

public class Player implements PlayerGameEngine, PlayerAI {
    private boolean wantsACard;
    private boolean wantsToFinishTurn;

    private List<Card> hand;

    public Player() {
        this.wantsACard = false;
        this.wantsToFinishTurn = false;

        this.hand = new ArrayList<Card>();
    }

    public void addCardToHand(Card aCard) {
        hand.add(aCard);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void askACard() {
        this.wantsACard = true;
    }

    public boolean wantsACard() {
        return this.wantsACard;
    }

    public void askToFinishTurn() {
        this.wantsToFinishTurn = true;
    }

    public boolean wantsToFinishTurn() {
        return this.wantsToFinishTurn;
    }

    public void resetStates() {
        this.wantsACard = false;
        this.wantsToFinishTurn = false;
    }

    public void receiveCard(Card aCard) {
        hand.add(aCard);
    }
}
