package com.example.newsamsungproject.graphics;

import android.graphics.Canvas;

import com.example.newsamsungproject.GameDisplay;
import com.example.newsamsungproject.Player;

public class Animator {
    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE = 5;

    private Sprite[] playerSpiteArray;
    private int updatesBeforeNextMoveFrame;
    private int idxNotMovingFrameR = 0;
    private int idxNotMovingFrameL = 6;
    private int idxMovingFrameR = 2; // move right
    private int idxMovingFrameL = 4; // move left
    private boolean dir; // direction of player

    public Animator(Sprite[] playerSpriteArray) {
        this.playerSpiteArray = playerSpriteArray;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player) {
        switch (player.getPlayerState().getState()){
            case NOT_MOVING:
                if (dir)
                    drawFrame(canvas, gameDisplay, player, playerSpiteArray[idxNotMovingFrameR]);
                else
                    drawFrame(canvas, gameDisplay, player, playerSpiteArray[idxNotMovingFrameL]);
                break;
            case STARTED_MOVING:
                updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE;
                if (player.getVelX() < 0)
                    drawFrame(canvas,gameDisplay,player, playerSpiteArray[idxMovingFrameL]);
                else
                    drawFrame(canvas, gameDisplay, player, playerSpiteArray[idxMovingFrameR]);
                break;
            case IS_MOVING:
                updatesBeforeNextMoveFrame--;
                dir = player.getVelX() > 0;
                if (updatesBeforeNextMoveFrame==0){
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE;
                    toggleIdxMovingFrame(dir);
                }
                if (player.getVelX() > 0)
                    drawFrame(canvas, gameDisplay, player, playerSpiteArray[idxMovingFrameR]);
                else
                    drawFrame(canvas, gameDisplay, player, playerSpiteArray[idxMovingFrameL]);
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

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {
        sprite.draw(canvas, (int)gameDisplay.gameToDisplayCoordinatesX((float)player.getPosX()), (int)gameDisplay.gameToDisplayCoordinatesY((float)player.getPosY()));
    }
}
