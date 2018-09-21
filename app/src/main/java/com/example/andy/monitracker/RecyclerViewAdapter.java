package com.example.andy.monitracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Andy on 5/7/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList<String> ItemNames;
    private ArrayList<Serializable_Bitmap> Photos;
    private ArrayList<String> ItemID;

    private ArrayList<String> ItemDates;
    private ArrayList<String> ItemPrice;

    RecyclerViewAdapter(ArrayList<String> itemNames, ArrayList<Serializable_Bitmap> photos, ArrayList<String> itemid, ArrayList<String> itemdate, ArrayList<String> itemprice) {
        ItemNames = itemNames;
        Photos = photos;
        ItemID = itemid;
        ItemDates = itemdate;
        ItemPrice = itemprice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_entry, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        try{
            holder.item_name.setText(ItemNames.get(position));
            holder.image.setImageBitmap((Photos.get(position).getBitmap()));
            holder.id.setText(ItemID.get(position));
            holder.date.setText(ItemDates.get(position));
            holder.price.setText(ItemPrice.get(position));
            holder.parentlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    String item_key = "item_key";
                    String date_key = "date_key";
                    String price_key = "price_key";
                    String photo_key = "photo_key";
                    String id_key = "id_key";
                    String Item_name = holder.item_name.getText().toString();
                    String Date = holder.date.getText().toString();
                    String Price = holder.price.getText().toString();
                    try {
                        BitmapDrawable drawable = (BitmapDrawable) holder.image.getDrawable();
                        Bitmap Photo = drawable.getBitmap();
                        intent.putExtra(item_key, Item_name);
                        intent.putExtra(date_key, Date);
                        intent.putExtra(price_key, Price);
                        intent.putExtra(photo_key, Photo);
                        intent.putExtra(id_key, holder.id.getText().toString());
                        v.getContext().startActivity(intent);
                    }catch (Exception e){
                        intent.putExtra(item_key, Item_name);
                        intent.putExtra(date_key, Date);
                        intent.putExtra(price_key, Price);
                        intent.putExtra(id_key, holder.id.getText().toString());
                        v.getContext().startActivity(intent);
                    }
                }
            });

            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Enter_Screen.All_Data.remove(holder.id.getText().toString());

                    /*
                    for (String key : Enter_Screen.All_Data.keySet()) {
                        Log.d("DeBUG: delete properly", Enter_Screen.All_Data.get(key).Item_name);
                    }
                    */

                    Intent intent = new Intent(v.getContext(), Spendinghistory.class);
                    v.getContext().startActivity(intent);
                }
            });
        }catch (Exception e){
            holder.item_name.setText(ItemNames.get(position));
            holder.id.setText(ItemID.get(position));
            holder.date.setText(ItemDates.get(position));
            holder.price.setText(ItemPrice.get(position));
            holder.date.setText(ItemDates.get(position));
            holder.price.setText(ItemPrice.get(position));
            holder.parentlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    String item_key = "item_key";
                    String date_key = "date_key";
                    String price_key = "price_key";
                    String photo_key = "photo_key";
                    String id_key = "id_key";
                    String Item_name = holder.item_name.getText().toString();
                    String Date = holder.date.getText().toString();
                    String Price = holder.price.getText().toString();
                    try {
                        BitmapDrawable drawable = (BitmapDrawable) holder.image.getDrawable();
                        Bitmap Photo = drawable.getBitmap();
                        intent.putExtra(item_key, Item_name);
                        intent.putExtra(date_key, Date);
                        intent.putExtra(price_key, Price);
                        intent.putExtra(photo_key, Photo);
                        intent.putExtra(id_key, holder.id.getText().toString());
                        v.getContext().startActivity(intent);
                    }catch (Exception e){
                        intent.putExtra(item_key, Item_name);
                        intent.putExtra(date_key, Date);
                        intent.putExtra(price_key, Price);
                        intent.putExtra(id_key, holder.id.getText().toString());
                        v.getContext().startActivity(intent);
                    }
                }
            });

            holder.delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Enter_Screen.All_Data.remove(holder.id.getText().toString());
                    Intent intent = new Intent(v.getContext(), Spendinghistory.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return Enter_Screen.All_Data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ConstraintLayout parentlayout;
        TextView item_name;
        TextView id;
        TextView date;
        TextView price;
        Button delete_button;
        TextView date_fixed;
        TextView price_fixed;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.HistoryPhoto);
            item_name = itemView.findViewById(R.id.ItemName);
            parentlayout = itemView.findViewById(R.id.parentlayout);
            id = itemView.findViewById(R.id.ItemID);
            date = itemView.findViewById(R.id.ItemDate);
            price = itemView.findViewById(R.id.ItemPrice);
            delete_button = itemView.findViewById(R.id.delete_button);
            date_fixed = itemView.findViewById(R.id.Date_Fixed);
            price_fixed = itemView.findViewById(R.id.Price_Fixed);
        }
    }

}

