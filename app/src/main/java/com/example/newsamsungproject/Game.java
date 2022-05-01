package com.example.newsamsungproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.newsamsungproject.gamepanel.Joystick;
import com.example.newsamsungproject.gamepanel.Perfomance;
import com.example.newsamsungproject.graphics.Animator;
import com.example.newsamsungproject.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Context context;
    private Joystick joystickWalk, joystickGun; // Джойстик
    int circleX, circleY, Rad, rad;
    Player player; // Игрок
    private List<Enemy> enemyList = new ArrayList<>();
    public static Resources res;
    GameLoop gameLoop;
    float hs;
    float ws;
    boolean isFirstDraw = true;
    GameMap gameMap;
    private List<Spell> spellList = new ArrayList<Spell>();
    private int numberOfSpellsToCast = 0;
    private float touchX, touchY;
    private Perfomance perfomance;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);
        getHolder().addCallback(this);
        res = getResources();

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        perfomance = new Perfomance(context, gameLoop);
        this.context = context;
        gameLoop = new GameLoop(this, surfaceHolder);


        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
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
            joystickWalk = new Joystick(circleX, circleY, Rad, rad);
            joystickGun = new Joystick(getWidth() - circleX, circleY, Rad, rad);
            SpriteSheet spriteSheet = new SpriteSheet(context);
            Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
            player = new Player(context,ws / 2, (hs/2)+120, joystickWalk, animator);
            isFirstDraw = false;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);
        }
        player.draw(canvas, gameDisplay); // Рисовать игрока
        joystickWalk.draw(canvas); // Рисовать джойстик
        joystickGun.draw(canvas);
        perfomance.draw(canvas);
        for (Enemy enemy: enemyList){
            enemy.draw(canvas, gameDisplay);
        }
        update();
    }


    public void update(){
        player.update();
        joystickWalk.update();
        joystickGun.update();
        if (player.isJump) player.jump();

        while (numberOfSpellsToCast > 0){
            spellList.add(new Spell(getContext(), player, touchX, touchY));
        }

        for (Spell spell: spellList) {
            spell.update();
        }

        if (Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }
        for (Enemy enemy : enemyList){
            enemy.update();
        }
        Iterator<Enemy> enemytIterator = enemyList.iterator();
        while (enemytIterator.hasNext()){
            if (Enemy.isColliding(enemytIterator.next(), player)){
                enemytIterator.remove();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
//            switch (event.getActionMasked()) {
//                case MotionEvent.ACTION_POINTER_DOWN:
//                    if (player.jumpIsPressed(event.getX(1), event.getY(1)))
//                        player.isJump = true; // Player's jump
//                    if ((joystickWalk.getIsPressed()) && (!player.isJump)) {
//                        touchX = event.getX(1);
//                        touchY = event.getY(1);
//                        numberOfSpellsToCast++;
//                        Log.d("Screen touched: ", String.valueOf(player.x));
//                    } else if (joystickWalk.isPressed(event.getX(1), event.getY(1))) {
//                        joystickWalk.setIsPressed(true);
//                    } else if (joystickGun.isPressed(event.getX(1), event.getY(1))) {
//                        joystickGun.setIsPressed(true);
//                    }
//
//                case MotionEvent.ACTION_MOVE:
//                    if (joystickWalk.getIsPressed()) {
//                        joystickWalk.setActuator(event.getX(1), event.getY(1));
//                    }
//                    if (joystickGun.getIsPressed()) {
//                        joystickGun.setActuator(event.getX(1), event.getY(1));
//                    }
//                    break;
//                case MotionEvent.ACTION_POINTER_UP:
//                    joystickWalk.setIsPressed(false);
//                    joystickWalk.resetActuator();
//                    joystickGun.setIsPressed(false);
//                    joystickGun.resetActuator();
//                    break;
//            }
//        }
//        if ((event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)) {
//            if (player.jumpIsPressed(event.getX(1), event.getY(1))) player.isJump = true; // Player's jump
//            if ((joystickWalk.getIsPressed()) && (!player.isJump)) {
//                touchX = event.getX(1);
//                touchY = event.getY(1);
//                numberOfSpellsToCast++;
//                Log.d("Screen touched: ", String.valueOf(player.x));
//            }
//        }
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (player.jumpIsPressed(event.getX(1), event.getY(1)))
                    player.isJump = true; // Player's jump
                if ((joystickWalk.getIsPressed()) && (!player.isJump)) {
                    touchX = event.getX(1);
                    touchY = event.getY(1);
                    numberOfSpellsToCast++;
                    Log.d("Screen touched: ", String.valueOf(player.x));
                } else if (joystickWalk.isPressed(event.getX(1), event.getY(1))) {
                    joystickWalk.setIsPressed(true);
                }
                if (joystickGun.isPressed(event.getX(1), event.getY(1))) {
                    joystickGun.setIsPressed(true);
                }
                //joystickGun.setIsPressed(true);
                break;
            case MotionEvent.ACTION_DOWN:
                if (player.jumpIsPressed(event.getX(), event.getY())) player.isJump = true;

                if ((joystickWalk.getIsPressed())){
                    touchX = event.getX();
                    touchY = event.getY();
                    numberOfSpellsToCast++;
                    Log.d("Screen touched: ", String.valueOf(player.x));
                }
                else if (joystickWalk.isPressed(event.getX(), event.getY())){
                    joystickWalk.setIsPressed(true);
                }
                else if (!player.isJump){
                    touchX = event.getX();
                    touchY = event.getY();
                    numberOfSpellsToCast++;
                    Log.d("Screen touched: ", String.valueOf(player.x));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (joystickWalk.getIsPressed()){
                    joystickWalk.setActuator(event.getX(), event.getY());
                }
                if (joystickGun.getIsPressed()){
                    joystickGun.setActuator(event.getX(1), event.getY(1));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isJoystick(0, event.getActionIndex())) {
                    joystickWalk.setIsPressed(false);
                    joystickWalk.resetActuator();
                    break;
                }
                break;
            case MotionEvent.ACTION_POINTER_1_UP:
                joystickGun.setIsPressed(false);
                joystickGun.resetActuator();

                break;

        }
        return true;
    }

    public boolean isJoystick(int n, int index){
        return n == index;
    }

    private void whichJoystick(int pointerId, boolean isPressed, MotionEvent event) {
        if (isPressed)
            joystickGun.setActuator(event.getX(pointerId), event.getY(pointerId));
    }
    public void pause(){
        gameLoop.stopLoop();
    }
}

