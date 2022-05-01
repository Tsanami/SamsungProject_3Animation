package com.example.newsamsungproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.newsamsungproject.gameobjects.Circle;

public class Spell extends Circle {
    public Spell(Context context, Player player, float tX, float tY ) {
        super(context,
                ContextCompat.getColor(context, R.color.white),
                player.x, player.y,
                25);
        double ro = Math.sqrt((Math.abs(tX)- Math.abs(player.x))*(Math.abs(tX)- Math.abs(player.x))+(tY- player.y)*(tY- player.y));
        velX = (float) (20 * (Math.abs(tX) - Math.abs(player.x))/ro);
        velY = (float) (20 * (tY - player.y)/ro);
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle(gameDisplay.gameToDisplayCoordinatesX(posX+35), gameDisplay.gameToDisplayCoordinatesY(posY+20),  radius, paint);
    }

    @Override
    public void update() {
        posX += velX;
        posY += velY;
    }
}
