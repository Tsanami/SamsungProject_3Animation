package com.example.samsungproject;

import com.example.samsungproject.gameobjects.GameObject;

public class GameDisplay {
    private float gameToDisplayCoordinateOffsetX;
    private float gameToDisplayCoordinateOffsetY;
    private float displayCenterX;
    private float displayCenterY;
    private float gameCenterX;
    private float gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int widthPixels, int heightPixels, GameObject centerObject){
        this.centerObject = centerObject;

        displayCenterX = widthPixels / 2f;
        displayCenterY = heightPixels / 2f;

    }

    public void update(){
        gameCenterX = centerObject.getPosX();
        gameCenterY = centerObject.getPosY();

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
