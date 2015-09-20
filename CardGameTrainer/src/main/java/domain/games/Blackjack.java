package domain.games;

import domain.decks.BlackjackShoe;
import domain.participant.House;
import domain.participant.Player;

public class Blackjack {
    private static final float BLACKJACK_PAY_FACTOR = 1.5f;

    private BlackjackGameState gameState;
    private BlackjackShoe gameShoe;

    private House house;

    private Player player;
    private int playerBet;

    public Blackjack(House house, Player player, BlackjackShoe gameShoe) {
        this.gameShoe = gameShoe;
        this.player = player;
        this.house = house;
        this.gameState = new BlackjackGameState();
    }

    public void startNewPlay() {
        playerBet = player.decideBet();
        giveInitialCardsToPlayer();
        giveInitialCardsToHouse();
    }

    public void askPlayerToPlay() {
        while (!gameState.hasBusted(player.getHand()) && player.wantsANewCard()) {
            player.receiveCard(gameShoe.giveTopCard());
        }
    }

    public void askHouseToPlay() {
        while (!gameState.hasBusted(house.getHand()) && house.wantsNewCard()) {
            house.receiveCard(gameShoe.giveTopCard());
        }
    }

    public void computeOutcome() {
        if (gameState.playerAndHouseAreEven(player.getHand(), house.getHand())) {
            player.receiveGains(0);
        } else if (gameState.hasBlackjack(player.getHand())) {
            int gain = (int) (playerBet * BLACKJACK_PAY_FACTOR);
            player.receiveGains(gain);
        } else if (gameState.playerBeatsHouse(player.getHand(), house.getHand())) {
            player.receiveGains(playerBet);
        } else {
            player.loseBet(playerBet);
        }
    }

    private void giveInitialCardsToPlayer() {
        player.receiveCard(gameShoe.giveTopCard());
        player.receiveCard(gameShoe.giveTopCard());
    }

    private void giveInitialCardsToHouse() {
        house.receiveCard(gameShoe.giveTopCard());
        house.receiveCard(gameShoe.giveTopCard());
    }

    // For test purpose only
    protected Blackjack(House house, Player player, BlackjackShoe gameShoe, BlackjackGameState gameState) {
        this.house = house;
        this.player = player;
        this.gameShoe = gameShoe;
        this.gameState = gameState;
    }
}
