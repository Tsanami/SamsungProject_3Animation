package com.example.newsamsungproject.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.newsamsungproject.GameDisplay;

public abstract class Circle extends GameObject {
    protected float radius;
    protected Paint paint;

    public Circle(Context context, int color, float radius ,float posX, float posY) {
        super(posX, posY);
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
    }
    public float getRadius(Circle circle){
        return radius;
    }


    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle(posX, posY, radius, paint);
    }
}
