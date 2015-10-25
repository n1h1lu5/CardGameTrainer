package ca.gcroteau.cardgametrainer.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import domain.participant.Player;

public class Blackjack extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blackjack_screen);

        View homeScreen = findViewById(R.id.blackjack_screen);
        homeScreen.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        Player p = new Player();

        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText(tv.getText().toString() + p.decideBet());
    }
}
