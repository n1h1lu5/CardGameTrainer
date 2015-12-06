package domain.participant;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import domain.decks.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerTest {
    private Player aPlayer;

    @Before
    public void setup() {
        aPlayer = new Player();
    }

    @Test
    public void whenCreatingANewPlayerRepresentation_thenTheHandIsEmpty() {
        // given // when // then
        assertEquals(aPlayer.getHand().size(), 0);
    }

    @Test
    public void givenTheHandOfAPlayer_thenTheHandCanBeModifiedAsItIsNeeded() {
        // given // when
        Card aCard = new Card(Card.Value.ACE, Card.Type.CLUB);

        // then
        aPlayer.addCardToHand(aCard);
        Card cardInTheHand = aPlayer.getHand().get(0);

        assertEquals(cardInTheHand, aCard);
    }

    @Test
    public void givenAPlayerReceivesACard_thenTheCardIsAddedToTheHand() {
        // given // when
        Card aCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        aPlayer.receiveCard(aCard);

        // then
        assertTrue(aPlayer.getHand().contains(aCard));
    }

    @Test
    public void givenAPlayerWantsANewCard_whenHeSetsHisStateToWantACard_thenHisStateIsChangedToWantACard() {
        // given // when
        aPlayer.askACard();

        // then
        assertTrue(aPlayer.wantsACard());
    }

    @Test
    public void givenAPlayerDoNotWantACard_whenAskedIfHeWantsACard_thenHeSaysThatHeDoesNotWantACard() {
        // given // when

        // then
        assertFalse(aPlayer.wantsACard());
    }

    @Test
    public void givenAPlayerWantsToFinishHisTurn_whenHeSetsHisStateToWantToFinishTurn_thenHisStateIsChangedToWantToFinishTurn() {
        // given // when
        aPlayer.askToFinishTurn();

        // then
        assertTrue(aPlayer.wantsToFinishTurn());
    }

    @Test
    public void givenAPlayerDoNotWantToFinishHisTurn_whenAskedIfHeWantsToStop_thenHeSaysThatHeDoesNotWantToStop() {
        // given // when

        // then
        assertFalse(aPlayer.wantsToFinishTurn());
    }

    @Test
    public void givenOneOrMoreStateOfThePlayerIsFlagged_whenPlayerResetsItsStates_thenAllHisStatesAreReseted() {
        // given
        aPlayer.askACard();
        aPlayer.askToFinishTurn();

        // when
        aPlayer.resetStates();

        // then
        assertFalse(aPlayer.wantsACard());
        assertFalse(aPlayer.wantsToFinishTurn());
    }
}
