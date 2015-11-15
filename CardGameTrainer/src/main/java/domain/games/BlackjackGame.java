package domain.games;

import domain.decks.BlackjackShoe;
import domain.decks.Card;
import domain.decks.CardDeckBuilder;
import domain.participant.BlackjackPlayer;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {
    private BlackjackPlayer player;
    private BlackjackShoe shoe;
    private BlackjackGameState currentGameState;

    public BlackjackGame(BlackjackPlayer player) {
        this.player = player;
        currentGameState = new StartGameState();
        shoe = new BlackjackShoe(1, new CardDeckBuilder());
    }

    public void update() {
        currentGameState.update(this);
    }

    public void changeState(BlackjackGameState newState) {
        // If needed, call onEnter here
        currentGameState = newState;
    }

    public List<Card> getPlayerHand() {
        return new ArrayList<Card>();
    }

    public List<Card> getHouseHand() {
        return new ArrayList<Card>();
    }

    public boolean hasPlayerBusted() {
        return player.hasBusted();
    }

    public void givePlayerEvenGains() {

    }

    public void givePlayerBlackjackGains() {

    }

    public void givePlayerStandardGains() {

    }

    public void takePlayerBet() {

    }

    public void givePlayerACard() {
        this.player.receiveCard(this.shoe.giveTopCard());
    }

    public void giveHouseACard() {

    }

    // For test purpose only
    protected BlackjackGame(BlackjackPlayer player, BlackjackShoe blackjackShoe, BlackjackGameState currentGameState) {
        this.player = player;
        this.shoe = blackjackShoe;
        this.currentGameState = currentGameState;
    }
}
