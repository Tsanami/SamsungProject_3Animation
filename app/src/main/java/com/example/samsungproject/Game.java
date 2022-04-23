package com.example.samsungproject;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.samsungproject.gamepanel.Joystick;


public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Joystick joystick; // Джойстик
    private final Player player; // Игрок

    Paint paint;

    public static Resources res;

    GameLoop gameLoop;

    float hs, ws;//ширина и высота области рисования
    boolean isFirstDraw = true;
    GameMap gameMap;

    Rect imageRect;

    public Game(Context context) {
        super(context);
        getHolder().addCallback(this);
        res = getResources();

        player = new Player();
        joystick = new Joystick(275, 700, 140, 80);

        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(5);
        setAlpha(0);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameLoop = new GameLoop(getHolder(), this);
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        gameLoop.setRunning(false);
        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(isFirstDraw){
            hs = getHeight();
            ws = getWidth();

            gameMap = new GameMap((int)ws, (int)hs, res);

            player.x = ws / 2; // Параметры игрока
            player.y = hs-100; //4 * hs / 5 ; // Параметры игрока

            isFirstDraw = false;

        }

        gameMap.draw(canvas); // Рисовать карту

        player.draw(canvas); // Рисовать игрока

        joystick.draw(canvas); // Рисовать джойстик

        update();
    }

    private void update(){
        player.update(joystick);
        joystick.update();

        if (player.isJump) player.jump();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed(event.getX(), event.getY())){
                    joystick.setIsPressed(true);
                }
            case MotionEvent.ACTION_POINTER_DOWN:
                if (player.jumpIsPressed(event.getX(), event.getY())){
                    player.isJump = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()){
                    joystick.setActuator(event.getX(), event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;

        }

        return true;
    }



}