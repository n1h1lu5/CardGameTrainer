package domain.games;

import domain.decks.BlackjackShoe;
import domain.decks.Card;
import domain.participant.Player;
import domain.participant.PlayerGameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BlackjackScoreCalculator.class)
public class BlackjackGameTest {
    private static final int A_BUSTING_SCORE = 30;
    private static final int A_NOT_BUSTING_SCORE = 20;

    private BlackjackGame aBlackjackGame;

    private PlayerGameEngine aPlayer;
    private StartGameState startState;
    private BlackjackShoe aBlackjackShoe;

    @Before
    public void setup() {
        aPlayer = mock(Player.class);
        startState = mock(StartGameState.class);
        aBlackjackShoe = mock(BlackjackShoe.class);

        aBlackjackGame = new BlackjackGame(aPlayer, aBlackjackShoe, startState);
    }

    @Test
    public void givenTheGameEngineIsUpdated_thenThePlayersStatesAreResetedAfterTheCurrentStateUpdate() {
        // given // when
        aBlackjackGame.update();

        // then
        InOrder inOrder = inOrder(startState, aPlayer);
        inOrder.verify(startState).update(aBlackjackGame);
        inOrder.verify(aPlayer).resetStates();
    }

    @Test
    public void givenOnlyOnePlayerInTheGame_whenGivingACardToThePlayer_thenThePlayerReceivesACardFromTheTopOfTheShoe() {
        // given // when
        Card cardToGive = new Card(Card.Value.ACE, Card.Type.CLUB);
        when(aBlackjackShoe.giveTopCard()).thenReturn(cardToGive);

        aBlackjackGame.givePlayerACard();

        // then
        verify(aBlackjackShoe).giveTopCard();
        verify(aPlayer).receiveCard(cardToGive);
    }

    @Test
    public void whenTheScoreCalculatorReturnsABustingScore_thenTheHandHasBusted() {
        // given // when
        PowerMockito.mockStatic(BlackjackScoreCalculator.class);

        PowerMockito.when(BlackjackScoreCalculator.calculateScore(anyList())).thenReturn(A_BUSTING_SCORE);

        // then
        Assert.assertTrue(aBlackjackGame.hasPlayerBusted());
    }

    @Test
    public void givenAHandWithAScoreUnder21_thenTheHandHasNotBusted() {
        // given // when
        PowerMockito.mockStatic(BlackjackScoreCalculator.class);

        PowerMockito.when(BlackjackScoreCalculator.calculateScore(anyList())).thenReturn(A_NOT_BUSTING_SCORE);

        // then
        Assert.assertFalse(aBlackjackGame.hasPlayerBusted());
    }

    @Test
    public void givenAskedIfTheCurrentPlayerWantsACard_thenTheGameEngineAskTheCurrentPlayerForThis() {
        // given // when
        aBlackjackGame.currentPlayerWantsCard();

        // then
        verify(aPlayer).wantsACard();
    }

    @Test
    public void givenAskedIfTheCurrentPlayerWantsToFinishHisTurn_thenTheGameEngineAskTheCurrentPlayerForThis() {
        // given // when
        aBlackjackGame.currentPlayerStoppedHisTurn();

        // then
        verify(aPlayer).wantsToFinishTurn();
    }
}
