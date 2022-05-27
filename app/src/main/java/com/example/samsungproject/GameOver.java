package com.example.samsungproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameOver {
    boolean playerAlive = true;
    public boolean isPlayerAlive() {
        return playerAlive;
    }
    public void setPlayerAlive(boolean playerAlive) {
        this.playerAlive = playerAlive;
    }
    public void drawGameOver(Canvas canvas, int ws, int hs){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(150);
        canvas.drawText("Game Over", ws/3, hs/2, paint);
    }
}
