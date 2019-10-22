package com.innovandoapps.library.kernel.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

/**
 * Created by desarrollo on 23/11/17.
 */

public abstract class BasePreferences {

    protected SharedPreferences preferences;

    public void init(Context context){
        preferences = context.getSharedPreferences(getFileName(),Context.MODE_PRIVATE);
    }

    public abstract  String getFileName();

    public void writeKey(String key,String valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,valor);
        editor.commit();
    }

    public String getValueKey(String key){
        return preferences.getString(key,"");
    }

    public void writeIntKey(String key,int valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,valor);
        editor.commit();
    }


    public int getIntValueKey(String key){
        return preferences.getInt(key,0);
    }


    public void writeBooleanKey(String key,boolean valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,valor);
        editor.commit();
    }


    public boolean getBooleanValueKey(String key){
        return preferences.getBoolean(key,false);
    }

    public void writeLongValueKey(String key,long valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key,valor);
        editor.commit();
    }

    public long getLongValue(String key){
        return preferences.getLong(key,0L);
    }

    public void writeFloatValueKey(String key,float valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key,valor);
        editor.commit();
    }

    public float getFloatValue(String key){
        return preferences.getFloat(key,0);
    }

    public void cleanFile(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public void objetoToJson(Object object,String key){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        writeKey(key, json);
    }

    public Object getObjeto(String key,Class cl ){
        Gson gson = new Gson();
        Object t = gson.fromJson(getValueKey(key),cl);
        return t;
    }
}
