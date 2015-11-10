package ca.gcroteau.cardgametrainer.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import domain.games.BlackjackGame;

public class BlackjackScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blackjack_screen);

        View blackjackScreen = findViewById(R.id.blackjack_screen);
        blackjackScreen.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        BlackjackGame g = new BlackjackGame();

        while(true) {
            g.update();
        }
    }
}
