package com.example.samsungproject;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread{
    private static final int MAX_UPS = 60;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private final double UPS_PERIOD = 1E+3/MAX_UPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public long startTime, elapsedTime, sleepTime;
    public void startLoop() {
        isRunning = true;
        start();
    }
     
    @Override
    public void run() {
        super.run();

        int updateCount = 0;

        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while (isRunning){
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
//                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }
            }
            catch (IllegalArgumentException e){
                e.printStackTrace();
            }
            finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            if(sleepTime > 0){
                try {
                    sleep(sleepTime);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            while (sleepTime < 0 && updateCount < MAX_UPS-1){
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                updateCount = 0;

                startTime = System.currentTimeMillis();
            }
        }
    }

    public void setRunning(boolean b) {
        isRunning = b;
    }

    public void stopLoop() {
        isRunning = false;
        try{
            join();
        }catch (InterruptedException e ) {
            e.printStackTrace();
        }
    }
}