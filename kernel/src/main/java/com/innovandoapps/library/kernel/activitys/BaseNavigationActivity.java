package com.innovandoapps.library.kernel.activitys;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.innovandoapps.library.kernel.R;
import com.innovandoapps.library.kernel.dialogs.DialogAcpView;
import com.innovandoapps.library.kernel.dialogs.listener.OnClickDialogListener;

public abstract class BaseNavigationActivity extends BaseActivity {

    protected Toolbar appbar;
    protected TextView txt_title;
    protected FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        appbar = (Toolbar)findViewById(getToolbarId());
        setSupportActionBar(appbar);
        getSupportActionBar().setHomeAsUpIndicator(getIcMenuId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getTitleToolbar());

        txt_title = (TextView)findViewById(R.id.txt_title);
        txt_title.setText(getTitleToolbar());
    }

    protected abstract int getToolbarId();

    protected abstract String getTitleToolbar();

    protected abstract int getIcMenuId();

    protected void closeSession(){
        DialogAcpView dialogAcpView = new DialogAcpView(getString(R.string.dialog_title_cerrar_session),
                getString(R.string.dialog_msg_cerrar_session));
        dialogAcpView.setOnClickDialogListener(new OnClickDialogListener() {
            @Override
            public void OnNegativeClick(DialogInterface dialog, String tag) {
                dialog.cancel();
            }

            @Override
            public void OnPositiveClick(DialogInterface dialog, String tag) {
                dialog.cancel();
                deleteDataSession();
            }
        });
        dialogAcpView.show(getSupportFragmentManager(),"");
    }

    protected abstract void deleteDataSession();

    protected void setTitleActionBar(String title){
        getSupportActionBar().setTitle(title);
        txt_title.setText(title);
    }
}
