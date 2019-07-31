package com.example.a2dandroidgame;

import android.graphics.Bitmap;

public class GameObject {

    private Bitmap image;

    protected final int rowCount, colCount;

    private final int WIDTH, HEIGHT;

    protected final int width, height;

    protected int x, y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y) {
        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.x = x;
        this.y = y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = WIDTH / colCount;
        this.height = HEIGHT / rowCount;
    }

    //creates a smaller object from a spritesheet
    protected Bitmap createSubImageAt(int row, int col) {
        Bitmap subImage = Bitmap.createBitmap(image, col * width, row * height, width, height);
        return subImage;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

