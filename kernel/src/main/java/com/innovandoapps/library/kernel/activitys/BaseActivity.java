package com.innovandoapps.library.kernel.activitys;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import dmax.dialog.SpotsDialog;

/**
 * @Autor Marcos Ramirez
 * Clase abstracta para generalizar los activitys
 */
public abstract class BaseActivity extends AppCompatActivity {

   private AlertDialog dialogProgresIndeterminate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResources());
        initController();
    }

    /**
     * Metodo que muestra un Cuadro de dialogo de progreso indeterminado
     * @param mensaje String mensaje del cuadro
     */
    public void showProgressDialog(String mensaje){
        dialogProgresIndeterminate = new SpotsDialog(BaseActivity.this,mensaje);
        dialogProgresIndeterminate.show();
    }

    /**
     * Metodo que hace desaparecer el cuadro de dialogo de progreso
     */
    public void offProgressDialog(){
        if(dialogProgresIndeterminate != null && dialogProgresIndeterminate.isShowing()){
            dialogProgresIndeterminate.dismiss();
        }
    }

    /**
     * Metodo abstracto que se ejecuta en el OnCreate destinada a la inicializacion de controles
     */
    public abstract void initController();

    /**
     * Metodo abstracto destinado determinar el layout del activity
     * @return Integer Id del layout del activity
     */
    public abstract int getLayoutResources();

    /**
     * Muestra un notificacion Toast
     * @param mensaje String a mostrar en el toast
     */
    public void notificacionToast(String mensaje){
        Toast toast = Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void notificacionSnackBar(String mensaje,View view){
        Snackbar snackbar = Snackbar.make(view,mensaje,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
