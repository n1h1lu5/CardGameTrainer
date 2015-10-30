package ca.gcroteau.cardgametrainer.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);
        View homeScreen = findViewById(R.id.home_screen);
        homeScreen.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void startBlackjack(View view) {
        Intent intent = new Intent(this, BlackjackScreen.class);
        startActivity(intent);
    }

    public void displayAbout(View view) {
        Intent intent = new Intent(this, AboutScreen.class);
        startActivity(intent);
    }

}
