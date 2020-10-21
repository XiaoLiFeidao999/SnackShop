package com.example.snackshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends BaseAdapter {
    private List<GoodsBean> list=new ArrayList<GoodsBean>();
    private Context context;
    public GoodsAdapter(List<GoodsBean> list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.food_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        String imagestring=list.get(position).getAddress();
        byte[] imagebyte= Base64.decode(imagestring,Base64.DEFAULT);
        Bitmap imagebitmap= BitmapFactory.decodeByteArray(imagebyte,0,imagebyte.length);
        viewHolder.good_image.setImageBitmap(imagebitmap);
        viewHolder.good_description.setText(list.get(position).getName());
        viewHolder.good_price.setText("￥"+list.get(position).getPrice()+"元");
        return convertView;
    }
    class ViewHolder{
        ImageView good_image;
        TextView good_description;
        TextView good_price;
        public ViewHolder(View v){
            good_image=(ImageView)v.findViewById(R.id.good_image);
            good_description=(TextView)v.findViewById(R.id.good_description);
            good_price=(TextView)v.findViewById(R.id.good_price);
        }
    }
}
