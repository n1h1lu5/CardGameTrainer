package domain.games;

public class HomeTurnState extends BlackjackGameState {
    public HomeTurnState() {
        nextState = new EndPlayState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 5");
        askHouseToPlay(blackjackGame);
        blackjackGame.changeState(nextState);
    }

    private void askHouseToPlay(BlackjackGame blackjackGame) {
        while (!blackjackGame.hasPlayerBusted() /* && house.wantsNewCard()*/) {
            // TODO: Will be re-added when home becomes a state object
            // TODO: Will change for hasHouseBusted, or house is exactly the same as a player, execpt for the AI
            blackjackGame.giveHouseACard();
            //house.receiveCard(gameShoe.giveTopCard());
        }
    }
}
