package com.example.newsamsungproject.gamepanel;

import android.content.Context;
import android.graphics.Canvas;

import com.example.newsamsungproject.GameLoop;

public class Perfomance {
    GameLoop gameLoop;
    Context context;

    public Perfomance(Context context, GameLoop gameLoop){
        this.context = context;
        this.gameLoop = gameLoop;
    }

    public void draw(Canvas canvas){
    }
}
