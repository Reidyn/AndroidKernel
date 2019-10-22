package com.innovandoapps.library.kernel.preferences;

import com.google.gson.Gson;

import java.lang.reflect.Field;

public abstract class ObjetToPreferences<T> extends BasePreferences{

    public void loadObjet(T object){
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if(value instanceof String){
                    writeKey(field.getName(), (String) value);
                } else if (value instanceof Integer){
                    writeIntKey(field.getName(), (Integer) value);
                }else if (value instanceof Long){
                    writeLongValueKey(field.getName(), (Long) value);
                }else if(value instanceof Float){
                    writeFloatValueKey(field.getName(), (Float) value);
                }else if (value instanceof Boolean){
                    writeBooleanKey(field.getName(), (Boolean) value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

    public void objetoToJson(T object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        writeKey("json", json);
    }

    public T getObjeto(){
        Gson gson = new Gson();
        T t = gson.fromJson(getValueKey("json"),getEntityClass());
        return t;
    }

    protected abstract Class<T> getEntityClass();

}