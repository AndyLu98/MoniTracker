package com.example.andy.monitracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Spendinghistory extends AppCompatActivity {

    RadioButton M_Recent;
    RadioButton L_Recent;
    static Boolean sort_most_recent = true;
    static Boolean sort_least_recent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendinghistory);
        //Log.d("boolean test", "onCreate: sort_m_recent" + "" + Boolean.toString(sort_most_recent));
        //Log.d("boolean test", "onCreate: sort_l_recent" + "" + Boolean.toString(sort_least_recent));

        M_Recent = findViewById(R.id.M_Recent);
        L_Recent = findViewById(R.id.L_Recent);

        View.OnClickListener sort_by_m_recent = new View.OnClickListener(){
            public void onClick(View v) {
                //Log.d("R Button", "onClick: RadioButton_M");
                Sort_from_M();
                sort_most_recent = true;
                sort_least_recent = false;

            }
        };

        M_Recent.setOnClickListener(sort_by_m_recent);

        final View.OnClickListener sort_by_l_recent = new View.OnClickListener(){
            public void onClick(View v) {
                //Log.d("R Button", "onClick: RadioButton_L");
                Sort_from_L();
                sort_most_recent = false;
                sort_least_recent = true;
            }
        };

        L_Recent.setOnClickListener(sort_by_l_recent);

        if (sort_most_recent == true && sort_least_recent == false) {
            Sort_from_M();
            M_Recent.setChecked(true);
        }else if (sort_most_recent == false && sort_least_recent == true){
            Sort_from_L();
            L_Recent.setChecked(true);
        }
//        }else if (sort_most_recent == false && sort_least_recent == true){
//            Sort_from_L();
//        }

        initRecycler();
    }

    private void initRecycler(){
        //Save_Data(Enter_Screen.All_Data);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(Enter_Screen.All_Item_Name, Enter_Screen.All_Photos, Enter_Screen.All_Item_ID, Enter_Screen.All_Item_date, Enter_Screen.All_Item_price);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d("DEBUG:ONSTOP", "onStop: Check if it is stopped");
        Save_Data(Enter_Screen.All_Data);
    }

    public void Save_Data(Map<String, Each_Entry> input) {
        try {
            File file = new File(getDir("data", MODE_PRIVATE), "map");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oout = new ObjectOutputStream(fos);
            oout.writeObject(input);
            oout.flush();
            oout.close();
        } catch (Exception e) {
            //Log.d("Exception", "Save_Data: ");
            e.printStackTrace();
        }
    }

    public void Sort_from_M(){
        Enter_Screen.All_Item_Name = new ArrayList<String>();
        Enter_Screen.All_Photos = new ArrayList<Serializable_Bitmap>();
        Enter_Screen.All_Item_ID = new ArrayList<String>();
        Enter_Screen.All_Item_date = new ArrayList<String>();
        Enter_Screen.All_Item_price = new ArrayList<String>();

        ArrayList<Each_Entry> temp_list = new ArrayList<>();
        for (String key:Enter_Screen.All_Data.keySet()) {
            temp_list.add(Enter_Screen.All_Data.get(key));
        }

        Collections.sort(temp_list);
        Collections.reverse(temp_list);

        for (Each_Entry i: temp_list){
            Enter_Screen.All_Item_Name.add(i.Item_name);
            Enter_Screen.All_Photos.add(i.Photo);
            Enter_Screen.All_Item_ID.add(i.id);
            Enter_Screen.All_Item_price.add(i.Price);
            Enter_Screen.All_Item_date.add(i.Date);
        }

        if (!sort_most_recent && sort_least_recent){
            Intent intent = new Intent(this,Spendinghistory.class);
            startActivity(intent);
        }
    }

    public void Sort_from_L(){
        Enter_Screen.All_Item_Name = new ArrayList<String>();
        Enter_Screen.All_Photos = new ArrayList<Serializable_Bitmap>();
        Enter_Screen.All_Item_ID = new ArrayList<String>();
        Enter_Screen.All_Item_date = new ArrayList<String>();
        Enter_Screen.All_Item_price = new ArrayList<String>();

        ArrayList<Each_Entry> temp_list = new ArrayList<>();
        for (String key:Enter_Screen.All_Data.keySet()) {
            temp_list.add(Enter_Screen.All_Data.get(key));
        }

        Collections.sort(temp_list);

        for (Each_Entry i: temp_list){
            Enter_Screen.All_Item_Name.add(i.Item_name);
            Enter_Screen.All_Photos.add(i.Photo);
            Enter_Screen.All_Item_ID.add(i.id);
            Enter_Screen.All_Item_price.add(i.Price);
            Enter_Screen.All_Item_date.add(i.Date);
        }
        if (sort_most_recent && !sort_least_recent) {
            Intent intent = new Intent(this, Spendinghistory.class);
            startActivity(intent);
        }

    }
}
