package domain.games;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.decks.Card;
import domain.games.BlackjackGameState;
import domain.games.BlackjackScoreCalculator;
import static org.mockito.Mockito.*;

public class BlackjackGameStateTest {
    private static final int MAXIMUM_VALUE_BEFORE_BUST = 21;
    private static final int A_BUSTING_SCORE = 30;
    private static final int A_WINNING_SCORE = 20;
    private static final int A_LOSING_SCORE = 4;

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

        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        // then
        Assert.assertTrue(gameState.hasBlackjack(blackjackHand));
    }

    @Test
    public void givenThenHandScoreIs21AndIsMadeOfMoreThan2Cards_thenThisIsNotABlackjack() {
        // given
        List<Card> a21Hand = new ArrayList<Card>();
        a21Hand.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        a21Hand.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        a21Hand.add(new Card(Card.Value.NINE, Card.Type.CLUB));

        when(scoreCalculator.calculateScore(a21Hand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        // then
        Assert.assertFalse(gameState.hasBlackjack(a21Hand));
    }

    @Test
    public void givenThenHandScoreIsNot21_thenThisIsNotABlackjack() {
        // given
        List<Card> notA21Hand = new ArrayList<Card>();
        notA21Hand.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        notA21Hand.add(new Card(Card.Value.NINE, Card.Type.CLUB));

        when(scoreCalculator.calculateScore(notA21Hand)).thenReturn(A_WINNING_SCORE);

        // then
        Assert.assertFalse(gameState.hasBlackjack(notA21Hand));
    }

    @Test
    public void givenThePlayerHasBustedButNotTheHouse_thenThePlayerHasLost() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);

        // then
        Assert.assertFalse(gameState.playerBeatsHouse(aBustingHand, aNotBustingHand));
    }

    @Test
    public void givenThePlayerHasNotBustedAndHasABetterScoreThenTheHouse_thenThePlayerHasWon() {
        // given
        List<Card> aWinningHand = createAWinningNotBustingHand();
        List<Card> aLoosingHand = createALosingNotBustingHand();

        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(A_WINNING_SCORE);
        when(scoreCalculator.calculateScore(aLoosingHand)).thenReturn(A_LOSING_SCORE);

        // then
        Assert.assertTrue(gameState.playerBeatsHouse(aWinningHand, aLoosingHand));
    }

    @Test
    public void givenThePlayerHasNotBustedAndHasNotABetterScoreThenTheHouse_thenThePlayerHasLost() {
        // given
        List<Card> aWinningHand = createAWinningNotBustingHand();
        List<Card> aLoosingHand = createALosingNotBustingHand();

        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(A_WINNING_SCORE);
        when(scoreCalculator.calculateScore(aLoosingHand)).thenReturn(A_LOSING_SCORE);

        // then
        Assert.assertFalse(gameState.playerBeatsHouse(aLoosingHand, aWinningHand));
    }

    @Test
    public void givenThePlayerHasNotBustedButTheHouseDid_thenThePlayerHasWon() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aWinningHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);
        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(A_WINNING_SCORE);

        // then
        Assert.assertTrue(gameState.playerBeatsHouse(aWinningHand, aBustingHand));
    }

    @Test
    public void givenAHandWithAScoreOver21_thenTheHandHasBusted() {
        // given
        List<Card> aBustingHand = createBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);

        // then
        Assert.assertTrue(gameState.hasBusted(aBustingHand));
    }

    @Test
    public void givenAHandWithAScoreUnder21_thenTheHandHasNotBusted() {
        // given
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);

        // then
        Assert.assertFalse(gameState.hasBusted(aNotBustingHand));
    }

    @Test
    public void givenThePlayerAndTheHouseHandsHaveTheSameScoreButScoreIsNotOver21_thenBothAreEven() {
        // given
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);

        // then
        Assert.assertTrue(gameState.playerAndHouseAreEven(aNotBustingHand, aNotBustingHand));
    }

    @Test
    public void givenThePlayerAndTheHouseHandsDoNotHaveTheSameScore_thenBothAreNotEven() {
        // given
        List<Card> aNotBustingHand = createALosingNotBustingHand();
        List<Card> anotherNotBustingHand = createAWinningNotBustingHand();

        int aLowerScore = A_WINNING_SCORE - 1;
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);
        when(scoreCalculator.calculateScore(anotherNotBustingHand)).thenReturn(aLowerScore);

        // then
        Assert.assertFalse(gameState.playerAndHouseAreEven(anotherNotBustingHand, aNotBustingHand));
    }

    @Test
    public void givenThePlayerAndTheHouseHasBusted_thenThePlayerAndTheHouseAreNotEven() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_BUSTING_SCORE);

        // then
        Assert.assertFalse(gameState.playerAndHouseAreEven(aBustingHand, aNotBustingHand));
    }

    @Test
    public void givenTheHouseHasABlackjackAndThePlayerHasA21ButNoBlackjack_thenThePlayerIsNotEvenWithTheHouse() {
        // given
        List<Card> a21Hand = createALosingNotBustingHand();
        List<Card> blackjackHand = createBlackjackHand();

        when(scoreCalculator.calculateScore(a21Hand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);
        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        // then
        Assert.assertFalse(gameState.playerAndHouseAreEven(a21Hand, blackjackHand));
    }

    @Test
    public void givenTheHouseAndThePlayerHaveABlackjack_thenThePlayerIsEvenWithTheHouse() {
        // given
        List<Card> blackjackHand = createBlackjackHand();
        List<Card> anotherBlackjackHand = createBlackjackHand();

        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);
        when(scoreCalculator.calculateScore(anotherBlackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        // then
        Assert.assertTrue(gameState.playerAndHouseAreEven(blackjackHand, anotherBlackjackHand));
    }

    private List<Card> createBustingHand() {
        List<Card> bustingHand = new ArrayList<Card>();
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return bustingHand;
    }

    private List<Card> createAWinningNotBustingHand() {
        List<Card> aNotBustingHand = new ArrayList<Card>();
        aNotBustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        aNotBustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return aNotBustingHand;
    }

    private List<Card> createALosingNotBustingHand() {
        List<Card> aNotBustingHand = new ArrayList<Card>();
        aNotBustingHand.add(new Card(Card.Value.TWO, Card.Type.CLUB));
        aNotBustingHand.add(new Card(Card.Value.TWO, Card.Type.CLUB));
        aNotBustingHand.add(new Card(Card.Value.TWO, Card.Type.CLUB));

        return aNotBustingHand;
    }

    private List<Card> createBlackjackHand() {
        List<Card> blackjackHand = new ArrayList<Card>();
        blackjackHand.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        blackjackHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return blackjackHand;
    }
}
