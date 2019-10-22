package com.innovandoapps.library.kernel.fragments;
/*************************************************************************************************************	 **
 * @autor : Marcos Ramirez
 * Created by Marcos on 22/02/17.                                                                       **
 * ***********************************************************************************************************
 *************************************************************************************************************/
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.Toast;;
/**
 * Base de los fragment del proyecto
 */
public abstract class BaseFragment extends Fragment {
    ///Variables
    protected ProgressDialog barProgressDialog;
    protected Handler handler = new Handler();
    protected boolean activo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activo = true;
    }

    /**
     * Metodo que inicializa las variables
     */
    public void inicializar(){}

    /***
     * Metodo que muesta un dialog progres
     * @param titulo  String titulo del dialog
     * @param mensaje String texto del mensaje
     */
    protected void showProgressDialog(String titulo,String mensaje){
        if(barProgressDialog == null){
            barProgressDialog = new ProgressDialog(getContext());
        }
        barProgressDialog.setTitle(titulo);
        barProgressDialog.setMessage(mensaje);
        barProgressDialog.setIndeterminate(true);
        barProgressDialog.setCancelable(false);
        barProgressDialog.setCanceledOnTouchOutside(false);
        barProgressDialog.show();
    }

    /**
     * Metodo que remueve el dialogProgress si este se encuentra visible
     */
    protected void offProgressDialog(){
        if(barProgressDialog != null && barProgressDialog.isShowing()){
            barProgressDialog.dismiss();
        }
    }

    /**
     * Metodo que despliega un mensaje Toast
     * @param context Contexto del Avtivity
     * @param mensaje String texto del mensaje
     */
    protected void notificacionToast(Context context, String mensaje){
        Toast toast = Toast.makeText(context,mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public void onDestroyView() {
        activo = false;
        super.onDestroyView();
    }
}
