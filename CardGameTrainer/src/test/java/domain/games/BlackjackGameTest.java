package domain.games;

import domain.decks.BlackjackShoe;
import domain.decks.Card;
import domain.participant.BlackjackPlayer;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlackjackGameTest {
    private BlackjackGame aBlackjackGame;

    private BlackjackPlayer aPlayer;
    private StartGameState startState;
    private BlackjackShoe aBlackjackShoe;

    @Before
    public void setup() {
        aPlayer = mock(BlackjackPlayer.class);
        startState = mock(StartGameState.class);
        aBlackjackShoe = mock(BlackjackShoe.class);

        aBlackjackGame = new BlackjackGame(aPlayer, aBlackjackShoe, startState);
    }

    @Test
    public void givenANewBlackjackGame_thenABlackjackPlayerIsInjectedToIt() {
        // given when then
        new BlackjackGame(aPlayer);
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
}
