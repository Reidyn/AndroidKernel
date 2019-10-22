package com.innovandoapps.library.kernel.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import com.innovandoapps.library.kernel.R;
import com.innovandoapps.library.kernel.dialogs.listener.OnClickDialogListener;

/**
 * Created by windows 8.1 on 19/11/2017.
 */
@SuppressLint("ValidFragment")
public class DialogAcpView extends DialogFragment{

    private String tag;
    private String titulo;
    private String mensaje;
    private String titleAceptar;
    private String titleCancelar;
    private boolean shown = false;

    private OnClickDialogListener onClickDialogListener;

    public DialogAcpView(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tag = "";
    }

    /**
     * Construtor
     *
     * @param titulo        Titulo del Cuadro de Dialog
     * @param mensaje       Cadena del Mensaje
     * @param titleAceptar  Cadena del positive button
     * @param titleCancelar cadena del negative button
     */
    public DialogAcpView(String titulo, String mensaje, String titleAceptar, String titleCancelar) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.titleAceptar = titleAceptar;
        this.titleCancelar = titleCancelar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
        }

        if (titleAceptar == null) {
            this.titleAceptar = getResources().getString(R.string.lbl_aceptar);
        }

        if (titleCancelar == null) {
            this.titleCancelar = getResources().getString(R.string.lbl_cancelar);
        }
    }

    /**
     * Listener del Aceptar y Cancelar
     */
    public void setOnClickDialogListener(OnClickDialogListener onClickDialogListener) {
        this.onClickDialogListener = onClickDialogListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton(titleAceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickDialogListener.OnPositiveClick(dialog, tag);
            }
        });
        builder.setNegativeButton(titleCancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickDialogListener.OnNegativeClick(dialog, tag);
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


    /**
     * Cierra el Dialogo
     * @param dialog Interface del dialogo
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }
}
