package domain.games;

import domain.decks.Card;

import java.util.List;

public class EndPlayState extends BlackjackGameState {
    private static final int SCORE_BUST_LIMIT = 21;
    private static final int BLACKJACK_HAND_SIZE = 2;

    private BlackjackScoreCalculator scoreCalculator;

    public EndPlayState() {
        scoreCalculator = new BlackjackScoreCalculator();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 6");

        computeOutcome(blackjackGame);
    }

    private void computeOutcome(BlackjackGame blackjackGame) {
        if (playerAndHouseAreEven(blackjackGame.getPlayerHand(), blackjackGame.getHouseHand())) {
            blackjackGame.givePlayerEvenGains();
        } else if (hasBlackjack(blackjackGame.getPlayerHand())) {
            blackjackGame.givePlayerBlackjackGains();
        } else if (playerBeatsHouse(blackjackGame.getPlayerHand(), blackjackGame.getHouseHand())) {
            blackjackGame.givePlayerStandardGains();
        } else {
            blackjackGame.takePlayerBet();
        }
    }

    private boolean playerBeatsHouse(List<Card> playerHand, List<Card> houseHand) {
        int playerScore = scoreCalculator.calculateScore(playerHand);
        int houseScore = scoreCalculator.calculateScore(houseHand);

        return (playerScore <= SCORE_BUST_LIMIT) && ((playerScore > houseScore) || (isScoreOverBustLimit(houseScore)));
    }

    private boolean hasBlackjack(List<Card> hand) {
        int score = scoreCalculator.calculateScore(hand);
        return score == SCORE_BUST_LIMIT && hand.size() == BLACKJACK_HAND_SIZE;
    }

    private boolean playerAndHouseAreEven(List<Card> playerHand, List<Card> houseHand) {
        int playerScore = scoreCalculator.calculateScore(playerHand);
        int houseScore = scoreCalculator.calculateScore(houseHand);

        if (isScoreOverBustLimit(playerScore)) {
            return false;
        } else if (hasBlackjack(houseHand) && !hasBlackjack(playerHand)) {
            return false;
        }
        return playerScore == houseScore;
    }

    private boolean isScoreOverBustLimit(int score) {
        return score > SCORE_BUST_LIMIT;
    }

    // For test purpose only
    protected EndPlayState(BlackjackScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
    }
}
