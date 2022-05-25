package com.example.samsungproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Countable {
    int coins;
    int score;
    Paint paint;

    public Countable() {
        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
    }

    public void setCoins(int coins) {
        this.coins += coins;
    }

    public void setScore(int score) {
        this.score += score;
    }
    public void minCoin(int bye){
        this.coins -= bye;
    }

    public void drawCoins(Canvas canvas){
        canvas.drawText("Coins: "+String.valueOf(coins), 10, 25, paint);
    }
    public void drawScore(Canvas canvas){
        canvas.drawText("Score: "+String.valueOf(score), 10, 65, paint);
    }
    public int getCoins(){ return coins;}
    public int getScore(){ return score;}
}