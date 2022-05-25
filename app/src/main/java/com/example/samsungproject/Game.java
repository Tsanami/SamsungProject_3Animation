package com.example.samsungproject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.samsungproject.gameobjects.Joystick;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final Context context;
    private Joystick joystickWalk; // Джойстик для ходьбы
    private Joystick joystickGun; // Джойстик для оружия
    private Player player; // Игрок
    Countable countable;
    Paint paint;
    public static Resources res;
    GameLoop gameLoop;
    float hs, ws;//ширина и высота области рисования
    boolean isFirstDraw = true;
    GameMap gameMap;
    private List<Spell> spellList = new ArrayList<Spell>();
    private List<Enemy> enemyList = new ArrayList<>();
    private int numberOfSpellsToCast = 0;
    private float touchX, touchY;
    int coins=1;
    int score=1;

    public Game(Context context) {
        super(context);
        getHolder().addCallback(this);
        res = getResources();

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

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
            countable = new Countable();
            joystickWalk = new Joystick(275, 660, 140, 80);
            joystickGun = new Joystick(1550, 660, 140, 80);
            player = new Player(context,ws / 2, (hs/2)+120, joystickWalk);
            isFirstDraw = false;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        //gameMap.draw(canvas); // Рисовать карту
        countable.drawCoins(canvas);
        countable.drawScore(canvas);

        player.draw(canvas); // Рисовать игрока

        for (Spell spell: spellList) {
            spell.draw(canvas);
        }
        for (Enemy enemy: enemyList){
            enemy.draw(canvas);
        }
        joystickWalk.draw(canvas); // Рисовать джойстик для ходьбы
        joystickGun.draw(canvas); // Рисовать джойстик для пушки


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
            Enemy enemy = enemyIterator.next();
            if (Enemy.isColliding(enemy, player)){
                enemyIterator.remove();
                continue;
            }
            Iterator<Spell> spellIterator = spellList.iterator();
            while (spellIterator.hasNext()){
                Spell spell = spellIterator.next();
                if (Enemy.isSpellColliding(enemy, spell)){
                    countable.setCoins(coins);
                    countable.setScore(score);
                    Log.d("aaaa", "dadada");
                    spellIterator.remove();
                    enemyIterator.remove();
                    break;
                }
            }
        }
        for (Spell spell: spellList) {
            spell.update();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount()-1;

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
                    if (joystickWalk.getIsPressed() && !joystickGun.isFirst())
                        joystickWalk.setIsFirst(true);
                }else if (joystickGun.isPressed(event.getX(), event.getY())){
                    joystickGun.setIsPressed(true);
                    if (joystickGun.getIsPressed() && !joystickWalk.isFirst())
                        joystickGun.setIsFirst(true);
                }
                else if (!player.isJump){
                    touchX = event.getX();
                    touchY = event.getY();
                    numberOfSpellsToCast++;
                    Log.d("Screen touched: ", String.valueOf(player.x));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (joystickGun.getIsPressed() && joystickGun.isFirst()){
                    if (pointerCount==0)
                        moveGunJk(0, joystickGun.getIsPressed(), event);
                    else if (joystickWalk.getIsPressed()){
                        moveGunJk(0, joystickGun.getIsPressed(), event);
                        moveWalkJk(1, joystickWalk.getIsPressed(), event);
                    }
                }
                else if (joystickWalk.getIsPressed() && joystickWalk.isFirst()){
                    if (pointerCount==0)
                        moveWalkJk(0, joystickWalk.getIsPressed(), event);
                    else if (joystickWalk.getIsPressed()){
                        moveWalkJk(0, joystickWalk.getIsPressed(), event);
                        moveGunJk(1, joystickGun.getIsPressed(), event);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (joystickGun.getIsPressed() && joystickGun.isFirst()){
                    if (pointerCount==0) {
                        joystickGun.clear();
                        break;
                    }
                }
                if (joystickWalk.getIsPressed() && !joystickWalk.isPressed(touchX, touchY)) {
                    joystickWalk.clear();
                    break;
                }
                break;

        }
        return true;
    }


    public void moveGunJk(int pointerId, boolean isPressed, MotionEvent e){
        if (isPressed)
            joystickGun.setCtrlCoef(e.getX(pointerId), e.getY(pointerId));
    }
    public void moveWalkJk(int pointerId, boolean isPressed, MotionEvent e){
        if (isPressed)
            joystickWalk.setCtrlCoef(e.getX(pointerId), e.getY(pointerId));
    }
    public void pause() {
        gameLoop.stopLoop();
    }

}