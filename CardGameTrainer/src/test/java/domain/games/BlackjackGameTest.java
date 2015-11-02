package domain.games;

import domain.decks.Card;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlackjackGameTest {
    private static final int A_BUSTING_SCORE = 30;
    private static final int A_NOT_BUSTING_SCORE = 20;

    private BlackjackGame blackjackGame;

    private BlackjackScoreCalculator scoreCalculator;
    private StartGameState startState;

    @Before
    public void setup() {
        scoreCalculator = mock(BlackjackScoreCalculator.class);
        startState = mock(StartGameState.class);
        blackjackGame = new BlackjackGame(startState, scoreCalculator);
    }

    @Test
    public void givenAHandWithAScoreOver21_thenTheHandHasBusted() {
        // given
        List<Card> aBustingHand = createBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);

        // then
        Assert.assertTrue(blackjackGame.hasBusted(aBustingHand));
    }

    @Test
    public void givenAHandWithAScoreUnder21_thenTheHandHasNotBusted() {
        // given
        List<Card> aNotBustingHand = createAotBustingHand();

        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_NOT_BUSTING_SCORE);

        // then
        Assert.assertFalse(blackjackGame.hasBusted(aNotBustingHand));
    }

    private List<Card> createBustingHand() {
        List<Card> bustingHand = new ArrayList<Card>();
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return bustingHand;
    }

    private List<Card> createAotBustingHand() {
        List<Card> aNotBustingHand = new ArrayList<Card>();
        aNotBustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        aNotBustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return aNotBustingHand;
    }
}
