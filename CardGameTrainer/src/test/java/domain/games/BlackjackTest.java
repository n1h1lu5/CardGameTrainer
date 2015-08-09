package domain.games;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import domain.decks.BlackjackShoe;
import domain.decks.Card;
import domain.games.Blackjack;
import domain.games.BlackjackGameState;
import domain.participant.House;
import domain.participant.Player;

public class BlackjackTest {
    private static final int PLAYER_BET = 10;
    private static final float BLACKJACK_PAY_FACTOR = 1.5f;

    private BlackjackGameState gameState;
    private BlackjackShoe gameShoe;
    private Player player;
    private House house;

    @Before
    public void setup() {
        gameState = mock(BlackjackGameState.class);
        gameShoe = mock(BlackjackShoe.class);
        player = mock(Player.class);
        house = mock(House.class);
    }

    @Test
    public void canCreateABlackjackInstence() {
        new Blackjack(house, player, gameShoe);
    }

    @Test
    public void givenANewPlayWithSinglePlayer_whenItsTheBeginingOfTheGame_thenThePlayerMustGiveHisBetThenReceiveHisCardsFirstAndTheHouseReceiveItsCardsLast() {
        // given // when
        startANewSinglePlayerGame();

        // then
        InOrder order = inOrder(player, house);

        verify(gameShoe, times(4)).takeCardOnTop();
        order.verify(player).decideBet();
        order.verify(player, times(2)).receiveCard(any(Card.class));
        order.verify(house, times(2)).receiveCard(any(Card.class));
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItBusts() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenReturn(true);
        when(gameState.hasBusted(anyListOf(Card.class))).thenAnswer(bustAfterTakingNewCards3Times());

        blackjack.askHouseToPlay();

        // then
        verify(house, times(5)).receiveCard(any(Card.class));
    }

    @Test
    public void whenAPlayerOrTheHouseWantANewCard_thenItAlwaysComeFromTheTopOfABlackjackFoe() {
        // when
        Blackjack blackjack = startANewSinglePlayerGame();
        
        when(house.wantsNewCard()).thenAnswer(stopTakingNewCardsAfter2Times());
        when(player.wantsANewCard()).thenAnswer(stopTakingNewCardsAfter2Times());

        blackjack.askHouseToPlay();
        blackjack.askPlayerToPlay();
        
        // then
        verify(gameShoe, times(8)).takeCardOnTop();
    }
    
    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItDoesNotWantAnyMore() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenAnswer(stopTakingNewCardsAfter2Times());

        blackjack.askHouseToPlay();

        // then
        verify(house, times(4)).receiveCard(any(Card.class));
    }

    @Test
    public void givenThePlayerHasReceivedHisFirstTwoCards_whenItsThePlayerTurn_thenHeCanAskForANewCardUntilHeDoesNotWantAnyMore() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(player.wantsANewCard()).thenAnswer(stopTakingNewCardsAfter2Times());

        blackjack.askPlayerToPlay();

        // then
        verify(player, times(4)).receiveCard(any(Card.class));
    }

    @Test
    public void givenThePlayerHasReceivedHisFirstTwoCards_whenItsThePlayerTurn_thenHeCanAskForANewCardUntilHeHasBusted() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(player.wantsANewCard()).thenReturn(true);
        when(gameState.hasBusted(anyListOf(Card.class))).thenAnswer(bustAfterTakingNewCards3Times());

        blackjack.askPlayerToPlay();

        // then
        verify(player, times(5)).receiveCard(any(Card.class));
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerBust_thenThePlayerHasLostHisBet() {
        // given
        Blackjack blackjack = startANewSinglePlayerGame();

        // when
        when(gameState.hasBusted(anyListOf(Card.class))).thenReturn(true);

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
        when(gameState.hasBlackjack(anyListOf(Card.class))).thenReturn(true);

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
        when(gameState.playerBeatsHouse(anyListOf(Card.class), anyListOf(Card.class))).thenReturn(true);

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
        when(gameState.playerBeatsHouse(anyListOf(Card.class), anyListOf(Card.class))).thenReturn(false);

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
        when(gameState.playerAndHouseAreEven(anyListOf(Card.class), anyListOf(Card.class))).thenReturn(true);
        blackjack.computeOutcome();

        // then
        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(player).receiveGains(argument.capture());

        Assert.assertEquals(0, argument.getValue().intValue());
    }

    private Blackjack startANewSinglePlayerGame() {
        Blackjack blackjack = new Blackjack(house, player, gameShoe, gameState);
        
        when(player.decideBet()).thenReturn(PLAYER_BET);
        when(gameShoe.takeCardOnTop()).thenReturn(new Card(10, Card.Type.CLOVER));
        
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

    private Answer<Boolean> bustAfterTakingNewCards3Times() {
        return new Answer<Boolean>() {
            private int numberOfTimeTheHandIsCalculated = 0;

            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                numberOfTimeTheHandIsCalculated++;
                if (numberOfTimeTheHandIsCalculated < 4)
                    return false;
                else
                    return true;
            }
        };
    }
}
