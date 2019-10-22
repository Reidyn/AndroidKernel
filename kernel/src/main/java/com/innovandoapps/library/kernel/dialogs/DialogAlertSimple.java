package com.innovandoapps.library.kernel.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import com.innovandoapps.library.kernel.R;
import com.innovandoapps.library.kernel.dialogs.listener.OnPositiveClickListener;

/**
 * Created by windows 8.1 on 19/11/2017.
 */
@SuppressLint("ValidFragment")
public class DialogAlertSimple extends DialogFragment {

    private String titulo;
    private String mensaje;
    private String lblaceptar;
    private String tag;
    private boolean shown = false;

    private OnPositiveClickListener onPositiveClickListener;

    /**
     * Constructor
     * @param titulo
     * @param mensaje
     */
    public DialogAlertSimple(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public DialogAlertSimple(String titulo, String mensaje,String lblaceptar) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.lblaceptar = lblaceptar;
    }

    public void setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener){
        this.onPositiveClickListener = onPositiveClickListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setCancelable(false);
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
        }

        if(lblaceptar == null){
            lblaceptar = getString(R.string.lbl_aceptar);
        }
    }

    /**
     * Metodo onCreate del dialog
     * @param savedInstanceState
     * @return dialogo a desplegar
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton(getString(R.string.lbl_aceptar),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPositiveClickListener.OnPositiveClick(dialog,"");
            }
        });
        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        this.tag = tag;
        super.show(manager, tag);
        shown = true;
    }
}
