package domain.games;

public class HomeTurnState extends BlackjackGameState {
    private BlackjackGameState1 gameState;

    public HomeTurnState() {
        gameState = new BlackjackGameState1();
        nextState = new EndPlayState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 5");
        askHouseToPlay(blackjackGame);
        blackjackGame.changeState(nextState);
    }

    private void askHouseToPlay(BlackjackGame blackjackGame) {
        while (!gameState.hasBusted(blackjackGame.getHouseHand()) /* && house.wantsNewCard()*/) {
            // TODO: Will be re-added when home becomes a state object
            blackjackGame.giveHouseACard();
            //house.receiveCard(gameShoe.giveTopCard());
        }
    }

    // For tests purpose only
    protected HomeTurnState(BlackjackGameState1 state) {
        gameState = state;
        nextState = new EndPlayState();
    }
}
