package com.example.newsamsungproject;

public class GameDisplay {
    private float gameToDisplayCoordinateOffsetX;
    private float gameToDisplayCoordinateOffsetY;
    private float displayCenterX;
    private float gameCenterX;
    private float displayCenterY;
    private float gameCenterY;
    private float x, y;
    private Player player;


    public GameDisplay(int widthPixels, int heightPixels, Player player){
        this.player = player;

        displayCenterX = (float) (widthPixels/2.0);
        displayCenterY = (float) (heightPixels/2.0);
    }

    public void update(){
        gameCenterX = (float) player.getPosX();
        gameCenterY = (float)player.getPosY();
//        gameCenterX = x;
//        gameCenterY = y;

        gameToDisplayCoordinateOffsetX = displayCenterX - gameCenterX;
        gameToDisplayCoordinateOffsetY = displayCenterY - gameCenterY;
    }

    public float gameToDisplayCoordinatesX(float x) {
        return x + gameToDisplayCoordinateOffsetX;
    }

    public float gameToDisplayCoordinatesY(float y) {
        return y + gameToDisplayCoordinateOffsetY;
    }
}
