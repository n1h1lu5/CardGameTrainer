package games;

import java.util.List;

public class BlackjackScoreCalculator {

    public int calculateScore(List<Integer> hand) {
        int score = 0;
        for (int card : hand)
            score += card;
        return score;
    }
}
