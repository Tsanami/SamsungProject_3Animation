package com.example.samsungproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.samsungproject.gameobjects.Circle;
import com.example.samsungproject.gamepanel.Joystick;
import com.example.samsungproject.gamepanel.Perfomance;
import com.example.samsungproject.graphics.Animator;
import com.example.samsungproject.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Context context;
    private Joystick joystickWalk; // Джойстик для ходьбы
    private Joystick joystickGun; // Джойстик для оружия
    private Player player; // Игрок
    Paint paint;
    public static Resources res;
    GameLoop gameLoop;
    float hs, ws;//ширина и высота области рисования
    boolean isFirstDraw = true;
    GameMap gameMap;
    private List<Enemy> enemyList = new ArrayList<>();
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
    public void surfaceCreated(SurfaceHolder holder){
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
            joystickWalk = new Joystick(275, 660, 140, 80);
            joystickGun = new Joystick(1550, 660, 140, 80);
            SpriteSheet spriteSheet = new SpriteSheet(context);
            Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());
            player = new Player(context,ws / 2, (hs/2)+120, joystickWalk, animator);
            isFirstDraw = false;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);
        }

        //gameMap.draw(canvas); // Рисовать карту

        player.draw(canvas, gameDisplay); // Рисовать игрока

        for (Spell spell: spellList) {
            spell.draw(canvas, gameDisplay);
        }
        joystickWalk.draw(canvas); // Рисовать джойстик для ходьбы
        joystickGun.draw(canvas); // Рисовать джойстик для пушки
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
            numberOfSpellsToCast--;
        }

        if (Enemy.readyToSpawn()){
            enemyList.add(new Enemy(getContext(), player));
        }
        for (Enemy enemy: enemyList) {
            enemy.update();
        }
        Iterator<Enemy> enemyIterator = enemyList.iterator();
        while (enemyIterator.hasNext()){
            Circle enemy = enemyIterator.next();
            if (Enemy.isColliding(enemy, player)){
                enemyIterator.remove();
                continue;
            }
            Iterator<Spell> spellIterator = spellList.iterator();
            while (spellIterator.hasNext()){
                Circle spell = spellIterator.next();
                if (Enemy.isSpellColliding(enemy, spell)){
                    spellIterator.remove();
                    enemyIterator.remove();
                    break;
                }
            }
        }
        for (Spell spell: spellList) {
            spell.update();
        }

        gameDisplay.update();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                }else if (joystickGun.isPressed(event.getX(), event.getY())){
                    joystickGun.setIsPressed(true);
                }
                else if (!player.isJump){
                    touchX = event.getX();
                    touchY = event.getY();
                    numberOfSpellsToCast++;
                    Log.d("Screen touched: ", String.valueOf(player.x));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (joystickWalk.getIsPressed() && (joystickWalk.isPressed(event.getX(), event.getY()))){
                    joystickWalk.setActuator(event.getX(), event.getY());
                }
//                if (joystickGun.getIsPressed() && (joystickGun.isPressed(event.getX(0), event.getY(0)))){
//                    joystickGun.setActuator(event.getX(), event.getY());
//                }
//                if (event.getPointerId(1) == 1){
//                    Log.d("2nd joystick move ", "moved");
//                    if (joystickGun.getIsPressed()){
//                        joystickGun.setActuator(event.getX(1), event.getY(1));
//                    }
//                }
                Log.d("PointerCount ", String.valueOf(event.getPointerCount()));
                whichJoystick(event.getPointerCount()-1, joystickGun.getIsPressed(), event);

                break;
            case MotionEvent.ACTION_UP:
                if (joystickWalk.getIsPressed()) {
                    joystickWalk.setIsPressed(false);
                    joystickWalk.resetActuator();
                    break;
                }
                if (joystickGun.getIsPressed()) {
                    joystickGun.setIsPressed(false);
                    joystickGun.resetActuator();
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

    public void whichJoystick(int pointerId, boolean isPressed, MotionEvent e){
        if (isPressed)
            joystickGun.setActuator(e.getX(pointerId), e.getY(pointerId));
    }
    public void pause() {
        gameLoop.stopLoop();
    }
}