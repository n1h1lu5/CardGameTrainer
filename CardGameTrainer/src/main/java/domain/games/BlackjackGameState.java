package domain.games;

public abstract class BlackjackGameState {
    protected BlackjackGameState nextState;

    public abstract void update(BlackjackGame blackjackGame);
}
