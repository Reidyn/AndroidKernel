package com.innovandoapps.library.kernel.dialogs;
/*************************************************************************************************************
 * Created by marcos on 18/03/16.                                                                       **
 * ***********************************************************************************************************
 *************************************************************************************************************/
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.innovandoapps.library.kernel.R;
import java.io.File;
import me.relex.circleindicator.CircleIndicator;
/**
 * Clase para desplegar un Dialogo con un pageview de imagenes
 */
@SuppressLint("ValidFragment")
public class DialogImagenes extends DialogFragment {

    private String[] imagenes;

    /**
     * Constructor
     * @param imagenes Array de String con los path de las imagenes
     */
    public DialogImagenes(String[] imagenes) {
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
                if (pager.getCurrentItem() < (imagenes.length - 1)) {
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

     private Context context;
     private LinearLayout mContainer;
     private String[] imagens;

     public GaleriaAdapter(String[] imagens,Context context) {
         this.imagens = imagens;
         this.context = context;
     }

     @Override
     public int getCount() {
         return imagens.length;
     }

     @Override
     public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
         return view ==((LinearLayout)object);
     }

     @Override
     public void destroyItem(ViewGroup container, int position, Object object) {
         ((ViewPager)container).removeView((LinearLayout)object);
     }

     @Override
     public Object instantiateItem(ViewGroup container, int position) {
         mContainer=new LinearLayout(context);///Nueva Instancia de contenedor
         mContainer.setOrientation(LinearLayout.VERTICAL);
         mContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
         /*Se carga una Imagen*/

         ImageView iv = new ImageView(context);
         iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
         File file = new File(imagens[position]);
         if(file.exists()){
             Glide.with(context).load(file).into(iv);
         }

         mContainer.addView(iv);

         /*Agregar view al contenedor de paginas*/
         ((ViewPager)container).addView(mContainer, 0);
         return mContainer;
     }
   }
}
