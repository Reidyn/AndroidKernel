package com.innovandoapps.library.kernel.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

public abstract class BaseToolbarActivity extends BaseActivity {

    protected Toolbar appbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appbar = (Toolbar)findViewById(getToolbarId());
        setSupportActionBar(appbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getTitleToolbar());

    }

    protected abstract int getToolbarId();

    protected abstract String getTitleToolbar();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
