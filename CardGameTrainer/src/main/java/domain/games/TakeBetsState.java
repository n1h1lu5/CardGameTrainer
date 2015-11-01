package domain.games;

public class TakeBetsState extends BlackjackGameState {

    public TakeBetsState() {
        nextState = new DistributeCardsState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 2");
        blackjackGame.changeState(nextState);
    }
}
