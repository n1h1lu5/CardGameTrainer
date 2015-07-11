package games;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import decks.Card;

public class BlackjackScoreCalculatorTest {
    
    private BlackjackScoreCalculator scoreCalculator;

    @Before
    public void setup() {
        scoreCalculator = new BlackjackScoreCalculator();
    }

    @Test
    public void givenAHand_whenCalculatingTheScore_thenAllTheCardsValueAreSummed() {
        // given
        List<Card> aHand = new ArrayList<Card>();
        aHand.add(new Card(4, Card.Type.CLOVER));
        aHand.add(new Card(5, Card.Type.CLOVER));
        aHand.add(new Card(8, Card.Type.CLOVER));

        // when // then
        Assert.assertEquals(17, scoreCalculator.calculateScore(aHand));
    }

    @Test
    public void givenAHandThatContainsAnAce_whenConsideredHasAn11ItMakesThePlayerBust_thenTheAceIsConsideredAsA1() {
        // given
        List<Card> aBustingHandWithAces = new ArrayList<Card>();
        aBustingHandWithAces.add(new Card(10, Card.Type.CLOVER));
        aBustingHandWithAces.add(new Card(1, Card.Type.CLOVER));
        aBustingHandWithAces.add(new Card(1, Card.Type.CLOVER));
        aBustingHandWithAces.add(new Card(10, Card.Type.CLOVER));

        // when // then
        Assert.assertEquals(22, scoreCalculator.calculateScore(aBustingHandWithAces));
    }

    @Test
    public void givenAHandThatContainsAnAce_whenConsideredHasAn11ItDoesNotMakesThePlayerBust_thenTheAceIsConsideredAsA11() {
        // given
        List<Card> aNotBustingHandWithAce = new ArrayList<Card>();
        aNotBustingHandWithAce.add(new Card(10, Card.Type.CLOVER));
        aNotBustingHandWithAce.add(new Card(1, Card.Type.CLOVER));

        // when // then
        Assert.assertEquals(21, scoreCalculator.calculateScore(aNotBustingHandWithAce));
    }
}
