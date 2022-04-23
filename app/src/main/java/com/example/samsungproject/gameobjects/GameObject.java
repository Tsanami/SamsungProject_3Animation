package com.example.samsungproject.gameobjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.samsungproject.GameDisplay;
import com.example.samsungproject.gamepanel.Joystick;

public abstract class GameObject {
    protected float posX;
    protected float posY;
    protected float velX = 0;
    protected float velY = 0;

    public GameObject(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);
    public abstract void update();

    public float getPosX(){ return 0; }
    public float getPosY(){ return 0; }


}
