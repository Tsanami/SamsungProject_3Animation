package com.example.samsungproject;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.samsungproject.gameobjects.Circle;
import com.example.samsungproject.gameobjects.GameObject;

public class Enemy extends Circle {
    private static final float SPEED_PIXELS_PER_SECOND = 600f/5f;
    private static final float MAX_SPEED = SPEED_PIXELS_PER_SECOND / 60;
    private static final float SPAWNS_PER_MINUTE = 30;
    private static final float SPAWNS_PER_SECOND = (float) (SPAWNS_PER_MINUTE/60.0);
    private static final float UPDATES_PER_SPAWN = 60 / SPAWNS_PER_SECOND;
    private static float updatesUntilSpawn = UPDATES_PER_SPAWN;
    private final Player player;
    float velocityX, velocityY;

    public Enemy(Context context, Player player) {
        super(context,
                ContextCompat.getColor(context, R.color.teal_200),
                100,
                (float) Math.random()*1000,
                75);
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

    public static double getDistanceBetweenObjects(Enemy enemy, GameObject gameObject){
        return Math.sqrt(Math.pow((gameObject.getPosX() - enemy.getPosX()), 2) +
                Math.pow((gameObject.getPosY() - enemy.getPosY()), 2));
    }
    public static double getDistanceSpellObjects(Enemy enemy, Spell spell){
        return Math.sqrt(Math.pow((spell.getX() - enemy.getPosX()), 2) +
                Math.pow((spell.getY() - enemy.getPosY()), 2));
    }

    public static boolean isColliding(Enemy enemy, Player player) {
//        float distance = (float)Math.sqrt(Math.pow((player.getPosX() - enemy.getPosX()), 2) + Math.pow((player.getPosY() - enemy.getPosY()), 2));
        float distance = (float)getDistanceBetweenObjects(enemy, player);
        Log.d("distance", String.valueOf(distance));
        float distanceToCollision = enemy.getRadius(enemy);
        Log.d("coldist", String.valueOf(distanceToCollision));
        if (distance < distanceToCollision) return true;
        else return false;
    }

    public static boolean isSpellColliding(Enemy enemy, Spell spell) {
        float distance = (float)getDistanceSpellObjects(enemy, spell);
        Log.d("spelldist", String.valueOf(distance));
        float distanceToCollision = 100;
        Log.d("adad",String.valueOf(distanceToCollision));
        if (distance < distanceToCollision) return true;
        else return false;
    }

    public void update(){
        double distanceToPlayerX = player.getPosX() - posX;
        double distanceToPlayerY = player.getPosY() - posY;
        double distanceToPlayer = (float)Math.sqrt(Math.pow((player.getPosX() - this.getPosX()), 2) + Math.pow((player.getPosY() - this.getPosY()), 2));
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