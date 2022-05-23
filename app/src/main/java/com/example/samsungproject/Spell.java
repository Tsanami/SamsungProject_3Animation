package com.example.samsungproject;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Spell{

    private final float radius;
    private Paint paint;
    private float x;
    private float y;
    private float velX;
    private float velY;
    public Spell(Context context, Player player, float tX, float tY ) {
        this.x = player.x;
        this.y = player.y;
        this.radius = 25;
        this.paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.white));
        double ro = Math.sqrt((Math.abs(tX)- Math.abs(player.x))*(Math.abs(tX)- Math.abs(player.x))+(tY- player.y)*(tY- player.y));
        this.velX = (float) (20 * (Math.abs(tX) - Math.abs(player.x))/ro);
        this.velY = (float) (20 * (tY - player.y)/ro);
    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(x+35, y+20, radius, paint);
    }
    public void update() {
        x += velX;
        y += velY;
    }

    public float getRadius() {
        return radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
