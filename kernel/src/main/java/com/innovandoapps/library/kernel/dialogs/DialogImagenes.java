package com.innovandoapps.library.kernel.dialogs;
/*************************************************************************************************************
 * Created by marcos on 18/03/16.                                                                       **
 * ***********************************************************************************************************
 *************************************************************************************************************/
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.innovandoapps.library.kernel.R;
import java.util.ArrayList;
import me.relex.circleindicator.CircleIndicator;
/**
 * Clase para desplegar un Dialogo con un pageview de imagenes
 */
@SuppressLint("ValidFragment")
public class DialogImagenes extends DialogFragment {

    private ArrayList<String>imagenes;

    /**
     * Constructor
     * @param imagenes Array de String con los path de las imagenes
     */
    public DialogImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    /**
     * Creacion del popup de Dialogo con imagenes deplegadas en forma paginada
     * @param savedInstanceState
     * @return Dialog a desplegar
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*inicializar contenedor*/
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_view_image, null);

        /*incializar controles*/
        final ViewPager pager = (ViewPager)view.findViewById(R.id.viewpager1);
        final CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        final Button btnGalleryAnt = (Button)view.findViewById(R.id.btnGalleryAnt);
        final Button btnGallerySgt = (Button)view.findViewById(R.id.btnGallerySgt);
        Log.i("Imagenes", imagenes.toString());

        /*Instanciar adapter de galeria*/
        final GaleriaAdapter galeria = new GaleriaAdapter(imagenes,getActivity().getApplicationContext());
        pager.setAdapter(galeria);
        indicator.setViewPager(pager);
        galeria.registerDataSetObserver(indicator.getDataSetObserver());

        /*Eventos de los controles*/
        btnGalleryAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() > 0) {
                    pager.setCurrentItem(pager.getCurrentItem()-1);
                }
            }
        });
        btnGallerySgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() < (imagenes.size() - 1)) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                }
            }
        });

        /*Crear dialog*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.dialog_title_img));
        builder.setView(view);
        return builder.create();
    }
 /*********************************************************************************************/
    private class GaleriaAdapter extends PagerAdapter {

     @Override
     public int getCount() {
         return 0;
     }

     @Override
     public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
         return false;
     }
 }
}
