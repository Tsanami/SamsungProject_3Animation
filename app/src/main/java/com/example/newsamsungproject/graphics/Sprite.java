package com.example.newsamsungproject.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private final Rect rect;
    private final SpriteSheet spriteSheet;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(x, y, x+167, y+250),
                null
        );
    }
}
