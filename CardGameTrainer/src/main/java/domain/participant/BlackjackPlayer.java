package domain.participant;

import domain.games.BlackjackScoreCalculator;

public class BlackjackPlayer {
    private static final int SCORE_BUST_LIMIT = 21;

    private Player player;
    private BlackjackScoreCalculator scoreCalculator;

    public BlackjackPlayer(Player player) {
        this.player = player;
        this.scoreCalculator = new BlackjackScoreCalculator();
    }

    public boolean hasBusted() {
        int score = scoreCalculator.calculateScore(player.hand);
        return isScoreOverBustLimit(score);
    }

    private boolean isScoreOverBustLimit(int score) {
        return score > SCORE_BUST_LIMIT;
    }

    //For test purpose only
    protected BlackjackPlayer(Player player, BlackjackScoreCalculator scoreCalculator) {
        this.player = player;
        this.scoreCalculator = scoreCalculator;
    }
}
