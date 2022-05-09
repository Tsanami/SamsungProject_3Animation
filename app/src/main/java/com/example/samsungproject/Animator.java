package com.example.samsungproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Animator {
    private static final int MAX_SPRITES_PER_SECOND = 5;
    private int updatesBeforeNextMoveFrame;

    private final Player player;
    private static Bitmap bitmap;
    private boolean dir; // direction of player


    private State state;
    private Rect standingR;
    private Rect standingL;
    private Rect[] moveR = new Rect[3];
    private Rect[] moveL = new Rect[3];
    private int idxR = 0;
    private int idxL = 0;
    private boolean isStanding = true;

    public Animator(Context context, Player player) {
        int width = 167;
        int height = 250;
        int distance = 9;

        standingR =  new Rect(0,0, width, height);
        standingL = new Rect(width*2+distance*2,height+distance, width*3+distance*2,height*2+distance);
        moveR[0] = new Rect(width+distance, 0,width*2+distance, 250);
        moveR[1] = new Rect(width*2+distance*2,0,width*3+distance*2, 250);
        moveR[2] = new Rect(width*3+distance*3,0,width*4+distance*3 , 250);
        moveL[0] = new Rect(width*2+distance*2,height+distance, width*3+distance*2,height*2+distance);
        moveL[1] = new Rect(0,height+distance, width,height*2+distance);
        moveL[2] = new Rect(width+distance,height+distance, width*2+distance,height*2+distance);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_sprites_test, bitmapOptions);

        this.player = player;
        this.state = State.NOT_MOVING;
    }

    public enum State{
        NOT_MOVING,
        STARTED_MOVING,
        IS_MOVING
    }

    public void draw(Canvas canvas) {
        float pVX = player.getVelX();
        float pVY = player.getVelY();
        isStanding = pVX==0;
        whichState(pVX, pVY);
        switch (state){
            case NOT_MOVING:
                drawSprite(canvas, player, isStanding);
                break;
            case STARTED_MOVING:
                updatesBeforeNextMoveFrame = MAX_SPRITES_PER_SECOND;
                drawSprite(canvas, player, isStanding);
                break;
            case IS_MOVING:
                updatesBeforeNextMoveFrame--;
                dir = pVX > 0;
                if (updatesBeforeNextMoveFrame==0){
                    updatesBeforeNextMoveFrame = MAX_SPRITES_PER_SECOND;
                    if (dir) {
                        if (idxR >= 2)
                            idxR = 0;
                        idxR++;
                    }
                    else {
                        if (idxL >= 2)
                            idxL = 0;
                        idxL++;
                    }
                }
                drawSprite(canvas, player, isStanding);
                break;
        }
    }

    private void whichState(float pVX, float pVY){
        if (state == State.NOT_MOVING && (pVX != 0 || pVY != 0)) {
            state = State.STARTED_MOVING;
            isStanding = false;
        }
        else if (state == State.STARTED_MOVING && (pVX != 0 || pVY != 0 )) {
            state = State.IS_MOVING;
            isStanding = false;
        }
        else if (state == State.IS_MOVING && ((pVX == 0 && pVY == 0))) {
            state = State.NOT_MOVING;
            isStanding = true;
        }
    }
    public void drawSprite(Canvas canvas, Player player, boolean isStanding) {
        int playerX = (int)player.getPosX();
        int playerY = (int)player.getPosY();
        Rect sRect = new Rect(playerX, playerY, playerX+167, playerY+250);

        if (isStanding) {
            if (dir)
                canvas.drawBitmap(bitmap, standingR, sRect, null);
            else
                canvas.drawBitmap(bitmap, standingL, sRect, null);
        }
        else {
            if (dir) {
                canvas.drawBitmap(bitmap, moveR[idxR], sRect, null);
            } else {
                canvas.drawBitmap(bitmap, moveL[idxL], sRect, null);
            }
        }
    }

}
