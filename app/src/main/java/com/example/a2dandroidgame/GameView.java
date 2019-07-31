package com.example.a2dandroidgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private PlayerObject player;


    public GameView(Context context) {
        super(context);

        //get focus to get user events
        this.setFocusable(true);

        //set callbacks
        getHolder().addCallback(this);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        this.player.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Bitmap playerBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.character);
        this.player = new PlayerObject(this, playerBitmap, 100, 50);

        this.gameThread = new GameThread(this, surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try{
                this.gameThread.setRunning(false);

                //app thread has to wait until gameThread is done
                this.gameThread.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = true;
        }
    }

    public void update() {
        this.player.update();
    }
}
