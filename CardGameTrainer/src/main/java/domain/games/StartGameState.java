package domain.games;

public class StartGameState extends BlackjackGameState {

    public StartGameState() {
        nextState = new TakeBetsState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 1");
        blackjackGame.changeState(nextState);
    }
}
