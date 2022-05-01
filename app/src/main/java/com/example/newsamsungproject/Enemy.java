package com.example.newsamsungproject;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.newsamsungproject.gameobjects.Circle;

public class Enemy extends Circle {
    private static final float SPEED_PIXELS_PER_SECOND = 600f/5f;
    private static final float MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS;
    private static final float SPAWNS_PER_MINUTE = 30;
    private static final float SPAWNS_PER_SECOND = (float) (SPAWNS_PER_MINUTE/60.0);
    private static final float UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static float updatesUntilSpawn = UPDATES_PER_SPAWN;
    private final Player player;
    float velocityX, velocityY;

    public Enemy(Context context, Player player) {
        super(context,
                ContextCompat.getColor(context, R.color.teal_200),
                100,
                (float) Math.random()*1000,
                MainActivity.getScreenHeight()-250);
        this.player = player;
    }

    public static boolean readyToSpawn() {
        if (updatesUntilSpawn <= 0){
            updatesUntilSpawn += UPDATES_PER_SPAWN;
            return true;
        } else{
            updatesUntilSpawn --;
            return false;
        }
    }



//    public float getPositionX() {
//        return eX;
//    }
//
//    public float getPositionY() {
//        return eY;
//    }

    public static double getDistanceBetweenObjects(Enemy enemy, Player player){
        return Math.sqrt(Math.pow((player.getPosX() - enemy.getPosX()), 2) +
                Math.pow((player.getPosY() - enemy.getPosY()), 2));
    }

    public static boolean isColliding(Enemy enemy, Player player) {
        float distance = (float)getDistanceBetweenObjects(enemy, player);
        float distanceToCollision = enemy.getRadius(enemy);
        if (distance < distanceToCollision) return true;
        else return false;
    }

    public void update(){
        double distanceToPlayerX = player.getPosX() - posX;
        double distanceToPlayerY = player.getPosY() - posY;
        double distanceToPlayer = getDistanceBetweenObjects(this, player);
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;
        if (distanceToPlayer > 0){
            velocityX = (float) (directionX*MAX_SPEED);
            velocityY = (float) (directionY*MAX_SPEED);

        }
        else {
            velocityX = 0;
            velocityY = 0;
        }
        posX += velocityX;
        posY += velocityY;
    }



}
