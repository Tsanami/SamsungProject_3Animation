package com.example.samsungproject.gameobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.samsungproject.R;

/*
Джойстик, состоящий из двух кругов
*/
public class Joystick {
    // Параметры большого круга ( отвечает за пределы выхода маленького круга )
    private int bigPosX;
    private int bigPosY;
    private int bigRad;
    private Paint bigPaint;

    // Параметры контроллера ( двигается именно маленький круг )
    private int ctrlPosX;
    private int ctrlPosY;
    private int ctrlRad;
    private Paint ctrlPaint;

    private double pressedInBig; // Нажат ли большой круг
    private boolean isPressed;
    private boolean isFirst;
    // Коэффициенты перемешения контроллера
    public double ctrlCoefX;
    public double ctrlCoefY;

    Bitmap atkBut, bigJk, controller;


    public Joystick(int circlePosX, int circlePosY, int bigCircleRad, int smallCircleRad, Context context){

        this.bigRad = bigCircleRad;
        bigPosX = circlePosX;
        bigPosY = circlePosY;
        bigPaint = new Paint();
        bigPaint.setColor(Color.GRAY);
        bigPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        this.ctrlRad = smallCircleRad;
        ctrlPosX = circlePosX;
        ctrlPosY = circlePosY;
        ctrlPaint = new Paint();
        ctrlPaint.setColor(Color.BLUE);
        ctrlPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        atkBut = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_button_test, bitmapOptions);
        bigJk = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_joystick, bitmapOptions);
        controller = BitmapFactory.decodeResource(context.getResources(), R.drawable.controller, bitmapOptions);
    }

    public void drawWalk(Canvas canvas) {
        canvas.drawBitmap(bigJk, bigPosX-150, bigPosY-150, bigPaint);
        canvas.drawBitmap(controller, ctrlPosX-50, ctrlPosY-50, bigPaint);
    }
    public void drawGun(Canvas canvas) {
        canvas.drawBitmap(bigJk, bigPosX-150, bigPosY-150, bigPaint);
        canvas.drawBitmap(atkBut, ctrlPosX-130, ctrlPosY-129, bigPaint);
    }

    // Проверка на нажатие джойстика
    public boolean isPressed(double tX, double tY) {

        /* При помощи теоремы пифагора проверяем, нажат ли большой круг в его радиусе
    Нажатие в круге: bigRad = 40 Если нажатие вне круга: bigRad = 40
    bigPosX = 50 bigPosX = 50
    bigPosY = 50 bigPosY = 50
    tX = 40 tX = 300
    tY = 40 tY = 300
    Тогда distLilBig = √( (50 - 40)^2 + (50 - 40)^2 ) = 14 distLilBig = 353 < 40 -> isPressed = false
    distLilBig < bigRad
    14 < 40
    Следовательно isPressed = true
    */
        pressedInBig = Math.sqrt( Math.pow(bigPosX - tX, 2) + Math.pow(bigPosY - tY, 2));
        return pressedInBig < bigRad;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    // Изменение коэффициентов при нажатии на джойстик
    public void setCtrlCoef(double tX, double tY) {
        double dX = tX - bigPosX;
        double dY = tY - bigPosY;
        double deltaDistance = Math.sqrt( Math.pow(dX, 2) + Math.pow(dY, 2));

        if (deltaDistance < bigRad){
            ctrlCoefX = dX/ bigRad;
            ctrlCoefY = dY/ bigRad;
        }else{
            ctrlCoefX = dX/deltaDistance;
            ctrlCoefY = dY/deltaDistance;
        }
    }

    public void resetCtrlCoef() {
        ctrlCoefX = 0.0;
        ctrlCoefY = 0.0;
    }

    public void update() {
        // Обновление позиции контроллера
        ctrlPosX = (int) (bigPosX + ctrlCoefX * bigRad);
        ctrlPosY = (int) (bigPosY + ctrlCoefY * bigRad);
    }
    public void setIsFirst(boolean first) {
        isFirst = first;
    }

    public boolean isFirst() {
        return isFirst;
    }
    public void clear(){
        resetCtrlCoef();
        setIsFirst(false);
        setIsPressed(false);
    }
}