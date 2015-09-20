package domain.decks;

public class Card {
    public enum Type {
        SPADE, HEART, CLUB, DIAMOND
    };

    public enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    public Value value;
    public Type type;

    public Card(Value number, Type type) {
        this.value = number;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null)
            return false;
        if(!(other instanceof Card))
            return false;
        Card otherCard = (Card) other;
        if(this.value == otherCard.value && this.type == otherCard.type)
            return true;
        return false;
    }
}
