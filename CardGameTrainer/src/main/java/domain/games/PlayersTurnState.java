package domain.games;

public class PlayersTurnState extends BlackjackGameState {

    public PlayersTurnState() {
        nextState = new HomeTurnState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 4");

        askPlayerToPlay(blackjackGame);
        blackjackGame.changeState(nextState);
    }

    private void askPlayerToPlay(BlackjackGame blackjackGame) {
        while (!blackjackGame.hasPlayerBusted()/* && player.wantsANewCard()*/) {
            // TODO: Will be re-added when player becomes a state object
            blackjackGame.givePlayerACard();
        }
    }
}
