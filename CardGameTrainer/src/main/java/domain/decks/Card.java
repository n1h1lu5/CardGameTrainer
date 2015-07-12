package domain.decks;

public class Card {
    public enum Type {
        HEART, DIAMOND, CLOVER, PIKE
    };

    public int number = 0;
    public Type type = Type.HEART;

    public Card(int number, Type type) {
        this.number = number;
        this.type = type;
    }
}
