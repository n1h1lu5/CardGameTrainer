package domain.games;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class DistributeCardsStateTest {
    private BlackjackGame blackjackGame;

    @Before
    public void setup() {
        blackjackGame = mock(BlackjackGame.class);
    }

    @Test
    public void givenANewPlayWithSinglePlayer_whenItsTheBeginningOfTheGame_thenThePlayerReceivesHisCardsFirstAndTheHouseReceivesItsCardsLast() {
        // given // when
        DistributeCardsState distributeCardsState = new DistributeCardsState();

        distributeCardsState.update(blackjackGame);

        // then
        InOrder order = inOrder(blackjackGame);

        order.verify(blackjackGame, times(2)).givePlayerACard();
        order.verify(blackjackGame, times(2)).giveHouseACard();
    }

}
