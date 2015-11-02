package domain.games;

import domain.decks.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class EndPlayStateTest {
    private static final int MAXIMUM_VALUE_BEFORE_BUST = 21;
    private static final int A_BUSTING_SCORE = 30;
    private static final int A_WINNING_SCORE = 20;
    private static final int A_LOSING_SCORE = 4;

    private EndPlayState endPlayState;

    private BlackjackGame blackjackGame;
    private BlackjackScoreCalculator scoreCalculator;

    @Before
    public void setup() {
        blackjackGame = mock(BlackjackGame.class);
        scoreCalculator = mock(BlackjackScoreCalculator.class);

        endPlayState = new EndPlayState(scoreCalculator);
    }

    @Test
    public void givenTheHandScoreIs21AndIsMadeOf2Cards_thenThisIsABlackjack() {
        // given
        List<Card> blackjackHand = createBlackjackHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(blackjackHand);
        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerBlackjackGains();
    }

    @Test
    public void givenThenHandScoreIs21AndIsMadeOfMoreThan2Cards_thenThisIsNotABlackjack() {
        // given
        List<Card> a21Hand = new ArrayList<Card>();
        a21Hand.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        a21Hand.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        a21Hand.add(new Card(Card.Value.NINE, Card.Type.CLUB));

        //when
        endPlayState.update(blackjackGame);

        when(blackjackGame.getPlayerHand()).thenReturn(a21Hand);
        when(scoreCalculator.calculateScore(a21Hand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        // then
        verify(blackjackGame, times(0)).givePlayerBlackjackGains();
    }

    @Test
    public void givenThePlayerHasBustedButNotTheHouse_thenThePlayerHasLost() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(aBustingHand);
        when(blackjackGame.getHouseHand()).thenReturn(aNotBustingHand);
        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).takePlayerBet();
    }

    @Test
    public void givenThePlayerHasNotBustedAndHasABetterScoreThenTheHouse_thenThePlayerHasWon() {
        // given
        List<Card> aWinningHand = createAWinningNotBustingHand();
        List<Card> aLoosingHand = createALosingNotBustingHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(aWinningHand);
        when(blackjackGame.getHouseHand()).thenReturn(aLoosingHand);
        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(A_WINNING_SCORE);
        when(scoreCalculator.calculateScore(aLoosingHand)).thenReturn(A_LOSING_SCORE);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerStandardGains();
    }

    @Test
    public void givenThePlayerHasNotBustedAndHasNotABetterScoreThenTheHouse_thenThePlayerHasLost() {
        // given
        List<Card> aWinningHand = createAWinningNotBustingHand();
        List<Card> aLoosingHand = createALosingNotBustingHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(aLoosingHand);
        when(blackjackGame.getHouseHand()).thenReturn(aWinningHand);
        when(scoreCalculator.calculateScore(aWinningHand)).thenReturn(A_WINNING_SCORE);
        when(scoreCalculator.calculateScore(aLoosingHand)).thenReturn(A_LOSING_SCORE);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).takePlayerBet();
    }

    @Test
    public void givenThePlayerHasNotBustedButTheHouseDid_thenThePlayerHasWon() {
        // given
        List<Card> aBustingHand = createBustingHand();
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(aNotBustingHand);
        when(blackjackGame.getHouseHand()).thenReturn(aBustingHand);
        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(A_BUSTING_SCORE);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerStandardGains();
    }

    @Test
    public void givenThePlayerAndTheHouseHandsHaveTheSameScoreButScoreIsNotOver21_thenBothAreEven() {
        // given
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(aNotBustingHand);
        when(blackjackGame.getHouseHand()).thenReturn(aNotBustingHand);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(A_WINNING_SCORE);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerEvenGains();
    }

    @Test
    public void givenTheHouseAndThePlayerHaveABlackjack_thenThePlayerIsEvenWithTheHouse() {
        // given
        List<Card> blackjackHand = createBlackjackHand();
        List<Card> anotherBlackjackHand = createBlackjackHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(blackjackHand);
        when(blackjackGame.getHouseHand()).thenReturn(anotherBlackjackHand);
        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);
        when(scoreCalculator.calculateScore(anotherBlackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerEvenGains();
    }

    @Test
    public void givenTheHousehasABlackjackButNotThePlayer_thenThePlayerHasLost() {
        // given
        List<Card> blackjackHand = createBlackjackHand();
        List<Card> aLosingHand = createALosingNotBustingHand();

        // when
        when(blackjackGame.getPlayerHand()).thenReturn(aLosingHand);
        when(blackjackGame.getHouseHand()).thenReturn(blackjackHand);
        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);
        when(scoreCalculator.calculateScore(aLosingHand)).thenReturn(MAXIMUM_VALUE_BEFORE_BUST);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).takePlayerBet();
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
