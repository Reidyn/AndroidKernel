package com.innovandoapps.library.kernel.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by windows 8.1 on 19/11/2017.
 */

public abstract class DataBaseMaestro extends SQLiteOpenHelper {

    public abstract String[] getQueryCreate();

    public abstract String[] getQueryUpdates();

    public DataBaseMaestro(Context contexto, String bdnombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, bdnombre, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String arr_query_creates[] = getQueryCreate();
        for(String query_create:arr_query_creates){
            sqLiteDatabase.execSQL(query_create);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String arr_query_creates[] = getQueryUpdates();
        for(String query_create:arr_query_creates){
            db.execSQL(query_create);
        }
    }
}
