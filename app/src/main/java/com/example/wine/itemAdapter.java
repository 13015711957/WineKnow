package com.example.wine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wine.Bean.WineBean;

import java.util.ArrayList;

public class itemAdapter extends BaseAdapter {
    public static ArrayList<WineBean> list;
    private Context context;
    public itemAdapter(ArrayList<WineBean> list, Context context)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeAllItem(int size){
        list.clear();
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.article_item,null,false);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_article_list_image);
            viewHolder.name= (TextView) convertView.findViewById(R.id.tv_article_list_name);
            viewHolder.price= (TextView) convertView.findViewById(R.id.tv_article_list_price);
            viewHolder.type= (TextView) convertView.findViewById(R.id.tv_article_list_type);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        Glide.with(context).load(list.get(position).getImgurl()).into(viewHolder.imageView);//Glide 加载图片
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.type.setText(list.get(position).getType());
        viewHolder.price.setText(list.get(position).getPrice());
        return convertView;
    }
    class ViewHolder
    {
        private ImageView imageView;
        private TextView name;
        private TextView price;
        private TextView type;
    }
}
