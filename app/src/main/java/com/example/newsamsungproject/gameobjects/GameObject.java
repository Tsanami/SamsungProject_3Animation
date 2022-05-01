package com.example.newsamsungproject.gameobjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.newsamsungproject.GameDisplay;

public abstract class GameObject {
    protected float posX;
    protected float posY;
    protected float velX = 0;
    protected float velY = 0;

    public GameObject(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }

    public GameObject(Context context) {
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();

    public double getPosX(){ return posX; }
    public double getPosY(){ return posY; }
}
