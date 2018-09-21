package com.example.andy.monitracker;

import android.graphics.Bitmap;
import java.io.Serializable;
/**
 * Created by Andy on 5/16/18.
 */

public class Serializable_Bitmap implements Serializable{
    private final int [] pixels;
    private final int width , height;

    Serializable_Bitmap(Bitmap bitmap){
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int [width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
    }

    Bitmap getBitmap(){
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }
}
