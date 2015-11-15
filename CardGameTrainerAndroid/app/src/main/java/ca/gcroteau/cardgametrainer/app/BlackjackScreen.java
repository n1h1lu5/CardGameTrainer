package ca.gcroteau.cardgametrainer.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import domain.games.BlackjackGame;
import domain.participant.BlackjackPlayer;
import domain.participant.Player;

public class BlackjackScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SurfaceView gameView = new BlackjackView(this);
        setContentView(gameView);
    }
}
