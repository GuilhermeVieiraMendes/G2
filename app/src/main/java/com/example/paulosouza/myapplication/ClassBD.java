package com.example.paulosouza.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassBD extends SQLiteOpenHelper {
    public static String db_nome = "teste";
    public static int db_version = 1;

    public ClassBD (Context context){
        super(context, db_nome, null, db_version);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE CLIENTE ( COD_CLIENTE integer primary key autoincrement, NOME Text, CPF text)";
        db.execSQL(sql);
    }

    public void onUpgrade (SQLiteDatabase db, int oldVersion , int newVersion){
        onCreate(db);
    }
}


