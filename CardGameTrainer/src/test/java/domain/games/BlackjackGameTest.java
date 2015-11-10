package domain.games;

import domain.participant.BlackjackPlayer;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class BlackjackGameTest {
    private BlackjackGame blackjackGame;

    private BlackjackPlayer aPlayer;
    private StartGameState startState;

    @Before
    public void setup() {
        aPlayer = mock(BlackjackPlayer.class);
        startState = mock(StartGameState.class);
        blackjackGame = new BlackjackGame(aPlayer, startState);
    }

    @Test
    public void givenANewBlackjackGame_thenABlackjackPlayerIsInjectedToIt() {
        // given when then
        new BlackjackGame(aPlayer);
    }
}
