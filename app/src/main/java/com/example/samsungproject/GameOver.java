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
        Paint restartPaint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(150);
        restartPaint.setColor(Color.WHITE);
        restartPaint.setTextSize(30);
        canvas.drawText("Game Over", ws/3, hs/2, paint);
        canvas.drawText("Press anywhere to restart", ws/2-170, hs/2+100, restartPaint);
    }
}
