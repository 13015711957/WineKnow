package com.example.wine.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wine.Bean.Favorite_item;
import com.example.wine.Bean.NewBean;
import com.example.wine.Bean.WineBean;

public class Util {

    public static void updateFlag_wine(int flag,String id, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("flag", flag);
        db.update("wines",values,"id=?",new String[]{id});
    }
    public static void updateFlag_new(int flag,String time, Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("flag", flag);
        db.update("News",values,"time=?",new String[]{time});
    }
    public static void insert_Favs(String title,String id,String time,int type,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put("time",time);
                values.put("title",title);
                values.put("id",id);
                values.put("type",type);
                db.insert("Favs",null,values);
            }catch (Exception e){

            }

    }
    public static void del_Favs(String id,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Favs","id=?",new String[]{id});
    }
    public static void insert_History(String title,String id,String time,int type,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("time",time);
            values.put("title",title);
            values.put("id",id);
            values.put("type",type);
            db.insert("History",null,values);
        }catch (Exception e){

        }


    }
    public static void del_History(String id,Context context){
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete("History","id=?",new String[]{id});
    }
    public static WineBean query_wine(String id,Context context)
    {
        WineBean wineBean=new WineBean();
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("wines", null, "id=?", new String[]{id}, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {

                wineBean.setId(cursor.getString(cursor.getColumnIndex("id")));
                wineBean.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                wineBean.setName(cursor.getString(cursor.getColumnIndex("name")));
                wineBean.setType(cursor.getString(cursor.getColumnIndex("type"))) ;
                wineBean.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                wineBean.setInfor(cursor.getString(cursor.getColumnIndex("infor")));
                wineBean.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
            } while (cursor.moveToNext());

        }
        return wineBean;
    }
    public static NewBean query_new(String time, Context context)
    {
        NewBean newBean=new NewBean();
        DatabaseHelper dbHelper = new DatabaseHelper(context, "test.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("News", null, "time=?", new String[]{time}, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {

                newBean.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
                newBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                newBean.setType(cursor.getString(cursor.getColumnIndex("type")));
                newBean.setTime(cursor.getString(cursor.getColumnIndex("time"))); ;
                newBean.setContent(cursor.getString(cursor.getColumnIndex("content")));
                newBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                newBean.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
            } while (cursor.moveToNext());

        }
        return newBean;
    }
}
