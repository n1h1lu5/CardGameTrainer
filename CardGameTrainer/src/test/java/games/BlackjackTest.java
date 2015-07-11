package games;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import decks.Card;
import static org.mockito.Mockito.*;
import participant.House;
import participant.Player;

public class BlackjackTest {
    private static final int PLAYER_BET = 10;
    private static final float BLACKJACK_PAY_FACTOR = 1.5f;

    private BlackjackScoreCalculator scoreCalculator;
    private Player player;
    private House house;

    @Before
    public void setup() {
        scoreCalculator = mock(BlackjackScoreCalculator.class);
        player = mock(Player.class);
        house = mock(House.class);
    }

    @Test
    public void givenANewPlayWithSinglePlayer_whenItsTheBeginingOfTheGame_thenThePlayerMustGiveHisBetThenReceiveTwoCards() {
        // given // when
        startANewSinglePlayerGame();

        // then
        InOrder order = inOrder(player);

        order.verify(player).decideBet();
        order.verify(player, times(2)).receiveCard(anyInt());
    }

    @Test
    public void givenANewPlayWithSinglePlayer_whenItsTheBeginingOfTheGame_thenThePlayerReceiveHisCardsFirstAndTheHouseReceiveItsCardsLast() {
        // given // when
        startANewSinglePlayerGame();

        // then
        InOrder order = inOrder(player, house);

        order.verify(player, times(2)).receiveCard(anyInt());
        order.verify(house, times(2)).receiveCard(anyInt());
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItBusts() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenReturn(true);
        when(scoreCalculator.calculateScore(anyListOf(Card.class))).thenAnswer(bustAfterTakingNewCards3Times());

        blackjack.askHouseToPlay();

        // then
        verify(house, times(5)).receiveCard(anyInt());
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItDoesNotWantAnyMore() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenAnswer(stopTakingNewCardsAfter2Times());
        when(house.getHand()).thenReturn(createALosingNotBustingHand());

        blackjack.askHouseToPlay();

        // then
        verify(house, times(4)).receiveCard(anyInt());
    }

    @Test
    public void givenThePlayerHasReceivedHisFirstTwoCards_whenItsThePlayerTurn_thenHeCanAskForANewCardUntilHeDoesNotWantAnyMore() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(player.wantsANewCard()).thenAnswer(stopTakingNewCardsAfter2Times());
        when(player.getHand()).thenReturn(createALosingNotBustingHand());

        blackjack.askPlayerToPlay();

