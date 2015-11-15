package domain.participant;

import domain.decks.Card;
import domain.games.BlackjackScoreCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlackjackPlayerTest {
    private static final int A_BUSTING_SCORE = 30;
    private static final int A_NOT_BUSTING_SCORE = 20;

    private Player aPlayer;
    private BlackjackScoreCalculator scoreCalculator;

    private BlackjackPlayer aBlackjackPlayer;

    @Before
    public void setup() {
        aPlayer = mock(Player.class);
        scoreCalculator = mock(BlackjackScoreCalculator.class);

        aBlackjackPlayer = new BlackjackPlayer(aPlayer, scoreCalculator);
    }

    @Test
    public void givenTheCreationOfABlackjackPlayer_thenAPlayerDataStructureIsPassedToIt() {
        // given // when // then
        Player aPlayerDataStructure = new Player();
        new BlackjackPlayer(aPlayerDataStructure);
    }

    @Test
    public void whenTheScoreCalculatorReturnsABustingScore_thenTheHandHasBusted() {
        // given // when
        when(scoreCalculator.calculateScore(anyList())).thenReturn(A_BUSTING_SCORE);

        // then
        Assert.assertTrue(aBlackjackPlayer.hasBusted());
    }

    @Test
    public void givenAHandWithAScoreUnder21_thenTheHandHasNotBusted() {
        // given // when
        when(scoreCalculator.calculateScore(anyList())).thenReturn(A_NOT_BUSTING_SCORE);

        // then
        Assert.assertFalse(aBlackjackPlayer.hasBusted());
    }

    @Test
    public void givenTheBlackjackPlayerReceivesACard_thenTheCardIsAddedToTheHand() {
        // given // when
        Card aCard = new Card(Card.Value.ACE, Card.Type.CLUB);
        aBlackjackPlayer.receiveCard(aCard);

        // then
        verify(aPlayer).addCardToHand(aCard);
    }

    /* // Can be used in the score calculator?
    private List<Card> createBustingHand() {
        List<Card> bustingHand = new ArrayList<Card>();
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        bustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return bustingHand;
    }

    private List<Card> createAotBustingHand() {
        List<Card> aNotBustingHand = new ArrayList<Card>();
        aNotBustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        aNotBustingHand.add(new Card(Card.Value.TEN, Card.Type.CLUB));
        return aNotBustingHand;
    }*/
}
