package domain.participant;

import domain.decks.Card;

import java.util.List;

public interface PlayerGameEngine {
    void receiveCard(Card aCard);
    List<Card> getHand();

    boolean wantsACard();
    boolean wantsToFinishTurn();

    void resetStates();
}