        // then
        verify(player, times(4)).receiveCard(anyInt());
    }

    @Test
    public void givenThePlayerHasReceivedHisFirstTwoCards_whenItsThePlayerTurn_thenHeCanAskForANewCardUntilHeHasBusted() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(player.wantsANewCard()).thenReturn(true);
        when(scoreCalculator.calculateScore(anyListOf(Card.class))).thenAnswer(bustAfterTakingNewCards3Times());

        blackjack.askPlayerToPlay();

        // then
        verify(player, times(5)).receiveCard(anyInt());
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerBust_thenThePlayerHasLostHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> bustingHand = createBustingHand();
        when(player.getHand()).thenReturn(bustingHand);
        when(scoreCalculator.calculateScore(bustingHand)).thenReturn(22);

        // then
        blackjack.computeOutcome();

        ArgumentCaptor<Integer> losingBetArgument = ArgumentCaptor.forClass(Integer.class);

        verify(player).loseBet(losingBetArgument.capture());
        Assert.assertEquals((int) (PLAYER_BET), losingBetArgument.getValue().intValue());
    }

    @Test
    public void givenASinglePlayerGame_whenPlayerHasABlackjack_thenPlayerWinsThreeToTwo() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> blackjackHand = createBlackjackHand();
        when(player.getHand()).thenReturn(blackjackHand);
        when(scoreCalculator.calculateScore(blackjackHand)).thenReturn(21);

        // then
        blackjack.computeOutcome();

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(player).receiveGains(argument.capture());
        Assert.assertEquals((int) (PLAYER_BET * BLACKJACK_PAY_FACTOR), argument.getValue().intValue());
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenThePlayerHasABetterScoreThenTheHouse_thenThePlayerWinsHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> losingHand = createALosingNotBustingHand();
        List<Card> winningHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(losingHand);
        when(player.getHand()).thenReturn(winningHand);
        when(scoreCalculator.calculateScore(losingHand)).thenReturn(10);
        when(scoreCalculator.calculateScore(winningHand)).thenReturn(15);

        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).receiveGains(argument.capture());

        Assert.assertEquals(PLAYER_BET, argument.getValue().intValue());
    }

    @Test
    public void givenAPlayerHas21ButNotABlackjack_whenTheGameIsFinishedAndTheHouseLoses_thenThePlayerWinsHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> losingHand = createALosingNotBustingHand();
        List<Card> winningHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(losingHand);
        when(player.getHand()).thenReturn(winningHand);
        when(scoreCalculator.calculateScore(losingHand)).thenReturn(15);
        when(scoreCalculator.calculateScore(winningHand)).thenReturn(20);

        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).receiveGains(argument.capture());

        Assert.assertEquals(PLAYER_BET, argument.getValue().intValue());
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenTheHouseHasABetterScoreThenThePlayer_thenThePlayerLosesHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> losingHand = createALosingNotBustingHand();
        List<Card> winningHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(winningHand);
        when(player.getHand()).thenReturn(losingHand);
        when(scoreCalculator.calculateScore(winningHand)).thenReturn(15);
        when(scoreCalculator.calculateScore(losingHand)).thenReturn(10);

        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).loseBet(argument.capture());

        Assert.assertEquals(PLAYER_BET, argument.getValue().intValue());
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenTheScoreIsEqual_thenThePlayerKeepsHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> aHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(aHand);
        when(player.getHand()).thenReturn(aHand);
        when(scoreCalculator.calculateScore(aHand)).thenReturn(10);

        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).receiveGains(argument.capture());

        Assert.assertEquals(0, argument.getValue().intValue());
    }

    @Test
    public void givenASinglePlayerGame_whenTheHouseBustAndPlayerHasNotBusted_thenThePlayerWinsHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Card> aBustingHand = createBustingHand();
        List<Card> aNotBustingHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(aBustingHand);
        when(player.getHand()).thenReturn(aNotBustingHand);
        when(scoreCalculator.calculateScore(aBustingHand)).thenReturn(22);
        when(scoreCalculator.calculateScore(aNotBustingHand)).thenReturn(10);

        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).receiveGains(argument.capture());

        Assert.assertEquals(PLAYER_BET, argument.getValue().intValue());
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
        aNotBustingHand.add(new Card(5, Card.Type.CLOVER));
        aNotBustingHand.add(new Card(5, Card.Type.CLOVER));
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

    private Blackjack startANewSinglePlayerGame() {
        Blackjack blackjack = new Blackjack(house, player, scoreCalculator);
        when(player.decideBet()).thenReturn(PLAYER_BET);
        blackjack.startNewPlay();
        return blackjack;
    }

    private Answer<Boolean> stopTakingNewCardsAfter2Times() {
        return new Answer<Boolean>() {
            private int numberOfTimeTheHandIsCalculated = 0;

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                numberOfTimeTheHandIsCalculated++;
                if (numberOfTimeTheHandIsCalculated < 3)
                    return true;
                return false;
            }
        };
    }

    private Answer<Integer> bustAfterTakingNewCards3Times() {
        return new Answer<Integer>() {
            private int numberOfTimeTheHandIsCalculated = 0;

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                numberOfTimeTheHandIsCalculated++;
                if (numberOfTimeTheHandIsCalculated < 4)
                    return 10;
                return 22;
            }
        };
    }
}
