package domain.games;

public class EndPlayState extends BlackjackGameState {
    private BlackjackGameState1 gameState;

    public EndPlayState() {
        gameState = new BlackjackGameState1();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 6");

        computeOutcome(blackjackGame);
    }

    private void computeOutcome(BlackjackGame blackjackGame) {
        if (gameState.playerAndHouseAreEven(blackjackGame.getPlayerHand(), blackjackGame.getHouseHand())) {
            blackjackGame.givePlayerEvenGains();
            //player.receiveGains(0);
        } else if (gameState.hasBlackjack(blackjackGame.getPlayerHand())) {
            blackjackGame.givePlayerBlackjackGains();
            //int gain = (int) (playerBet * BLACKJACK_PAY_FACTOR);
            //player.receiveGains(gain);
        } else if (gameState.playerBeatsHouse(blackjackGame.getPlayerHand(), blackjackGame.getHouseHand())) {
            blackjackGame.givePlayerStandardGains();
            //player.receiveGains(playerBet);
        } else {
            blackjackGame.takePlayerBet();
            //player.loseBet(playerBet);
        }
    }

    // For test purpose only
    protected EndPlayState(BlackjackGameState1 state) {
        this.gameState = state;
    }
}
