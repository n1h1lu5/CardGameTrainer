package domain.games;

import java.util.ArrayList;
import java.util.List;

import domain.decks.Card;

public class BlackjackScoreCalculator {
    private static final int SCORE_BUST_LIMIT = 21;

    public int calculateScore(List<Card> hand) {
        int score = 0;
        List<Card> aces = transfertAcesFromHandToAceList(hand);
        
        for (Card card : hand)
            score += card.number;
        
        if (aces.size() != 0) {
            score += calculateAcesScore(aces, score);
        }
        
        return score;
    }
    
    private int calculateAcesScore(List<Card> aces, final int score) {
        int acesAllOnesScore = calculateAllAcesAsOnes(aces);
        int acesAllOnesButOneScore = calculateAcesAsOnesButOne(aces);

        if (acesAllOnesButOneScore + score <= SCORE_BUST_LIMIT)
            return acesAllOnesButOneScore;
        else
            return acesAllOnesScore;
    }

    private int calculateAcesAsOnesButOne(List<Card> aces) {
        int acesAllOnesButOne = 0;
        for (int i = 0; i < aces.size(); i++) {
            if (i == 0)
                acesAllOnesButOne += 11;
            else
                acesAllOnesButOne += 1;
        }
        return acesAllOnesButOne;
    }

    private int calculateAllAcesAsOnes(List<Card> aces) {
        return aces.size();
    }

    private List<Card> transfertAcesFromHandToAceList(List<Card> hand) {
        List<Card> aces = new ArrayList<Card>();

        for (Card card : hand) {
            if (card.number == 1)
                aces.add(card);
        }
        for (Card ace : aces)
            hand.remove(ace);

        return aces;
    }
}
