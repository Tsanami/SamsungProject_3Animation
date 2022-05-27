package com.example.samsungproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class Melee {
    private int x,y,rad;
    private int xShifter,yShifter;
    private Paint paint;
    private static Bitmap meleePNG, shifterPNG, meleeCirclePNG;
    private int shifterRad = 76;
    private int meleeRad = 140;
    private boolean isMelee=false;
    public Melee(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.red));
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        meleePNG = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword, bitmapOptions);
        meleeCirclePNG = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword_circle, bitmapOptions);
        shifterPNG = BitmapFactory.decodeResource(context.getResources(), R.drawable.weapon_shifter, bitmapOptions);
        xShifter = x-240;
        yShifter = y+170;
    }

    public void drawMelee(Canvas canvas){
        canvas.drawCircle(x,y+275,meleeRad-50,paint);
        canvas.drawBitmap(meleeCirclePNG, x-140,y+150,paint);
    }

    public void drawWeaponShifter(Canvas canvas){
        canvas.drawCircle(xShifter,yShifter,shifterRad-50,paint);
        canvas.drawBitmap(shifterPNG, xShifter-110,yShifter-110,paint);
    }

    public boolean shifterIsPressed(double sTX, double sTY) {
        double isPressed = Math.sqrt(Math.pow(xShifter - sTX, 2) + Math.pow(yShifter - sTY, 2));
        return isPressed < shifterRad;
    }

    public boolean isMelee() {
        return isMelee;
    }
    public void setMelee(boolean melee) {
        isMelee = melee;
    }
}
