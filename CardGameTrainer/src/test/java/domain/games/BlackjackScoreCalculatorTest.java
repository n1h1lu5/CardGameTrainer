package domain.games;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import domain.decks.Card;
import domain.games.BlackjackScoreCalculator;

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
        aHand.add(new Card(Card.Value.FOUR, Card.Type.CLUB));
        aHand.add(new Card(Card.Value.FIVE, Card.Type.CLUB));
        aHand.add(new Card(Card.Value.EIGHT, Card.Type.CLUB));

        // when // then
        Assert.assertEquals(17, scoreCalculator.calculateScore(aHand));
    }

    @Test
    public void givenAHandThatContainsAnAce_whenConsideredHasAn11ItMakesThePlayerBust_thenTheAceIsConsideredAsA1() {
        // given
        List<Card> aBustingHandWithAces = new ArrayList<Card>();
        aBustingHandWithAces.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        aBustingHandWithAces.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        aBustingHandWithAces.add(new Card(Card.Value.ACE, Card.Type.CLUB));
        aBustingHandWithAces.add(new Card(Card.Value.TEN, Card.Type.CLUB));

        // when // then
        Assert.assertEquals(22, scoreCalculator.calculateScore(aBustingHandWithAces));
    }

    @Test
    public void givenAHandThatContainsAnAce_whenConsideredHasAn11ItDoesNotMakesThePlayerBust_thenTheAceIsConsideredAsA11() {
        // given
        List<Card> aNotBustingHandWithAce = new ArrayList<Card>();
        aNotBustingHandWithAce.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        aNotBustingHandWithAce.add(new Card(Card.Value.ACE, Card.Type.CLUB));

        // when // then
        Assert.assertEquals(21, scoreCalculator.calculateScore(aNotBustingHandWithAce));
    }
}
