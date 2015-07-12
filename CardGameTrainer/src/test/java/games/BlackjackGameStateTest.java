package games;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import decks.Card;
import static org.mockito.Mockito.*;

public class BlackjackGameStateTest {
    private BlackjackGameState gameState;

    private BlackjackScoreCalculator scoreCalculator;

    @Before
    public void setup() {
        scoreCalculator = mock(BlackjackScoreCalculator.class);
        gameState = new BlackjackGameState(scoreCalculator);
    }

    @Test
    public void canCreateABlackjackGameState() {
        new BlackjackGameState();
    }

    @Test
    public void givenTheHandScoreIs21AndIsMadeOf2Cards_thenThisIsABlackjack() {
        // given
        List<Card> blackjackHand = createBlackjackHand();

        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(21);

        // then
        Assert.assertTrue(gameState.hasBlackjack(blackjackHand));
    }

    @Test
    public void givenThenHandScoreIs21AndIsMadeOfMoreThan2Cards_thenThisIsNotABlackjack() {
        // given
        List<Card> a21Hand = new ArrayList<Card>();
        a21Hand.add(new Card(1, Card.Type.CLOVER));
        a21Hand.add(new Card(1, Card.Type.CLOVER));
        a21Hand.add(new Card(9, Card.Type.CLOVER));

        when(scoreCalculator.calculateScore(a21Hand)).thenReturn(21);

        // then
        Assert.assertFalse(gameState.hasBlackjack(a21Hand));
    }

    @Test
    public void givenThenHandScoreIsNot21_thenThisIsNotABlackjack() {
        // given
        List<Card> notA21Hand = new ArrayList<Card>();
        notA21Hand.add(new Card(1, Card.Type.CLOVER));
        notA21Hand.add(new Card(9, Card.Type.CLOVER));

        when(scoreCalculator.calculateScore(notA21Hand)).thenReturn(20);

        // then
        Assert.assertFalse(gameState.hasBlackjack(notA21Hand));
    }

    @Test
    public void givenThePlayerHasBustedButNotTheHouse_thenThePlayerHasLost() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(30);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(20);

        // then
        Assert.assertFalse(gameState.playerBeatsHouse(aBustingHand, aNotBustingHand));
    }

    @Test
    public void givenThePlayerHasNotBustedAndHasABetterScoreThenTheHouse_thenThePlayerHasWon() {
        // given
        List<Card> aWinningHand = createAWinningNotBustingHand();
        List<Card> aLoosingHand = createALosingNotBustingHand();

        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(20);
        when(scoreCalculator.calculateScore(aLoosingHand)).thenReturn(4);

        // then
        Assert.assertTrue(gameState.playerBeatsHouse(aWinningHand, aLoosingHand));
    }

    @Test
    public void givenThePlayerHasNotBustedAndHasNotABetterScoreThenTheHouse_thenThePlayerHasLost() {
        // given
        List<Card> aWinningHand = createAWinningNotBustingHand();
        List<Card> aLoosingHand = createALosingNotBustingHand();

        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(20);
        when(scoreCalculator.calculateScore(aLoosingHand)).thenReturn(4);

        // then
        Assert.assertFalse(gameState.playerBeatsHouse(aLoosingHand, aWinningHand));
    }

    @Test
    public void givenThePlayerHasNotBustedButTheHouseDid_thenThePlayerHasWon() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aWinningHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(30);
        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(20);

        // then
        Assert.assertTrue(gameState.playerBeatsHouse(aWinningHand, aBustingHand));
    }

    @Test
    public void givenAHandWithAScoreOver21_thenTheHandHasBusted() {
        // given
        List<Card> aBustingHand = createBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(30);

        // then
        Assert.assertTrue(gameState.hasBusted(aBustingHand));
    }

    @Test
    public void givenAHandWithAScoreUnder21_thenTheHandHasNotBusted() {
        // given
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(20);

        // then
        Assert.assertFalse(gameState.hasBusted(aNotBustingHand));
    }

    private List<Card> createBustingHand() {
        List<Card> bustingHand = new ArrayList<Card>();
        bustingHand.add(new Card(10, Card.Type.CLOVER));
        bustingHand.add(new Card(10, Card.Type.CLOVER));
        bustingHand.add(new Card(10, Card.Type.CLOVER));
        return bustingHand;
    }

    private List<Card> createAWinningNotBustingHand() {
        List<Card> aNotBustingHand = new ArrayList<Card>();
        aNotBustingHand.add(new Card(10, Card.Type.CLOVER));
        aNotBustingHand.add(new Card(10, Card.Type.CLOVER));
        return aNotBustingHand;
    }

    private List<Card> createALosingNotBustingHand() {
        List<Card> aNotBustingHand = new ArrayList<Card>();
        aNotBustingHand.add(new Card(2, Card.Type.CLOVER));
        aNotBustingHand.add(new Card(2, Card.Type.CLOVER));

        return aNotBustingHand;
    }

    private List<Card> createBlackjackHand() {
        List<Card> blackjackHand = new ArrayList<Card>();
        blackjackHand.add(new Card(1, Card.Type.CLOVER));
        blackjackHand.add(new Card(10, Card.Type.CLOVER));
        return blackjackHand;
    }
}
