package com.example.wine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wine.Bean.Favorite_item;
import com.example.wine.Bean.WineBean;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    public static ArrayList<Favorite_item> list;
    private Context context;
    public HistoryAdapter(ArrayList<Favorite_item> list, Context context)
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.history_item,null,false);
            viewHolder.title= (TextView) convertView.findViewById(R.id.history_title);
            viewHolder.time= (TextView) convertView.findViewById(R.id.history_time);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.time.setText(list.get(position).getTime());
        return convertView;
    }
    class ViewHolder
    {

        private TextView title;
        private TextView time;
    }
}
