package com.example.samsungproject.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.example.samsungproject.R;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero_sprites_test, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray(){
        Sprite[] spriteArray = new Sprite[7];
        int width = 167;
        int height = 250;
        int distance = 9;
        spriteArray[0] = new Sprite(this, new Rect(0,0, width, height));
        spriteArray[1] = new Sprite(this, new Rect(width+distance, 0,width*2+distance, 250));
        spriteArray[2] = new Sprite(this, new Rect(width*2+distance*2,0,width*3+distance*2, 250));
        spriteArray[3] = new Sprite(this, new Rect(width*3+distance*3,0,width*4+distance*3 , 250));
        spriteArray[4] = new Sprite(this, new Rect(0,height+distance, width,height*2+distance));
        spriteArray[5] = new Sprite(this, new Rect(width+distance,height+distance, width*2+distance,height*2+distance));
        spriteArray[6] = new Sprite(this, new Rect(width*2+distance*2,height+distance, width*3+distance*2,height*2+distance));

        Log.d("PlayerState", "doing smth");
        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
