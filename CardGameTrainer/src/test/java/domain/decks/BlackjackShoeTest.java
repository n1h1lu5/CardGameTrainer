package domain.decks;

import java.util.List;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

import org.hamcrest.collection.IsIterableContainingInOrder;

import static org.hamcrest.core.IsNot.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;

public class BlackjackShoeTest {
    private static final int ONE_DECK = 1;
    private static final int TWO_DECK = 2;

    private static List<Card> a52CardsDeck;
    private CardDeckBuilder cardDeckBuilder;

    @BeforeClass
    public static void setupOnce() {
        a52CardsDeck = a52CardsDeck(); 
    }

    @Before
    public void setup() {
        cardDeckBuilder = mock(CardDeckBuilder.class);
    }

    @Test
    public void whenCreatingABlackjackShoe_thenItCanBeCreatedWithMultipleCardDecks() {
        // when
        when(cardDeckBuilder.getDeck()).thenReturn(a52CardsDeck);
        BlackjackShoe shoe = new BlackjackShoe(TWO_DECK, cardDeckBuilder);

        InOrder order = inOrder(cardDeckBuilder);

        order.verify(cardDeckBuilder).buildStandardCards();
        order.verify(cardDeckBuilder).getDeck();
        order.verify(cardDeckBuilder).clearDeck();

        order.verify(cardDeckBuilder).buildStandardCards();
        order.verify(cardDeckBuilder).getDeck();
        order.verify(cardDeckBuilder).clearDeck();

        assertEquals(104, shoe.getShoe().size());
    }

    @Test(expected = NotEnoughCardDecksToCreateBlackjackShoeException.class)
    public void whenCreatingABlackjackShoeWithLessThen1CardDeck_thenANotEnoughDeckToCreateBlackjackShoeExceptionIsThrown() {
        // when
        new BlackjackShoe(0, cardDeckBuilder);
    }

    @Test
    public void givenABlackjackShoe_whenShufflingTheShoe_thenTheCardsAreShuffled() throws Exception {
        // given
        when(cardDeckBuilder.getDeck()).thenReturn(a52CardsDeck);
        BlackjackShoe shoe = new BlackjackShoe(ONE_DECK, cardDeckBuilder);

        // when
        shoe.shuffle();

        // then
        Assert.assertThat(a52CardsDeck, not(IsIterableContainingInOrder.contains(shoe.getShoe().toArray())));
    }

    @Test
    public void givenABlackjackShoe_whenShufflingTheShoe_thenAllTheCardsThatWereTakenFromTheShoeAreBackInItWithNoDuplication() {
        // given
        when(cardDeckBuilder.getDeck()).thenReturn(a52CardsDeck);

        BlackjackShoe shoe = new BlackjackShoe(ONE_DECK, cardDeckBuilder);

        // when
        shoe.giveTopCard();
        shoe.shuffle();

        // then
        boolean allCardsAreInTheShoe = shoe.getShoe().containsAll(a52CardsDeck) && a52CardsDeck.containsAll(shoe.getShoe());
        boolean withNoDuplication = shoe.getShoe().size() == 52;
        assertTrue(allCardsAreInTheShoe && withNoDuplication);

        // when
        shoe.giveTopCard();
        shoe.shuffle();

        // then
        allCardsAreInTheShoe = shoe.getShoe().containsAll(a52CardsDeck) && a52CardsDeck.containsAll(shoe.getShoe());
        withNoDuplication = shoe.getShoe().size() == 52;
        assertTrue(allCardsAreInTheShoe && withNoDuplication);
    }

    @Test
    public void givenABlackjackShoe_whenTakingTheTopCards_thenTheTopCardsAreReturned() {        
        // given
        when(cardDeckBuilder.getDeck()).thenReturn(a52CardsDeck);
        BlackjackShoe shoe = new BlackjackShoe(ONE_DECK, cardDeckBuilder);

        // when
        Card firstTopCard = shoe.giveTopCard();
        Card secondTopCard = shoe.giveTopCard();

        // then
        Card firstExpectedCard = new Card(Card.Value.KING, Card.Type.DIAMOND);
        Card secondExpectedCard = new Card(Card.Value.QUEEN, Card.Type.DIAMOND);

        assertEquals(firstExpectedCard, firstTopCard);
        assertEquals(secondExpectedCard, secondTopCard);
    }

    @Test(expected = EmptyBlackjackShoeException.class)
    public void givenABlackjackShoe_whenTakingTheTopCardOfAnEmptyShoe_thenEmptyBlackjackShoeExceptionIsThrown() {
        // given
        BlackjackShoe shoe = new BlackjackShoe(ONE_DECK, cardDeckBuilder);

        // when
        for(int i = 0; i < 53; i++) {
            shoe.giveTopCard();
        }
    }

    private static List<Card> a52CardsDeck() {
        CardDeckBuilder deckBuilder = new CardDeckBuilder();

        deckBuilder.buildStandardCards();
        return deckBuilder.getDeck();
    }
}
