package com.example.wine.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //带全部参数的构造函数，此构造函数必不可少
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE wines(id varchar(20) PRIMARY KEY ,name varchar(20),type varchar(20),price varchar(10),infor text,imgurl text)";
        db.execSQL(sql);
        String sql1= "CREATE TABLE News(title varchar(50),time varchar(20)PRIMARY KEY,content text,imgurl text,url text)";
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
