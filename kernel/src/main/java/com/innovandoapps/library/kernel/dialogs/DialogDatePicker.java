package com.innovandoapps.library.kernel.dialogs;
/*************************************************************************************************************	 **
 * @autor : Marcos Ramirez
 * Created by desarrollo on 18/04/16.                                                                       **
 * ***********************************************************************************************************
 *************************************************************************************************************/
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import com.innovandoapps.library.kernel.dialogs.listener.OnDataSelectListener;
import java.util.Calendar;

/**
 * @author Marcos Ramirez
 * Dialog Tipo Date Picker
 */
@SuppressLint("ValidFragment")
public class DialogDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private OnDataSelectListener listener;

    /**
     * Metodo Create del dialogo
     * @param savedInstanceState
     * @return Dialog a desplegar
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Setear Listener del evento de seleccion de fecha
     * @param onDataSelectListener Interface que iteractua con el botton definir fecha
     */
    public void setOnDataSelectListener(OnDataSelectListener onDataSelectListener){
        this.listener = onDataSelectListener;
    }

    /**
     * Setear Fecha
     * @param view       View Picker de fecha
     * @param year       int año
     * @param monthOfYear   int mes del año
     * @param dayOfMonth    int dia del mes
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String day = "";
        if(dayOfMonth < 10){
            day = "0" + dayOfMonth;
        }else{
            day = dayOfMonth + "";
        }

        String fecha = year + "-" + (monthOfYear+1) + "-" + day;

        if((monthOfYear+1)<10){
            fecha = year + "-0" + (monthOfYear+1) + "-" + day;
        }
        listener.OnDataSelect(fecha);
    }
}
