package com.example.ordersystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Food> foodsList;

    public FoodListAdapter(Context context, int layout, ArrayList<Food> foodsList) {
        this.context = context;
        this.layout = layout;
        this.foodsList = foodsList;
    }

    @Override
    public int getCount() {
        return foodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private  class  ViewHolder{
        ImageView imageView;
        TextView txtID,txtName, txtPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =  inflater.inflate(layout, null);

            holder.txtID = (TextView) row.findViewById(R.id.txtID);
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtPrice = (TextView) row.findViewById(R.id.txtPrice);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Food food = foodsList.get(position);

        holder.txtID.setText(food.getId());
        holder.txtName.setText(food.getName());
        holder.txtPrice.setText(food.getPrice());

        byte[] foodimage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodimage, 0,foodimage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
