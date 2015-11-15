package domain.participant;

import static org.junit.Assert.*;

import domain.decks.Card;
import org.junit.Test;

public class PlayerTest {


    @Test
    public void whenCreatingANewPlayerRepresentation_thenTheHandIsEmpty() {
        // given when
        Player aPlayer = new Player();

        // then
        assertEquals(aPlayer.getHand().size(), 0);
    }

    @Test
    public void givenTheHandOfAPlayer_thenTheHandCanBeModifiedAsItIsNeeded() {
        // given when
        Player aPlayer = new Player();
        Card aCard = new Card(Card.Value.ACE, Card.Type.CLUB);

        // then
        aPlayer.addCardToHand(aCard);
        Card cardInTheHand = aPlayer.getHand().get(0);

        assertEquals(cardInTheHand, aCard);
    }
}
