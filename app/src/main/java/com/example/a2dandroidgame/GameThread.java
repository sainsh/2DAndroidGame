package com.example.a2dandroidgame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameView gameView, SurfaceHolder surfaceHolder) {
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        while (running) {
            Canvas canvas = null;

            try{
                //get canvas from holder and lock it
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas){
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }catch (Exception e){

            }finally {
                if(canvas!=null){
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            //draw time
            long now = System.nanoTime();
            long waitTime = (now-startTime)/1000000;
            if(waitTime <10){
                waitTime = 10;
            }
            System.out.println(" Wait time= " + waitTime);

            try{
                this.sleep(waitTime);

            }catch (InterruptedException e){

            }
            startTime = System.nanoTime();
            System.out.println(".");
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
