package domain.games;

import domain.decks.Card;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class HomeTurnStateTest {
    private BlackjackGame blackjackGame;

    @Before
    public void setup() {
        blackjackGame = mock(BlackjackGame.class);
    }

    @Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItBusts() {
        // given
        HomeTurnState homeTurnState = new HomeTurnState();

        // when
        //when(player.wantsANewCard()).thenReturn(true);
        when(blackjackGame.hasPlayerBusted()).thenAnswer(bustAfterTakingNewCards3Times());

        homeTurnState.update(blackjackGame);

        // then
        verify(blackjackGame, times(3)).giveHouseACard();
    }

    //TODO: when home becomes a state object
    /*@Test
    public void givenASinglePlayerGame_whenThePlayerHasPlayed_thenTheHouseCanAskANewCardUntilItDoesNotWantAnyMore() {
        // given
        BlackjackGame1 blackjack = startANewSinglePlayerGame();

        // when
        when(house.wantsNewCard()).thenAnswer(stopTakingNewCardsAfter2Times());

        blackjack.askHouseToPlay();

        // then
        verify(house, times(4)).receiveCard(any(Card.class));
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
