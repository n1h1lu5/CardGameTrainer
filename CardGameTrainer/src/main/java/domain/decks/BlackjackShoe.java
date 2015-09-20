package domain.decks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BlackjackShoe {
    private Stack<Card> shoe;
    private List<Card> givenCards;

    public BlackjackShoe(int numberOfDecks, CardDeckBuilder cardDeckBuilder) {
        if(numberOfDecks < 1)
            throw new NotEnoughCardDecksToCreateBlackjackShoeException();
        shoe = new Stack<Card>();
        givenCards = new ArrayList<Card>();

        buildShoe(numberOfDecks, cardDeckBuilder);
    }

    private void buildShoe(int numberOfDecks, CardDeckBuilder cardDeckBuilder) {
        for(int i = 0; i < numberOfDecks; i++) {
            cardDeckBuilder.buildStandardCards();
            shoe.addAll(cardDeckBuilder.getDeck());
            cardDeckBuilder.clearDeck();
        }
    }

    public Card giveTopCard() {
        if(shoe.isEmpty())
            throw new EmptyBlackjackShoeException();
        Card topCard = shoe.pop();
        givenCards.add(topCard);
        return topCard;
    }

    public void shuffle() {
        shoe.addAll(givenCards);
        givenCards.clear();
        Collections.shuffle(shoe);
    }

    // For test purposes only
    protected Stack<Card> getShoe() {
        return this.shoe;
    }
}
