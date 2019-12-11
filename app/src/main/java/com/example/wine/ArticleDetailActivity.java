package com.example.wine;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.wine.Bean.WineBean;


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
    }
    private void initView(){
        wine=(WineBean) getIntent().getSerializableExtra("wine");
        Log.e("detail",wine.toString());
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
        infor.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
