package com.example.samsungproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.samsungproject.gameobjects.GameObject;
import com.example.samsungproject.gameobjects.PlayerState;
import com.example.samsungproject.gamepanel.HealthBar;
import com.example.samsungproject.gamepanel.Joystick;
import com.example.samsungproject.graphics.Animator;
import com.example.samsungproject.graphics.Sprite;

public class Player extends GameObject{

    float jumpCount = 10; // прыжок игрока
    boolean isJump = false; // падать или нет
    Bitmap image = BitmapFactory.decodeResource(Game.res , R.drawable.idle1);
    float x, y, tX = 0, tY = 0;
    float jumpX = 1750, jumpY = 450, jumpRadius = 76; // Параметры кнопки прыжка
    public int MAX_HP = 10;

    float k = 600f/30f; // velocity or koeff
    float hi, wi;//ширина и высота изображения
    Paint paint;
    Paint jumpBtPaint = new Paint();
    private HealthBar healthBar;
    private int hp;
    Joystick joystick;
    private Animator animator;
    private PlayerState playerState;


    public Player(Context context, float x, float y, Joystick joystick, Animator animator){
        super(x,y);
        this.x = x;
        this.y = y;
        this.joystick = joystick;
        jumpBtPaint.setColor(Color.GRAY);
        this.animator = animator;
        this.healthBar = new HealthBar(context, this );
        this.hp = MAX_HP;
        this.playerState = new PlayerState(this);
    }



    //расчет смещения картинки по x и y
    void delta(){
        double ro = Math.sqrt((tX- x)*(tX- x)+(tY- y)*(tY- y));
        velX = (float) (k * (tX - x)/ro);
        //dy = (float) (k * (tY - iY)/ro);
    }

    void jump(){
        if ((jumpCount >= -10) && (isJump)){
            velY = jumpCount * Math.abs(jumpCount) *0.5f;
            y -= velY;
            jumpCount -= 1;
            delta();
        }

        else {
            jumpCount = 10;
            velY = 0;
            isJump = false;
        }
    }


    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        animator.draw(canvas, gameDisplay, this);
        canvas.drawCircle(jumpX, jumpY, jumpRadius, jumpBtPaint);
        healthBar.draw(canvas, healthBar);
    }

    public void update() {
        velX = (float)joystick.getActuatorX() * k;
        //dy = (float)joystick.getActuatorY() * k;
        x += velX;
        //y += dy;
        playerState.update();
    }

    public boolean jumpIsPressed(double jTX, double jTY) { // jTX - jumpTouchY
        double isPressed = Math.sqrt(Math.pow(jumpX - jTX, 2) + Math.pow(jumpY - jTY, 2));
        return isPressed < jumpRadius;
    }

    public int getHP() {
        return hp;
    }

    public float getPosX() {
        return x;
    }
    public float getPosY() {
        return y;
    }
    public float getVelX() { return velX; }
    public PlayerState getPlayerState() { return playerState; }
}
