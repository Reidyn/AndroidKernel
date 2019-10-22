package com.innovandoapps.library.kernel.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import com.innovandoapps.library.kernel.dialogs.listener.OnSelectItemListener;

/**
 * Created by windows 8.1 on 18/02/2018.
 */
@SuppressLint("ValidFragment")
public class DialogSelection extends DialogFragment{

    private String tag;
    private String titulo;
    private String[] options;
    private OnSelectItemListener onSelectItemListener;

    public DialogSelection(String titulo, String[] options) {
        this.titulo = titulo;
        this.options = options;
        this.tag = "";
    }

    public void setOnSelectItemListener(OnSelectItemListener onSelectItemListener){
        this.onSelectItemListener = onSelectItemListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Evitar que dialog cierre al presionar afuera*/
        this.setCancelable(false);
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.BLACK);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titulo);
        ssBuilder.setSpan(foregroundColorSpan,0,titulo.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(ssBuilder);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onSelectItemListener.OnSelectItem(which);
            }
        });
        return builder.create();
    }
}