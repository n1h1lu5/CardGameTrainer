package games;

import participant.House;
import participant.Player;

public class Blackjack {
    private static final float BLACKJACK_PAY_FACTOR = 1.5f;

    private BlackjackGameState gameState;

    private House house;

    private Player player;
    private int playerBet;

    public Blackjack(House house, Player player) {
        this.gameState = new BlackjackGameState();
        this.player = player;
        this.house = house;
    }

    public void startNewPlay() {
        playerBet = player.decideBet();
        giveInitialCardsToPlayer();
        giveInitialCardsToHouse();
    }

    public void askPlayerToPlay() {
        while (!gameState.hasBusted(player.getHand()) && player.wantsANewCard()) {
            player.receiveCard(1);
        }
    }

    public void askHouseToPlay() {
        while (!gameState.hasBusted(house.getHand()) && house.wantsNewCard()) {
            house.receiveCard(1);
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
        player.receiveCard(1);
        player.receiveCard(1);
    }

    private void giveInitialCardsToHouse() {
        house.receiveCard(1);
        house.receiveCard(1);
    }

    // For test purpose only
    protected Blackjack(House house, Player player, BlackjackGameState gameState) {
        this.gameState = gameState;
        this.player = player;
        this.house = house;
    }
}
