package com.example.samsungproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shop {
    Paint paint = new Paint();
    Paint textPaint = new Paint();
    int width;
    int height;
    int menuX;
    int menuY;
    int dmgX;
    int dmgY;
    int hpX;
    int hpY;
    int closeMenuX;
    int closeMenuY;
    int radius = 100;
    double pressedOnMenu;
    double pressedOnCloseMenu;
    double pressedOnDmgUp;
    double pressedOnHpUp;
    boolean isPressed;

    public Shop(int width, int height) {
        this.width = menuX;
        this.height = menuY;
        this.menuX = width - width/10;
        this.menuY = height/10;
        this.closeMenuX = menuX - 250;
        this.closeMenuY = menuY;
        this.dmgX = width - width/10 - radius/2;
        this.dmgY = 310;
        this.hpX = dmgX - 250;
        this.hpY = dmgY;
    }


    public boolean menuIsPressed(double tX, double tY){
        pressedOnMenu = Math.sqrt(Math.pow(menuX - tX, 2)+ Math.pow(menuY - tY, 2));
        return pressedOnMenu < radius;
    }
    public boolean dmgIsPressed(double tX, double tY){
        pressedOnDmgUp = Math.sqrt(Math.pow(dmgX - tX, 2)+ Math.pow(dmgY - tY, 2));
        return pressedOnDmgUp < radius;
    }
    public boolean hpIsPressed(double tX, double tY){
        pressedOnHpUp = Math.sqrt(Math.pow(hpX - tX, 2)+ Math.pow(hpY - tY, 2));
        return pressedOnHpUp < radius;
    }
    public boolean closeMenuIsPressed(double tX, double tY){
        pressedOnCloseMenu = Math.sqrt(Math.pow(closeMenuX - tX, 2)+ Math.pow(closeMenuY - tY, 2));
        return pressedOnCloseMenu < radius;
    }
    public void drawMenu(Canvas canvas){

        paint.setColor(Color.WHITE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
        canvas.drawCircle(menuX, menuY, radius, paint);
        canvas.drawText("Shop", menuX, menuY+15, textPaint);
    }
    public void drawDMGUP(Canvas canvas){
        paint.setColor(Color.RED);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(25);
        canvas.drawCircle(dmgX,dmgY,radius,paint);
        canvas.drawText("DMG UP: 10 C", dmgX, dmgY+10, textPaint);
    }
    public void drawHPUP(Canvas canvas){
        paint.setColor(Color.GREEN);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(25);
        canvas.drawCircle(hpX,hpY,radius,paint);
        canvas.drawText("HP UP: 10 C", hpX, hpY+10, textPaint);
    }
    public void drawCloseMenu(Canvas canvas){
        paint.setColor(Color.WHITE);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(50);
        canvas.drawCircle(closeMenuX, closeMenuY, radius, paint);
        canvas.drawText("Close", closeMenuX, closeMenuY+15, textPaint);
    }

    public void setIsPressed(boolean b) {
        this.isPressed = b;
    }
    public boolean getIsPressed(){
        return isPressed;
    }
}
