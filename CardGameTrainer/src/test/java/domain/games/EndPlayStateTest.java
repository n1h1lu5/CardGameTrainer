package domain.games;

import domain.decks.Card;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EndPlayStateTest {
    private BlackjackGameState1 gameState; // This temp class will go extinct
    private BlackjackGame blackjackGame;

    @Before
    public void setup() {
        gameState = mock(BlackjackGameState1.class);
        blackjackGame = mock(BlackjackGame.class);
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenPlayerHasABlackjack_thenPlayerWinsBlackjackGains() {
        // given
        EndPlayState endPlayState = new EndPlayState(gameState);

        // when
        when(gameState.hasBlackjack(anyListOf(Card.class))).thenReturn(true);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerBlackjackGains();
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerBust_thenThePlayerHasLostHisBet() {
        // given
        EndPlayState endPlayState = new EndPlayState(gameState);

        // when
        when(gameState.hasBusted(anyListOf(Card.class))).thenReturn(true);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).takePlayerBet();
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenTheHouseHasABetterScoreThenThePlayer_thenThePlayerLosesHisBet() {
        // given
        EndPlayState endPlayState = new EndPlayState(gameState);

        // when
        when(gameState.hasBlackjack(anyListOf(Card.class))).thenReturn(false);
        when(gameState.hasBusted(anyListOf(Card.class))).thenReturn(false);
        when(gameState.playerBeatsHouse(anyListOf(Card.class), anyListOf(Card.class))).thenReturn(false);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).takePlayerBet();
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenThePlayerHasABetterScoreThenTheHouse_thenThePlayerWinsHisBet() {
        // given
        EndPlayState endPlayState = new EndPlayState(gameState);

        // when
        when(gameState.playerBeatsHouse(anyListOf(Card.class), anyListOf(Card.class))).thenReturn(true);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerStandardGains();
    }

    @Test
    public void givenTheEndOfASinglePlayerGame_whenTheScoreIsEqual_thenThePlayerKeepsHisBet() {
        // given
        EndPlayState endPlayState = new EndPlayState(gameState);

        // when
        when(gameState.playerAndHouseAreEven(anyListOf(Card.class), anyListOf(Card.class))).thenReturn(true);

        endPlayState.update(blackjackGame);

        // then
        verify(blackjackGame).givePlayerEvenGains();
    }
}
