package com.example.samsungproject;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.samsungproject.gameobjects.Circle;
import com.example.samsungproject.gameobjects.GameObject;

public class Enemy extends Circle {
    private static final float SPEED_PIXELS_PER_SECOND = 400f;
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
                (float) (player.getPosX()-(Math.random()*1000)),
                (float) Math.random()*1000,
                100);
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
    public static double getDistanceBetweenObjects(Circle enemy, GameObject gameObject){
        return Math.sqrt(Math.pow((gameObject.getPosX() - enemy.getPosX()), 2) +
                Math.pow((gameObject.getPosY() - enemy.getPosY()), 2));
    }

    public static boolean isColliding(Circle circle, GameObject gameObject) {
        float distance = (float)getDistanceBetweenObjects(circle, gameObject);
        float distanceToCollision = circle.getRadius(circle);
        if (distance < distanceToCollision) return true;
        else return false;
    }
    public static boolean isSpellColliding(Circle enemy, Circle circle) {
        float distance = (float)getDistanceBetweenObjects(enemy, circle);
        float distanceToCollision = enemy.getRadius(enemy) + circle.getRadius(circle);
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
