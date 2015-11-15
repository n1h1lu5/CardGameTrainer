package ca.gcroteau.cardgametrainer.app;

import domain.games.BlackjackGame;
import domain.participant.BlackjackPlayer;
import domain.participant.Player;

public class BlackjackGameLoop implements Runnable {

    @Override
    public void run() {
        BlackjackPlayer p = new BlackjackPlayer(new Player());
        BlackjackGame g = new BlackjackGame(p);

        while(true) {
            g.update();
            delay();
        }
    }

    private void delay() {
        try {
            Thread.sleep(33);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
