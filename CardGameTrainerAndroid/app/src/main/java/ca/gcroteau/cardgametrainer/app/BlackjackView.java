package ca.gcroteau.cardgametrainer.app;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BlackjackView extends SurfaceView implements SurfaceHolder.Callback {

    private Thread displayThread;

    public BlackjackView(Context context) {
        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback( this);

        displayThread = new Thread(new BlackjackGameLoop());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        displayThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            displayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do nothing
    }
}
