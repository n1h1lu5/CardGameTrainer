package games;

import java.util.List;

import decks.Card;

public class BlackjackGameState {
    private static final int SCORE_BUST_LIMIT = 21;
    private static final int BLACKJACK_HAND_SIZE = 2;

    private BlackjackScoreCalculator scoreCalculator;

    public BlackjackGameState() {
        scoreCalculator = new BlackjackScoreCalculator();
    }

    public boolean playerBeatsHouse(List<Card> playerHand, List<Card> houseHand) {
        int playerScore = scoreCalculator.calculateScore(playerHand);
        int houseScore = scoreCalculator.calculateScore(houseHand);

        if (playerScore <= SCORE_BUST_LIMIT) {
            return (playerScore > houseScore) || (isScoreOverBustLimit(houseScore));
        } else {
            return false;
        }
    }

    public boolean hasBusted(List<Card> hand) {
        int score = scoreCalculator.calculateScore(hand);
        return isScoreOverBustLimit(score);
    }

    public boolean hasBlackjack(List<Card> hand) {
        int score = scoreCalculator.calculateScore(hand);
        return score == SCORE_BUST_LIMIT && hand.size() == BLACKJACK_HAND_SIZE;
    }

    public boolean playerAndHouseAreEven(List<Card> hand, List<Card> hand2) {
        return false;
    }

    private boolean isScoreOverBustLimit(int score) {
        return score > SCORE_BUST_LIMIT;
    }

    // For tests purpose only
    protected BlackjackGameState(BlackjackScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }
}
