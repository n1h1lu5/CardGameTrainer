package domain.games;

import domain.decks.BlackjackShoe;
import domain.decks.Card;
import domain.participant.PlayerGameEngine;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private static final int SCORE_BUST_LIMIT = 21;

    private PlayerGameEngine player;
    private BlackjackShoe shoe;
    private BlackjackGameState currentGameState;

    public BlackjackGame(PlayerGameEngine player, BlackjackShoe shoe, BlackjackGameState currentGameState) {
        this.player = player;
        this.currentGameState = currentGameState;
        this.shoe = shoe;
    }

    public void update() {
        this.currentGameState.update(this);
        this.player.resetStates();
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
        int score = BlackjackScoreCalculator.calculateScore(this.player.getHand());
        return isScoreOverBustLimit(score);
    }

    private boolean isScoreOverBustLimit(int score) {
        return score > SCORE_BUST_LIMIT;
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
        this.player.receiveCard(this.shoe.giveTopCard());
    }

    public void giveHouseACard() {

    }

    public boolean currentPlayerWantsCard() {
        return this.player.wantsACard();
    }

    public boolean currentPlayerStoppedHisTurn() {
        return this.player.wantsToFinishTurn();
    }
}
