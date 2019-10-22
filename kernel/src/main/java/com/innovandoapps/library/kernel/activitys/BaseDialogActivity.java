package com.innovandoapps.library.kernel.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

public abstract class BaseDialogActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initController();
    }
}
