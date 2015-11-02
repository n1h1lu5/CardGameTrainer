package domain.games;

import domain.decks.Card;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private static final int SCORE_BUST_LIMIT = 21;

    private BlackjackGameState currentGameState;
    private BlackjackScoreCalculator scoreCalculator; // TODO: Should be in player

    public BlackjackGame() {
        currentGameState = new StartGameState();
        scoreCalculator = new BlackjackScoreCalculator();
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

    public boolean hasBusted(List<Card> hand) {
        int score = scoreCalculator.calculateScore(hand);
        return isScoreOverBustLimit(score);
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

    private boolean isScoreOverBustLimit(int score) {
        return score > SCORE_BUST_LIMIT;
    }

    public BlackjackGame(BlackjackGameState currentGameState, BlackjackScoreCalculator scoreCalculator) {
        this.currentGameState = currentGameState;
        this.scoreCalculator = scoreCalculator;
    }
}
