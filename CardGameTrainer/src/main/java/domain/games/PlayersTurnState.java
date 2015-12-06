package domain.games;

public class PlayersTurnState extends BlackjackGameState {

    public PlayersTurnState() {
        //nextState = new HomeTurnState();
        nextState = new EndPlayState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 4");

        if (!blackjackGame.hasPlayerBusted())
            askPlayerToPlay(blackjackGame);
        else
            blackjackGame.changeState(nextState);
    }

    private void askPlayerToPlay(BlackjackGame blackjackGame) {
        if (blackjackGame.currentPlayerWantsCard())
            blackjackGame.givePlayerACard();
        else if (blackjackGame.currentPlayerStoppedHisTurn())
            blackjackGame.changeState(nextState);
    }
}
