package com.innovandoapps.library.kernel.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by windows 8.1 on 19/11/2017.
 */

public abstract class DbBaseManager{

    private SQLiteDatabase db;

    public abstract SQLiteOpenHelper getSqliteOpenHelper();

    public abstract String getDataBaseName();

    public abstract int getDataBaseVersion();

    /**
     * Fuerza la creacin de la base de datos
     */
    public void open(){
        db = getSqliteOpenHelper().getWritableDatabase();
        db.beginTransaction();
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * Inserta fila en la base de datos\n
     * @param tabla     nombre de la tabla donde insertar el registro
     * @param registros  Conjunto clave valor del registro
     * @return  long con el id del registro insertado -1 si falla
     */
    public long insertarResgistro(String tabla,ContentValues registros){
        long result = 0;
        db = getSqliteOpenHelper().getWritableDatabase();
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

    /**
     * Actualiza los registro de la tabla\n
     * @param tabla         nombre de la tabla
     * @param registros	 	Conjuntos de campos a actualizar
     * @param clausuraWhere	sentencia where
     * @param arg		    array de argumentos para la sentencia where
     * @return				true si se realiza la actualizacion, false si se produce un error
     */
    public boolean actualizarRegistro(String tabla,ContentValues registros,String clausuraWhere,String[] arg){
        boolean sw=false;
        db = getSqliteOpenHelper().getWritableDatabase();
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

    /**
     * Actualizacion de la base de datos
     * @param tabla			nombre de la tabla
     * @param registros		campos a actualizar
     * @param clausuraWhere sentencia where
     * @param arg			array de argumentos de la sentencia where
     * @return			    cantidad de registros actializados, -1 si se produce un error
     */
    public int updateRegistros(String tabla,ContentValues registros,String clausuraWhere,String[] arg){
        int cont = 0;
        db = getSqliteOpenHelper().getWritableDatabase();
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

    /**
     * Realiza una consulta a la BD
     * @param tabla  		nombre de la tabla
     * @param campos		Array de campos a consultar
     * @param clausuraWhere Sentencia where de filtro
     * @param arg			array de argumentos para la setencia where
     * @param Group			sentencia de group by
     * @param clausuraHaving sentencia having
     * @param order			senetencia ORDER BY
     * @return 			    Cursor con el resultados de la consulta
     */
    public Cursor consultar(String tabla, String[] campos, String clausuraWhere, String[] arg, String Group, String clausuraHaving, String order){
        db = getSqliteOpenHelper().getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.query(tabla,campos,clausuraWhere,arg,Group,clausuraHaving,order);
        }catch(SQLiteException ex){

        }
        return cursor;
    }

    /**
     * Consulta a la base de datos apartir de un sentencia
     * @param sql  sentencia SQL
     * @param args array de argumentos
     * @return 	   cursor con el resultado de la consulta
     */
    public Cursor consultaSql(String sql,String[] args){
        db = getSqliteOpenHelper().getReadableDatabase();
        Cursor cursor=null;
        try{
            cursor=db.rawQuery(sql,args);
        }catch(SQLiteException ex){

        }
        return cursor;
    }

    /**
     * Elimina registros de la DB
     * @param tabla 		nombre de la tabla
     * @param clausuraWhere	Sentencia where
     * @param args			array de argumentos
     * @return				numero de registros eliminados -1 si se produce un error
     */
    public int eliminarRegistros(String tabla,String clausuraWhere,String[] args ){
        int delete = 0;
        db = getSqliteOpenHelper().getWritableDatabase();
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
    public boolean sqlQuery(String sql){
        db = getSqliteOpenHelper().getWritableDatabase();
        db.beginTransaction();
        db.execSQL(sql);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return true;
    }

    /**
     * Cierra la BD
     */
    public void close(){
        db.close();
    }
}
