package games;

import java.util.List;

import participant.House;
import participant.Player;

public class Blackjack {
    private static final int BLACKJACK_HAND_SIZE = 2;
    public static final float BLACKJACK_PAY_FACTOR = 1.5f;
    public static final int SCORE_BUST_LIMIT = 21;

    private House house;

    private Player player;
    private int playerBet;

    public Blackjack(House house, Player player) {
        this.player = player;
        this.house = house;
    }

    public void startNewPlay() {
        playerBet = player.decideBet();
        giveInitialCardsToPlayer();
    }

    public void askEveryoneToPlay() {
        while (!hasBusted(calculatePlayerScore()) && player.wantsANewCard()) {
            player.receiveCard(1);
        }
    }

    public void computeOutcome() {
        int playerScore = calculatePlayerScore();
        int houseScore = calculateHouseScore();

        if (hasBlackjack(playerScore)) {
            int gain = (int) (playerBet * BLACKJACK_PAY_FACTOR);
            player.receiveGains(gain);
        } else if (hasBusted(playerScore)) {
            player.loseBet(playerBet);
        } else if (playerBeatsHouse(playerScore, houseScore)) {
            player.receiveGains(playerBet);
        } else if (houseBeatsPlayer(playerScore, houseScore)) {
            player.loseBet(playerBet);
        } else {
            player.receiveGains(0);
        }
    }

    private void giveInitialCardsToPlayer() {
        player.receiveCard(1);
        player.receiveCard(1);
    }

    private boolean houseBeatsPlayer(int playerScore, int houseScore) {
        return playerScore < houseScore;
    }

    private boolean playerBeatsHouse(int playerScore, int houseScore) {
        return (playerScore > houseScore) || (houseScore > SCORE_BUST_LIMIT && playerScore <= SCORE_BUST_LIMIT);
    }

    private int calculateHouseScore() {
        return calculateScore(house.getHand());
    }

    private int calculatePlayerScore() {
        return calculateScore(player.getHand());
    }

    private int calculateScore(List<Integer> hand) {
        int score = 0;
        for (int card : hand)
            score += card;
        return score;
    }

    private boolean hasBusted(int score) {
        return score > SCORE_BUST_LIMIT;
    }

    private boolean hasBlackjack(int score) {
        return score == SCORE_BUST_LIMIT && player.getHand().size() == BLACKJACK_HAND_SIZE;
    }
}
