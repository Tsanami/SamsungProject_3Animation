package com.example.newsamsungproject.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.newsamsungproject.Player;
import com.example.newsamsungproject.R;

public class HealthBar {
    float borderLeft = 1500, borderTop = 90, borderRight = 1800, borderBottom = 140;
    Paint borderPaint = new Paint();
    Player player;

    public HealthBar(Context context, Player player){
        int borderColor = ContextCompat.getColor(context, R.color.hp_color);
        borderPaint.setColor(borderColor);
        this.player = player;
    }

    public void draw(Canvas canvas, HealthBar healthBar){
        float hpPercentage = (float)player.getHP()/player.MAX_HP;
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);
        //canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);
    }
}
