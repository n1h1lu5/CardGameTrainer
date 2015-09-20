package domain.decks;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CardDeckBuilderTest {
    private CardDeckBuilder deckBuilder;

    @Before
    public void setup() {
        deckBuilder = new CardDeckBuilder();
    }

    @Test
    public void givenANewStandardDeckIsCreated_thenItContainsAllTheNecessaryCards() {
        // given
        deckBuilder.buildStandardCards();
        List<Card> deck = deckBuilder.getDeck();

        // then
        for(Card.Type type: Card.Type.values()) {
            for(Card.Value value : Card.Value.values()) {
                assertEquals(new Card(value, type), deck.get(0));
                deck.remove(0);
            }
        }
    }

    @Test
    public void givenAStandardDeckHasBeenBuilded_whenTheDeckIsCleared_thenTheDeckIsEmpty() {
        // given
        deckBuilder.buildStandardCards();

        // when
        deckBuilder.clearDeck();

        // then
        assertTrue(deckBuilder.getDeck().isEmpty());
    }
}
