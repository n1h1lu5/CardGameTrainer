package domain.decks;

public class Card implements Comparable<Card> {
    public enum Type {
        HEART, DIAMOND, CLOVER, PIKE
    };

    public int number = 0;
    public Type type = Type.HEART;

    public Card(int number, Type type) {
        this.number = number;
        this.type = type;
    }

    @Override
    public int compareTo(Card compared) {
        if (this.number > compared.number) {
            return 1;
        } else if (this.number < compared.number) {
            return -1;
        }
        return 0;
    }

}
