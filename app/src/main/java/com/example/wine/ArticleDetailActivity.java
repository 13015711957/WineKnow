package com.example.wine;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wine.Bean.WineBean;
import com.example.wine.Util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ArticleDetailActivity extends AppCompatActivity {
    private WineBean wine;
    private ImageView imageView;
    private TextView name;
    private TextView price;
    private TextView type;
    private TextView infor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initView();
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        if(item.getItemId()==R.id.fav)
        {
            if (wine.getFlag()==0)
            {
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                item.setTitle("取消收藏");
                item.setIcon(getResources().getDrawable(R.drawable.button2));
                wine.setFlag(1);
                Util.updateFlag_wine(wine.getFlag(),wine.getId(),ArticleDetailActivity.this);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                Util.insert_Favs(wine.getName(),wine.getId(),simpleDateFormat.format(date),0,ArticleDetailActivity.this);
            }
            else {
                Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                item.setTitle("收藏");
                item.setIcon(getResources().getDrawable(R.drawable.button1));
                wine.setFlag(0);
                //Log.e(">>>>>>>>>>>>>",wine.getId());
                Util.updateFlag_wine(wine.getFlag(),wine.getId(),ArticleDetailActivity.this);
                Util.del_Favs(wine.getId(),ArticleDetailActivity.this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //右侧菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        MenuItem menuItem=menu.findItem(R.id.fav);
        if(wine.getFlag()==0)
        {
            menuItem.setIcon(getResources().getDrawable(R.drawable.button1));
            menuItem.setTitle("收藏");
        }
        else
        {
            menuItem.setIcon(getResources().getDrawable(R.drawable.button2));
            menuItem.setTitle("取消收藏");
        }
        return true;
    }

    private void initView(){
        wine=(WineBean) getIntent().getSerializableExtra("wine");
        //Log.e("detail",wine.toString());
        imageView=(ImageView)findViewById(R.id.detail_image);
        name=(TextView)findViewById(R.id.detail_name);
        price=(TextView)findViewById(R.id.detail_price);
        type=(TextView)findViewById(R.id.detail_type);
        infor=(TextView)findViewById(R.id.detail_infor);
        Glide.with(this).load(wine.getImgurl()).into(imageView);
        name.setText(wine.getName());
        price.setText(wine.getPrice());
        type.setText(wine.getType());
        infor.setText(wine.getInfor());
    }
}
