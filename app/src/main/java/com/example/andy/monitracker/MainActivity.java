package com.example.andy.monitracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView itemphoto;
    public EditText editText_item;
    public EditText editText_date;
    public EditText editText_price;
    public Bitmap photo;
    public Serializable_Bitmap photo2;
    Button takephoto;
    Button confirm;
    Button Fill_Date;
    static public int object_counter;
    String Item_id;
    String Item_name;
    String Item_date;
    String Item_price;
    Each_Entry This_Entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemphoto = findViewById(R.id.imageView);
        takephoto = findViewById(R.id.button4);
        takephoto.setText("Take Photo");
        confirm = findViewById(R.id.button);
        Fill_Date = findViewById(R.id.button7);
        editText_item = findViewById(R.id.editText);
        editText_date = findViewById(R.id.editText2);
        editText_price = findViewById(R.id.editText3);

        Intent intent = getIntent();
        Item_name = intent.getStringExtra("item_key");
        Item_price = intent.getStringExtra("price_key");
        Item_date = intent.getStringExtra("date_key");
        Item_id = intent.getStringExtra("id_key");
        Bitmap photo = intent.getParcelableExtra("photo_key");
        editText_item.setText(Item_name);
        editText_price.setText(Item_price);
        editText_date.setText(Item_date);
        itemphoto.setImageBitmap(photo);
    }

    public void confirm (View view){
        if (editText_item.getText().toString().equals("")|| editText_date.getText().toString().equals("") || editText_price.getText().toString().equals("")) {
            Toast.makeText(getBaseContext(),"Missing Inputs",Toast.LENGTH_LONG).show();
        }else{
            try {
                BitmapDrawable drawable = (BitmapDrawable) itemphoto.getDrawable();
                Serializable_Bitmap item_photo = new Serializable_Bitmap(drawable.getBitmap());
                This_Entry = new Each_Entry(editText_item.getText().toString(), editText_date.getText().toString(), editText_price.getText().toString(), item_photo, Integer.toString(object_counter));
            }catch (Exception e){
                This_Entry = new Each_Entry(editText_item.getText().toString(), editText_date.getText().toString(), editText_price.getText().toString(), null,Integer.toString(object_counter));
            }
            Map<String, Each_Entry> temp = new HashMap<>();
            if (Enter_Screen.All_Data.keySet().size() != 0) {
                boolean flag = true;
                for (String key : Enter_Screen.All_Data.keySet()) {
                    if (key.equals(Item_id)) {
                        temp.put(Item_id, This_Entry);
                        //Log.d("deBug2", Item_id);
                        flag = false;
                        break;
                    }
                }
                Enter_Screen.All_Data.putAll(temp);
                if (flag) {
                    Enter_Screen.All_Data.put(Integer.toString(object_counter), This_Entry);
                    object_counter = object_counter + 1;
                    save_counter();
                }
            } else {
                Enter_Screen.All_Data.put(Integer.toString(object_counter), This_Entry);
                object_counter = object_counter + 1;
                save_counter();
            }

            Toast.makeText(getBaseContext(), "Save Successful", Toast.LENGTH_SHORT).show();

            Save_Data(Enter_Screen.All_Data);

            Intent intent = new Intent(this, Enter_Screen.class);
            startActivity(intent);
        }
    }

    public void clear (View view){
        editText_item.getText().clear();
        editText_date.getText().clear();
        editText_price.getText().clear();
        itemphoto.setImageDrawable(null);
    }

    public void fill_date(View view){
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy/MM/dd").format(cDate);
        Log.d("Date", "fill_date: " + fDate);
        editText_date.setText(fDate);
    }

    public void TakePhoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        takephoto.setText("Retake Photo");
    }


    @Override
    protected void onActivityResult(int RequestCode ,int ResultCode,Intent data){
        if(RequestCode == REQUEST_IMAGE_CAPTURE){
            try {
                Bundle extra = data.getExtras();
                photo = (Bitmap) extra.get("data");
                photo2 = new Serializable_Bitmap(photo);
                itemphoto.setImageBitmap(photo2.getBitmap());
            }catch (Exception e){
                photo = null;
                photo2 = null;
            }
        }
    }

    public void Save_Data(Map<String,Each_Entry> input){
        try {
            File file = new File(getDir("data", MODE_PRIVATE), "map");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(fos);
            oout.writeObject(input);
            oout.flush();
            oout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save_counter(){
        SharedPreferences SharedPref = getSharedPreferences("MoniTrackerCounter",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.putInt("ObjectCounter",object_counter);
        editor.apply();
    }

}
