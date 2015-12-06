package domain.games;

public class DistributeCardsState extends BlackjackGameState {

    public DistributeCardsState() {
        nextState = new PlayersTurnState();
    }

    @Override
    public void update(BlackjackGame blackjackGame) {
        System.out.println("state 3");
        startNewPlay(blackjackGame);

        blackjackGame.changeState(nextState);
    }

    private void startNewPlay(BlackjackGame blackjackGame) {
        giveInitialCardsToPlayer(blackjackGame);
        giveInitialCardsToHouse(blackjackGame);
    }

    private void giveInitialCardsToPlayer(BlackjackGame blackjackGame) {
        blackjackGame.givePlayerACard();
        blackjackGame.givePlayerACard();
    }

    private void giveInitialCardsToHouse(BlackjackGame blackjackGame) {
        blackjackGame.giveHouseACard();
        blackjackGame.giveHouseACard();
    }
}
