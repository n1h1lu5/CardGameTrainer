package ca.gcroteau.cardgametrainer.app;

import domain.games.BlackjackGame;
import domain.participant.BlackjackPlayer;
import domain.participant.Player;

public class BlackjackGameLoop implements Runnable {
    private static final int MS_FOR_30FPS = 33;

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
            Thread.sleep(MS_FOR_30FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
