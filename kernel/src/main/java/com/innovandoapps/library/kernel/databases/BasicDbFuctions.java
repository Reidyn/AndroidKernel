package com.innovandoapps.library.kernel.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class BasicDbFuctions {

    public SQLiteDatabase openOnlyRead(String path){
        SQLiteDatabase myDataBase = null;
        try{
            myDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteCantOpenDatabaseException ex){
            ex.printStackTrace();
        }
        return myDataBase;
    }


    public SQLiteDatabase openReadWrite(String path){
        SQLiteDatabase myDataBase = null;
        try{
            myDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteCantOpenDatabaseException ex){
            ex.printStackTrace();
        }
        return myDataBase;
    }

    public long insertarResgistro(String tabla,ContentValues registros,SQLiteDatabase db){
        long result = 0;
        db.beginTransaction();
        try{
            result = db.insert(tabla,null,registros);
            db.setTransactionSuccessful();
        }catch(SQLiteException ex){
            result = -1;
        }finally{
            db.endTransaction();
        }
        db.close();
        return result;
    }

    public boolean actualizarRegistro(String tabla,ContentValues registros,String clausuraWhere,String[] arg,SQLiteDatabase db){
        boolean sw = false;
        db.beginTransaction();
        try{
            db.update(tabla, registros, clausuraWhere, arg);
            db.setTransactionSuccessful();
            sw=true;
        }catch(SQLiteException ex){

        }finally{
            db.endTransaction();
        }
        db.close();
        return sw;
    }

    public int updateRegistros(String tabla,ContentValues registros,String clausuraWhere,String[] arg,SQLiteDatabase db){
        int cont = 0;
        db.beginTransaction();
        try{
            cont = db.update(tabla, registros, clausuraWhere, arg);
            db.setTransactionSuccessful();
        }catch(SQLiteException ex){
            cont = -1;
        }finally{
            db.endTransaction();
        }
        db.close();
        return cont;
    }

    public Cursor consultar(String tabla, String[] campos, String clausuraWhere, String[] arg, String Group, String clausuraHaving, String order,SQLiteDatabase db){
        Cursor cursor = null;
        try{
            cursor = db.query(tabla,campos,clausuraWhere,arg,Group,clausuraHaving,order);
        }catch(SQLiteException ex){

        }
        return cursor;
    }

    public Cursor consultaSql(String sql,String[] args,SQLiteDatabase db){
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(sql,args);
        }catch(SQLiteException ex){

        }
        return cursor;
    }

    public int eliminarRegistros(String tabla,String clausuraWhere,String[] args,SQLiteDatabase db){
        int delete = 0;
        db.beginTransaction();
        try{
            delete = db.delete(tabla, clausuraWhere, args);
            db.setTransactionSuccessful();
        }catch(SQLiteException ex){
            delete = -1;
        }finally{
            db.endTransaction();
        }
        db.close();
        return delete;
    }

    /**
     * Ejecuta una setencia SQL
     * @param sql cadena SQL
     * @return    true
     */
    public boolean sqlQuery(String sql,SQLiteDatabase db){
        db.beginTransaction();
        db.execSQL(sql);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return true;
    }
}
