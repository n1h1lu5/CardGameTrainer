package domain.games;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class PlayersTurnsStateTest {
    private BlackjackGame blackjackGame;

    private PlayersTurnState playersTurnState;

    @Before
    public void setup() {
        playersTurnState = new PlayersTurnState();
        blackjackGame = mock(BlackjackGame.class);
    }

    @Test
    public void givenItIsThePlayerTurnToHaveNewCards_whenHeHasBusted_thenHisTurnIsFinished() {
        // given // when
        when(blackjackGame.hasPlayerBusted()).thenReturn(true);

        playersTurnState.update(blackjackGame);

        // then
        verify(blackjackGame).changeState(any(EndPlayState.class));
    }

    @Test
    public void givenItIsThePlayerTurnToHaveNewCards_whenThePlayerHasNotBustedAndWantsACard_thenHeReceivesACard() {
        // given // when
        when(blackjackGame.hasPlayerBusted()).thenReturn(false);
        when(blackjackGame.currentPlayerWantsCard()).thenReturn(true);

        playersTurnState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerACard();
    }

    @Test
    public void givenItIsThePlayerTurnToHaveNewCards_whenThePlayerHasNotBustedAndDoesNotWantACardAndDoesNotFinishHisTurn_thenHeDoesNotReceiveACardButHisTurnIsNotFinished() {
        // given // when
        when(blackjackGame.hasPlayerBusted()).thenReturn(false);
        when(blackjackGame.currentPlayerWantsCard()).thenReturn(false);
        when(blackjackGame.currentPlayerStoppedHisTurn()).thenReturn(false);

        playersTurnState.update(blackjackGame);

        // then
        verify(blackjackGame, never()).givePlayerACard();
        verify(blackjackGame, never()).changeState(any(EndPlayState.class));
    }

    @Test
    public void givenItIsThePlayerTurnToHaveNewCards_whenThePlayerStopsItsTurn_thenHisTurnIsFinished() {
        // given // when
        when(blackjackGame.hasPlayerBusted()).thenReturn(false);
        when(blackjackGame.currentPlayerStoppedHisTurn()).thenReturn(true);

        playersTurnState.update(blackjackGame);

        // then
        verify(blackjackGame, never()).givePlayerACard();
        verify(blackjackGame).changeState(any(EndPlayState.class));
    }

    //TODO: when player becomes a state object
    /*@Test
    public void givenThePlayerHasReceivedHisFirstTwoCards_whenItsThePlayerTurn_thenHeCanAskForANewCardUntilHeDoesNotWantAnyMore() {
        // given
        PlayersTurnState playersTurnState = new PlayersTurnState(gameState);

        // when
        when(player.wantsANewCard()).thenAnswer(stopTakingNewCardsAfter2Times());

        blackjack.askPlayerToPlay();

        // then
        verify(player, times(4)).receiveCard(any(Card.class));
    }*/

    /*@Test
    public void givenItIsThePlayerTurnToHaveNewCards_thenHeCanAskForANewCardUntilHeHasBusted() {
        // given
        PlayersTurnState playersTurnState = new PlayersTurnState();

        // when
        //when(player.wantsANewCard()).thenReturn(true);
        when(blackjackGame.hasPlayerBusted()).thenAnswer(bustAfterTakingNewCards3Times());

        playersTurnState.update(blackjackGame);

        // then
        verify(blackjackGame, times(3)).givePlayerACard();
    }*/

    // TODO: Test to be moved
    /*@Test
    public void whenAPlayerOrTheHouseWantANewCard_thenItAlwaysComeFromTheTopOfABlackjackFoe() {
        // when
        BlackjackGame1 blackjack = startANewSinglePlayerGame();

        when(house.wantsNewCard()).thenAnswer(stopTakingNewCardsAfter2Times());
        when(player.wantsANewCard()).thenAnswer(stopTakingNewCardsAfter2Times());

        blackjack.askHouseToPlay();
        blackjack.askPlayerToPlay();

        // then
        verify(gameShoe, times(8)).giveTopCard();
    }*/

    private Answer<Boolean> bustAfterTakingNewCards3Times() {
        return new Answer<Boolean>() {
            private int numberOfTimeTheHandIsCalculated = 0;

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
