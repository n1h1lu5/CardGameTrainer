package domain.participant;

import java.util.ArrayList;
import java.util.List;

import domain.decks.Card;

public class Player {
    public List<Card> hand;

    public Player() {
        hand = new ArrayList<Card>();
    }
}
