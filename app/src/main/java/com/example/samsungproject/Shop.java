package com.example.samsungproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shop {
    Paint paint = new Paint();
    int width;
    int height;
    int menuX;
    int menuY;
    int dmgX;
    int dmgY;
    int hpX;
    int hpY;
    int radius = 100;
    double pressedOnMenu;
    boolean isPressed;

    public Shop(int width, int height) {
        this.width = menuX;
        this.height = menuY;
        this.menuX = width - width/10;
        this.menuY = height/10;
        this.dmgX = width - width/10 - radius/2;
        this.dmgY = 310;
        this.hpX = dmgX - 250;
        this.hpY = dmgY;
    }

    public void drawMenu(Canvas canvas){
        paint.setColor(Color.WHITE);
        canvas.drawCircle(menuX, menuY, radius, paint);
    }
    public boolean menuIsPressed(double tX, double tY){
        pressedOnMenu = Math.sqrt(Math.pow(menuX - tX, 2)+ Math.pow(menuY - tY, 2));
        return pressedOnMenu < radius;
    }
    public void  drawDMGUP(Canvas canvas){

        paint.setColor(Color.RED);
        canvas.drawCircle(dmgX,dmgY,radius,paint);
    }
    public void drawHPUP(Canvas canvas){
        paint.setColor(Color.GREEN);
        canvas.drawCircle(hpX,hpY,radius,paint);
    }

    public void setIsPressed(boolean b) {
        this.isPressed = b;
    }
    public boolean getIsPressed(){
        return isPressed;
    }
}
