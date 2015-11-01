package domain.games;

public class PlayersTurnState extends BlackjackGameState {

    private BlackjackGameState1 gameState;

    public PlayersTurnState() {
        gameState = new BlackjackGameState1();
        nextState = new HomeTurnState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 4");

        askPlayerToPlay(blackjackGame);
        blackjackGame.changeState(nextState);
    }

    private void askPlayerToPlay(BlackjackGame blackjackGame) {
        while (!gameState.hasBusted(blackjackGame.getPlayerHand())/* && player.wantsANewCard()*/) {
            // TODO: Will be re-added when player becomes a state object
            blackjackGame.givePlayerACard();
        }
    }

    // For tests purpose only
    protected PlayersTurnState(BlackjackGameState1 state) {
        gameState = state;
        nextState = new HomeTurnState();
    }
}
