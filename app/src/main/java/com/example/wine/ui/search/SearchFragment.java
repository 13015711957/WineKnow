package com.example.wine.ui.search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wine.ArticleDetailActivity;
import com.example.wine.Bean.WineBean;
import com.example.wine.R;
import com.example.wine.Util.DatabaseHelper;
import com.example.wine.Util.Util;
import com.example.wine.itemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchFragment extends Fragment {

    private SearchView mSearchView;
    private ListView listView;
    private ArrayList<WineBean> wineBeans=new ArrayList<WineBean>();
    private String mContentText;
    private itemAdapter iad;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);
        mSearchView = (SearchView) root.findViewById(R.id.searchView);
        listView=(ListView)root.findViewById(R.id.lv_load_search);
        mSearchView.setSubmitButtonEnabled(true);


        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                int size = wineBeans.size();
                if(size>0){
                    wineBeans.clear();
                    iad.removeAllItem(size);
                }
                initView(query);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(getContext(), ArticleDetailActivity.class);
                        WineBean wine=itemAdapter.list.get(i);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
                        Date date = new Date(System.currentTimeMillis());
                        Util.insert_History(wine.getName(),wine.getId(),simpleDateFormat.format(date),0,getContext());

                        try {
                            DatabaseHelper dbHelper = new DatabaseHelper(getContext(), "test.db",null,1);
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            Cursor cursor = db.query("wines", null, "id=?", new String[]{wine.getId()}, null, null, null, null);
                            while (cursor.moveToNext())
                            {
                                int x=cursor.getInt(cursor.getColumnIndex("flag"));
                                wine.setFlag(x);
                            }
                        }catch (Exception e){
                            Log.e("<<<<<<<","error");
                        }
                        intent.putExtra("wine",wine);
                        startActivity(intent);
                        //Log.e("wine",wine.toString());
                    }
                });
                return true;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return root;
    }
    private void initView( String wd){

            try {
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "test.db",null,1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query("wines", null, "name like '%"+wd+"%' or infor like '%"+wd+"%'", null, null, null, null, null);
                Log.e("----------",String.valueOf(cursor.getCount()));
                if (cursor.moveToFirst())
                {
                    do {
                        WineBean wineBean=new WineBean();
                        wineBean.setId(cursor.getString(cursor.getColumnIndex("id")));
                        wineBean.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                        wineBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                        wineBean.setType(cursor.getString(cursor.getColumnIndex("type"))) ;
                        wineBean.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                        wineBean.setInfor(cursor.getString(cursor.getColumnIndex("infor")));
                        wineBean.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
                        wineBeans.add(wineBean);
                    } while (cursor.moveToNext());

                }

            }catch (Exception e){
                Log.d("main>>>","error...");
            }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                iad=new itemAdapter(wineBeans,getContext());
                listView.setAdapter(iad);
            }
        });

    }
}