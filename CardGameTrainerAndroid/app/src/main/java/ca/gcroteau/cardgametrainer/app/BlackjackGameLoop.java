package ca.gcroteau.cardgametrainer.app;

import android.widget.Toast;
import domain.decks.BlackjackShoe;
import domain.decks.CardDeckBuilder;
import domain.games.BlackjackGame;
//import domain.participant.BlackjackPlayer;
import domain.games.StartGameState;
import domain.participant.Player;

public class BlackjackGameLoop implements Runnable {
    private static final int MS_FOR_30FPS = 33;

    private String currentCommand = "";

    @Override
    public void run() {
        Player p = new Player();
        BlackjackGame g = new BlackjackGame(p, new BlackjackShoe(1, new CardDeckBuilder()), new StartGameState());

        while(true) {
            if(currentCommand.equals("askCard")) {
                p.askACard();
                currentCommand = "";

                System.out.println("-------------------------");
                System.out.println("Ask card command received");
                System.out.println("-------------------------");
            }
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

    public void sendCommand() {
        currentCommand = "askCard";
    }
}
