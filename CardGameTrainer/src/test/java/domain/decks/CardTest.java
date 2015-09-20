package domain.decks;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class CardTest {

    @Test
    public void canCreateACard() {
        new Card(Card.Value.ACE, Card.Type.CLUB);
    }

    @Test
    public void aNecessaryCheckInOrderToHave100PercentCodeCoverage_junitStrangeBehaviorRegardingEnums() {
        Card.Type.valueOf("CLUB");
        Card.Value.valueOf("ACE");
    }
    
    @Test
    public void givenTwoCards_whenTheyHaveTheSameValueAndType_thenTheyAreEqual() {
        // given // when
        Card firstCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        Card secondCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        
        // then
        assertEquals(firstCard, secondCard);
    }
    
    @Test
    public void givenTwoCards_whenTheyDoNotHaveTheSameValueAndOrTheSameType_thenTheyAreNotEqual() {
        // given // when
        Card firstCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        Card secondCard = new Card(Card.Value.TWO, Card.Type.CLUB);
        Card thirdCard = new Card(Card.Value.ACE, Card.Type.HEART);
        Card fourthCard = new Card(Card.Value.TWO, Card.Type.HEART);
        
        // then
        assertThat(firstCard, is(not(secondCard)));
        assertThat(firstCard, is(not(thirdCard)));
        assertThat(firstCard, is(not(fourthCard)));
    }
    
    @Test
    public void givenACard_whenComparingToANull_thenTheyAreNotEquals() {
        // given
        Card aCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        
        // when // then
        assertFalse(aCard.equals(null));
    }
    
    @Test
    public void givenACard_whenComparingItToAnotherObjectType_thenTheyAreNotEqual() {
        // given
        Card aCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        
        // when // then
        assertFalse(aCard.equals("a string"));
    }
}
