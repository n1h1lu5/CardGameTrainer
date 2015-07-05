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

import static org.mockito.Mockito.*;
import participant.House;
import participant.Player;

public class BlackjackTest {

    private static final int PLAYER_BET = 10;

    private Player player;
    private House house;

    @Before
    public void setup() {
        player = mock(Player.class);
        house = mock(House.class);
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItBusts() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenReturn(true);
        when(house.getHand()).thenAnswer(new Answer<List<Integer>>() {
            private int numberOfTimeTheHandIsCalculated = 0;

            @Override
            public List<Integer> answer(InvocationOnMock invocation) throws Throwable {
                numberOfTimeTheHandIsCalculated++;
                if (numberOfTimeTheHandIsCalculated < 4)
                    return createALosingNotBustingHand();
                return createBustingHand();
            }
        });

        blackjack.askHouseToPlay();

        // then
        verify(house, times(3)).receiveCard(anyInt());
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItDoesNotWantAnyMore() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenAnswer(new Answer<Boolean>() {
            private int numberOfTimeTheHandIsCalculated = 0;

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                numberOfTimeTheHandIsCalculated++;
                if (numberOfTimeTheHandIsCalculated < 3)
                    return true;
                return false;
            }

        });
        when(house.getHand()).thenReturn(createALosingNotBustingHand());
        blackjack.askHouseToPlay();

        // then
        verify(house, times(2)).receiveCard(anyInt());
    }

    @Test
    public void givenThePlayerHasReceivedHisFirstTwoCards_whenItsThePlayerTurn_thenHeCanAskForANewCardUntilHeDoesNotWantAnyMore() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(player.wantsANewCard()).then(new Answer<Boolean>() {
            private int numberOfTimeTheHandIsCalculated = 0;

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                numberOfTimeTheHandIsCalculated++;
                if (numberOfTimeTheHandIsCalculated < 3)
                    return true;
                return false;
            }
        });
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
        when(player.getHand()).thenAnswer(new Answer<List<Integer>>() {
            private int numberOfTimeTheHandIsCaculated = 0;

            public List<Integer> answer(InvocationOnMock invocation) {
                numberOfTimeTheHandIsCaculated++;
                if (numberOfTimeTheHandIsCaculated < 4)
                    return createALosingNotBustingHand();
                return createBustingHand();
            }
        });

        blackjack.askPlayerToPlay();

        // then
        verify(player, times(5)).receiveCard(anyInt());
    }

    @Test
    public void givenANewPlayWithSinglePlayer_whenItsTheBeginingOfTheGame_thenThePlayerMustGiveHisBetThenReceiveTwoCards() {
        // given //when
        startANewSinglePlayerGame();

        // then
        InOrder order = inOrder(player);

        order.verify(player).decideBet();
        order.verify(player, times(2)).receiveCard(anyInt());
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerBust_thenThePlayerHasLostHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Integer> bustingHand = createBustingHand();
        when(player.getHand()).thenReturn(bustingHand);

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
        List<Integer> blackjackHand = createBlackjackHand();
        when(player.getHand()).thenReturn(blackjackHand);

        // then
        blackjack.computeOutcome();

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(player).receiveGains(argument.capture());
        Assert.assertEquals((int) (PLAYER_BET * Blackjack.BLACKJACK_PAY_FACTOR), argument.getValue().intValue());
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenThePlayerHasABetterScoreThenTheHouse_thenThePlayerWinsHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        List<Integer> losingHand = createALosingNotBustingHand();
        List<Integer> winningHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(losingHand);
        when(player.getHand()).thenReturn(winningHand);

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
        List<Integer> losingHand = createALosingNotBustingHand();
        List<Integer> winningHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(losingHand);
        when(player.getHand()).thenReturn(winningHand);

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
        List<Integer> losingHand = createALosingNotBustingHand();
        List<Integer> winningHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(winningHand);
        when(player.getHand()).thenReturn(losingHand);

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
        List<Integer> aHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(aHand);
        when(player.getHand()).thenReturn(aHand);

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
        List<Integer> aBustingHand = createBustingHand();
        List<Integer> aNotBustingHand = createAWinningNotBustingHand();

        when(house.getHand()).thenReturn(aBustingHand);
        when(player.getHand()).thenReturn(aNotBustingHand);

        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).receiveGains(argument.capture());

        Assert.assertEquals(PLAYER_BET, argument.getValue().intValue());
    }

    private List<Integer> createBustingHand() {
        List<Integer> bustingHand = new ArrayList<Integer>();
        bustingHand.add(11);
        bustingHand.add(9);
        bustingHand.add(2);
        return bustingHand;
    }

    private List<Integer> createAWinningNotBustingHand() {
        List<Integer> aNotBustingHand = new ArrayList<Integer>();
        aNotBustingHand.add(5);
        aNotBustingHand.add(5);
        return aNotBustingHand;
    }

    private List<Integer> createALosingNotBustingHand() {
        List<Integer> aNotBustingHand = new ArrayList<Integer>();
        aNotBustingHand.add(1);
        aNotBustingHand.add(1);

        return aNotBustingHand;
    }

    private List<Integer> createBlackjackHand() {
        List<Integer> blackjackHand = new ArrayList<Integer>();
        blackjackHand.add(11);
        blackjackHand.add(10);
        return blackjackHand;
    }

    private Blackjack startANewSinglePlayerGame() {
        Blackjack blackjack = new Blackjack(house, player);
        when(player.decideBet()).thenReturn(PLAYER_BET);
        blackjack.startNewPlay();
        return blackjack;
    }
}
