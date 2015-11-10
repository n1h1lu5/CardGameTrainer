package domain.games;

import domain.decks.Card;
import domain.participant.BlackjackPlayer;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private BlackjackPlayer player;
    private BlackjackGameState currentGameState;

    public BlackjackGame(BlackjackPlayer player) {
        this.player = player;
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

    public boolean hasPlayerBusted() {
        return player.hasBusted();
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

    // For test purpose only
    protected BlackjackGame(BlackjackPlayer player, BlackjackGameState currentGameState) {
        this.player = player;
        this.currentGameState = currentGameState;
    }
}
