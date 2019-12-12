package com.example.wine.ui.favorites;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wine.ArticleDetailActivity;
import com.example.wine.Bean.Favorite_item;
import com.example.wine.Bean.NewBean;
import com.example.wine.Bean.WineBean;
import com.example.wine.HistoryAdapter;
import com.example.wine.NewsAdapter;
import com.example.wine.NewsDetailActivity;
import com.example.wine.R;
import com.example.wine.Util.DatabaseHelper;
import com.example.wine.Util.Util;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private ListView listView;
    private ArrayList<Favorite_item> favoriteItems=new ArrayList<Favorite_item>();
    private String mContentText;
    private Favorite_item fav;
    private int flag=0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel =
                ViewModelProviders.of(this).get(FavoritesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        listView=(ListView)root.findViewById(R.id.lv_load_fav);
        initView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                fav= HistoryAdapter.list.get(i);
                if(fav.getType()==0)
                {
                    Intent intent=new Intent(getContext(), ArticleDetailActivity.class);
                    WineBean wineBean= Util.query_wine(fav.getId(),getContext());
                    intent.putExtra("wine",wineBean);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(getContext(), NewsDetailActivity.class);
                    NewBean newBean=Util.query_new(fav.getId(),getContext());
                    intent.putExtra("newBean",newBean);
                    startActivity(intent);
                }
                Log.e("*************",fav.toString());
                //startActivity(intent);
                //Log.e("news",newBean.toString());
            }
        });
        return root;
    }
    private void initView(){
        if (flag==1)
        {

        }
        else {
            flag=1;
            try {

                DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "test.db",null,1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = db.query("Favs", null, null, null, null, null, null, null);
                if (cursor.moveToFirst())
                {
                    do {
                        Favorite_item Fav=new Favorite_item();
                        Fav.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        Fav.setType(cursor.getInt(cursor.getColumnIndex("type"))) ;
                        Fav.setId(cursor.getString(cursor.getColumnIndex("id")));
                        Fav.setTime(cursor.getString(cursor.getColumnIndex("time")));
                        favoriteItems.add(Fav);
                    } while (cursor.moveToNext());

                }

            }catch (Exception e){
                Log.d("main","error...");
            }

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new HistoryAdapter(favoriteItems,getContext()));
            }
        });

    }
}