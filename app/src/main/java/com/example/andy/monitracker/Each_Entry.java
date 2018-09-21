package com.example.andy.monitracker;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Andy on 5/7/18.
 */

class Each_Entry implements Serializable, Comparable<Each_Entry> {
    public String Item_name;
    public String Date;
    public String Price;
    public Serializable_Bitmap Photo;
    public String id;

    public Each_Entry(String item_name, String date, String price, Serializable_Bitmap photo, String ID) {
        Item_name = item_name;
        Date = date;
        Price = price;
        Photo = photo;
        id = ID;
    }

    @Override
    public int compareTo(@NonNull Each_Entry o) {
        return Date.compareTo(o.Date);
    }

}

