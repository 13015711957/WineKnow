package com.example.wine;


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

import androidx.fragment.app.Fragment;

import com.example.wine.Bean.WineBean;
import com.example.wine.Util.DatabaseHelper;
import com.example.wine.Util.Util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KnowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KnowFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";
    private ListView listView;
    private ArrayList<WineBean> wineBeans=new ArrayList<WineBean>();
    private String mContentText;
    private int flag=0;

    public KnowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static KnowFragment newInstance(String param1) {
        KnowFragment fragment = new KnowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_know, container, false);
        listView=(ListView)rootView.findViewById(R.id.lv_loaddata);
        initView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),ArticleDetailActivity.class);
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
        return rootView;
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
                Cursor cursor = db.query("wines", null, null, null, null, null, null, null);
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

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new itemAdapter(wineBeans,getActivity()));
            }
        });

    }

}
