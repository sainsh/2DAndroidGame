package com.example.a2dandroidgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PlayerObject extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    private int currentRow = ROW_LEFT_TO_RIGHT;

    private int currentCol;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

    public static final float VELOCITY = 0.1f;

    private int movingVectorX = 10;
    private int movingVectorY = 5;

    private long lastDrawNanoTime = -1;

    private GameView gameView;

    public PlayerObject(GameView gameView, Bitmap image, int x, int y) {
        super(image, 4, 3, x, y);

        this.gameView = gameView;

        this.topToBottoms = new Bitmap[colCount]; // 3
        this.rightToLefts = new Bitmap[colCount]; // 3
        this.leftToRights = new Bitmap[colCount]; // 3
        this.bottomToTops = new Bitmap[colCount]; // 3

        for (int col = 0; col < this.colCount; col++) {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col] = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col] = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
    }

    public Bitmap[] getMovementBitmaps() {
        switch (currentRow) {
            case ROW_BOTTOM_TO_TOP:
                return this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMovementBitmap() {
        Bitmap[] bitmaps = this.getMovementBitmaps();
        return bitmaps[this.currentCol];
    }

    public void update() {
        this.currentCol++;
        if (currentCol >= this.colCount) {
            this.currentCol = 0;
        }
        //current time
        long now = System.nanoTime();

        //if never drawn
        if (lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }

        //nanotime to milli
        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);

        //distance traveled
        float distance = VELOCITY * deltaTime;

        double movingVectorLength =
                Math.sqrt(movingVectorX * movingVectorX + movingVectorY * movingVectorY);

        //determine x and y
        this.x = x + (int) (distance * movingVectorX / movingVectorLength);
        this.y = y + (int) (distance * movingVectorY / movingVectorLength);

        // When the game's character touches the edge of the screen, then change direction

        if(this.x < 0 )  {
            this.x = 0;
            this.movingVectorX = - this.movingVectorX;
        } else if(this.x > this.gameView.getWidth() -width)  {
            this.x= this.gameView.getWidth()-width;
            this.movingVectorX = - this.movingVectorX;
        }

        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.gameView.getHeight()- height)  {
            this.y= this.gameView.getHeight()- height;
            this.movingVectorY = - this.movingVectorY ;
        }

        //currentRow
        if (movingVectorX > 0) {
            if (movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_TOP_TO_BOTTOM;
            } else if (movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_BOTTOM_TO_TOP;
            } else {
                this.currentRow = ROW_LEFT_TO_RIGHT;
            }
        } else {
            if (movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_TOP_TO_BOTTOM;
            } else if (movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.currentRow = ROW_BOTTOM_TO_TOP;
            } else {
                this.currentRow = ROW_RIGHT_TO_LEFT;
            }

        }
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getCurrentMovementBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
        //update lastDrawTime
        this.lastDrawNanoTime = System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY) {
        this.movingVectorX = movingVectorX;
        this.movingVectorY = movingVectorY;
    }
}
