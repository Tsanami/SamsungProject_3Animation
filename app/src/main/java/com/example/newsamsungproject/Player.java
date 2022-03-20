package com.example.newsamsungproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {

    float jumpCount = 10; // прыжок игрока
    boolean isJump = false; // падать или нет
    Bitmap image = BitmapFactory.decodeResource(Game.res , R.drawable.obito);
    float x, y, tX = 0, tY = 0;
    float dx = 0, dy = 0;
    float jumpX = 1700, jumpY = 600, jumpRadius = 95; // Параметры кнопки прыжка

    float k = 600f/30f; // velocity or koeff
    float hi, wi;//ширина и высота изображения
    Paint paint;
    Paint jumpBtPaint = new Paint();

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }


    //    public Player(){
//        this.x = x;
//        this.y = y;
//
//    }

    public void update(Joystick joystick) {
        dx = (float)joystick.getActuatorX() * k;
        //dy = (float)joystick.getActuatorY() * k;
        x += dx;
        //y += dy;
    }

    //расчет смещения картинки по x и y
    void delta(){
        double ro = Math.sqrt((tX- x)*(tX- x)+(tY- y)*(tY- y));
        dx = (float) (k * (tX - x)/ro);
        //dy = (float) (k * (tY - iY)/ro);
    }

    void jump(){
        if ((jumpCount >= -10) && (isJump)){
            y -= jumpCount * Math.abs(jumpCount) *0.5;
            jumpCount -= 1;
            delta();
        }

        else {
            jumpCount = 10;
            isJump = false;
        }
    }


    public void draw(Canvas canvas) {
        jumpBtPaint.setColor(Color.GRAY);
        canvas.drawBitmap(image, x, y, paint);
        canvas.drawCircle(jumpX, jumpY, jumpRadius, jumpBtPaint);
    }


    public boolean jumpIsPressed(double jTX, double jTY) { // jTX - jumpTouchY
        double isPressed = Math.sqrt(Math.pow(jumpX - jTX, 2) + Math.pow(jumpY - jTY, 2));
        return isPressed < jumpRadius;
    }
}