package com.example.wine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wine.Bean.NewBean;
import com.example.wine.Util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailActivity extends AppCompatActivity {
    private WebView webView;
    private NewBean newBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webView= (WebView) findViewById(R.id.wb_new);
        Intent intent=getIntent();
        newBean=(NewBean) intent.getSerializableExtra("newBean");
        initWebView();
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
            if (newBean.getFlag()==0)
            {
                Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                item.setTitle("取消收藏");
                item.setIcon(getResources().getDrawable(R.drawable.button2));
                newBean.setFlag(1);
                Util.updateFlag_new(newBean.getFlag(),newBean.getTime(),NewsDetailActivity.this);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                Util.insert_Favs(newBean.getTitle(),newBean.getTime(),simpleDateFormat.format(date),1,NewsDetailActivity.this);
            }
            else {
                Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                item.setTitle("收藏");
                item.setIcon(getResources().getDrawable(R.drawable.button1));
                newBean.setFlag(0);
                Util.updateFlag_new(newBean.getFlag(),newBean.getTime(),NewsDetailActivity.this);
                Util.del_Favs(newBean.getTime(),NewsDetailActivity.this);
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
        if(newBean.getFlag()==0)
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
    private void initWebView() {
        webView.loadUrl(newBean.getUrl());
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//返回为true默认webview自带浏览器打开，false调用三方浏览器
            }
        });
    }
}
