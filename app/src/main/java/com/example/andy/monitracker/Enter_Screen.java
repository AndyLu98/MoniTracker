package com.example.andy.monitracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Enter_Screen extends AppCompatActivity {

    static Map<String, Each_Entry> All_Data;
    static public ArrayList<String> All_Item_Name;
    static public ArrayList<Serializable_Bitmap> All_Photos;
    static public ArrayList<String> All_Item_ID;
    static public ArrayList<String> All_Item_date;
    static public ArrayList<String> All_Item_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__screen);
        All_Item_Name = new ArrayList<String>();
        All_Photos = new ArrayList<Serializable_Bitmap>();
        All_Item_ID = new ArrayList<String>();
        All_Item_date = new ArrayList<String>();
        All_Item_price = new ArrayList<String>();
        read_file();
        ReadFromSharPref();
    }

    protected void onStop() {
        super.onStop();
        save_counter();
        Log.d("counter:", "onStop: savedcounter");
        Log.d("counter", "# counter: " + MainActivity.object_counter);
    }
    protected void onDestroy() {
        super.onDestroy();
        save_counter();
        Log.d("counter:", "onStop: savedcounter");
        Log.d("counter", "# counter: " + MainActivity.object_counter);
    }

    public void createAList(View view){
        All_Data = new HashMap< String, Each_Entry>();
    }

    public void transition(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void history(View view){
        if (All_Data.size() == 0){
            Toast.makeText(getBaseContext(),"No Entry",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, Spendinghistory.class);
            startActivity(intent);
        }
    }

    public void read_file(){
        try {
            File directory = getDir("data",MODE_PRIVATE);
            File file = new File(directory,"map");
            FileInputStream fis = new FileInputStream((file));
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<String, Each_Entry> loaded_file = (Map<String, Each_Entry>) ois.readObject();
            All_Data = loaded_file;
        } catch (Exception e) {
            All_Data = new HashMap<>();
            e.printStackTrace();
        }
    }

    public void save_counter(){
        SharedPreferences sharedpref = getSharedPreferences("MoniTrackerCounter",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt("ObjectCounter",MainActivity.object_counter);
        editor.apply();
    }

    public void ReadFromSharPref(){
        SharedPreferences SharedPref = getSharedPreferences("MoniTrackerCounter", Context.MODE_PRIVATE);
        MainActivity.object_counter = SharedPref.getInt("ObjectCounter",0);
    }

}
