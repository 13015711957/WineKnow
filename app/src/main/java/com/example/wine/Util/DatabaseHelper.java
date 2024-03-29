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
        String sql = "CREATE TABLE wines(id varchar(20) PRIMARY KEY ,name varchar(20),type varchar(20),price varchar(10),infor text,imgurl text,flag Integer)";
        db.execSQL(sql);
        String sql1= "CREATE TABLE News(title varchar(50),type varchar(20),time varchar(20)PRIMARY KEY,content text,imgurl text,url text,flag Integer)";
        db.execSQL(sql1);
        String sql2="CREATE TABLE Favs(title varchar(50),id varchar(20)PRIMARY KEY,time varchar(20),type Integer)";
        db.execSQL(sql2);
        String sql3="CREATE TABLE History(title varchar(50),id varchar(20)PRIMARY KEY, time varchar(20),type Integer)";
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
