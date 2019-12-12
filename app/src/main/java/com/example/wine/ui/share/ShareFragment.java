package com.example.wine.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wine.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        Button b1=root.findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allShare();
            }
        });
        return root;
    }
    /**
     * Android原生分享功能
     * 默认选取手机所有可以分享的APP
     */
    public void allShare(){
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "share");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, "share with you:"+"android");//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "share");
        startActivity(share_intent);
    }
    /**
     * Android原生分享功能
     * @param appName:要分享的应用程序名称
     */
    private void share(String appName) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        share_intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件:" + appName);
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }
}