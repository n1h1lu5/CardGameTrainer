package domain.games;

import domain.decks.Card;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private BlackjackGameState currentGameState;

    public BlackjackGame() {
        currentGameState = new StartGameState();
    }

    public void update() {
        currentGameState.update(this);
    }

    public void changeState(BlackjackGameState newState) {
        // If needed, call onEnter here
        currentGameState = newState;
    }

    public List<Card> getPlayerHand() {
        return new ArrayList<Card>();
    }

    public List<Card> getHouseHand() {
        return new ArrayList<Card>();
    }

    public void givePlayerEvenGains() {

    }

    public void givePlayerBlackjackGains() {

    }

    public void givePlayerStandardGains() {

    }

    public void takePlayerBet() {

    }

    public void givePlayerACard() {

    }

    public void giveHouseACard() {

    }
}
