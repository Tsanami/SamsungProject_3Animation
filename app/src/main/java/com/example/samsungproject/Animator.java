package com.example.samsungproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Animator {
    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE = 5;
    private final Player player;
    private State state;
    private Rect[] rect = new Rect[7];
    private int updatesBeforeNextMoveFrame;
    private int idxNotMovingFrameR = 0;
    private int idxNotMovingFrameL = 6;
    private int idxMovingFrameR = 2; // move right
    private int idxMovingFrameL = 4; // move left
    private boolean dir; // direction of player
    private static Bitmap bitmap;

    public Animator(Context context, Player player) {
        int width = 167;
        int height = 250;
        int distance = 9;
        rect[0] = new Rect(0,0, width, height);
        rect[1] = new Rect(width+distance, 0,width*2+distance, 250);
        rect[2] = new Rect(width*2+distance*2,0,width*3+distance*2, 250);
        rect[3] = new Rect(width*3+distance*3,0,width*4+distance*3 , 250);
        rect[4] = new Rect(0,height+distance, width,height*2+distance);
        rect[5] = new Rect(width+distance,height+distance, width*2+distance,height*2+distance);
        rect[6] = new Rect(width*2+distance*2,height+distance, width*3+distance*2,height*2+distance);

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

//        switch (state){
//            case NOT_MOVING:
//                if (player.getVelX() != 0 || player.getVelY() != 0 ) {
//                    state = State.STARTED_MOVING;
//                }
//                break;
//            case STARTED_MOVING:
//                if (player.getVelX() != 0 || player.getVelY() != 0 ) {
//                    state = State.IS_MOVING;
//                }
//                break;
//            case IS_MOVING:
//                if (player.getVelX() == 0 && player.getVelY() == 0) {
//                    state = State.NOT_MOVING;
//                }
//                break;
//            default:
//                break;
//        }
//
//        switch (state){
//            case NOT_MOVING:
//                if (dir)
//                    drawFrame(canvas, player, idxNotMovingFrameR);
//                else
//                    drawFrame(canvas, player, idxNotMovingFrameL);
//                break;
//            case STARTED_MOVING:
//                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE;
//                if (player.getVelX() < 0)
//                    drawFrame(canvas, player, idxMovingFrameL);
//                else
//                    drawFrame(canvas, player, idxMovingFrameR);
//                break;
//            case IS_MOVING:
//                updatesBeforeNextMoveFrame--;
//                dir = player.getVelX() > 0;
//                if (updatesBeforeNextMoveFrame==0){
//                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE;
//                    toggleIdxMovingFrame(dir);
//                }
//                if (player.getVelX() > 0)
//                    drawFrame(canvas, player, idxMovingFrameR);
//                else
//                    drawFrame(canvas, player, idxMovingFrameL);
//                break;
//        }
        switch (state){
            case NOT_MOVING:
                if (player.getVelX() != 0 || player.getVelY() != 0 ) {
                    state = State.STARTED_MOVING;
                    break;
                }
                break;
            case STARTED_MOVING:
                if (player.getVelX() != 0 || player.getVelY() != 0 ) {
                    state = State.IS_MOVING;
                    break;
                }
                break;
            case IS_MOVING:
                if (player.getVelX() == 0 && player.getVelY() == 0) {
                    state = State.NOT_MOVING;
                    break;
                }
                break;
            default:
                break;
        }

        switch (state){
            case NOT_MOVING:
                if (dir)
                    drawFrame(canvas, player, idxNotMovingFrameR);
                else
                    drawFrame(canvas, player, idxNotMovingFrameL);
                break;
            case STARTED_MOVING:
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE;
                if (player.getVelX() < 0)
                    drawFrame(canvas, player, idxMovingFrameL);
                else
                    drawFrame(canvas, player, idxMovingFrameR);
                break;
            case IS_MOVING:
                updatesBeforeNextMoveFrame--;
                dir = player.getVelX() > 0;
                if (updatesBeforeNextMoveFrame==0){
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE;
                    toggleIdxMovingFrame(dir);
                }
                if (player.getVelX() > 0)
                    drawFrame(canvas, player, idxMovingFrameR);
                else
                    drawFrame(canvas, player, idxMovingFrameL);
                break;
        }
    }

    private void toggleIdxMovingFrame(boolean dir) {
        if (dir) {
            if (idxMovingFrameR == 2)
                idxMovingFrameR = 3;
            else
                idxMovingFrameR = 2;
        }
        else{
            if (idxMovingFrameL == 4)
                idxMovingFrameL = 5;
            else
                idxMovingFrameL = 4;
        }
    }

    public void drawFrame(Canvas canvas, Player player, int ind) {
        int playerX = (int)player.getPosX();
        int playerY = (int)player.getPosY();
        canvas.drawBitmap(bitmap, rect[ind], new Rect(playerX, playerY, playerX+167, playerY+250), null);
    }

}
