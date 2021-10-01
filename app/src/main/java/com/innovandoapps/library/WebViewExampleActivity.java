package com.innovandoapps.library;

import com.innovandoapps.library.kernel.activitys.BaseWebActivity;

public class WebViewExampleActivity extends BaseWebActivity {


    @Override
    public String getUrl() {
        return "http://ssmtest.stp.gov.py/#/admin/login";
    }

    @Override
    public int getWebViweId() {
        return R.id.web;
    }

    @Override
    public int getProgressId() {
        return R.id.progress;
    }

    @Override
    public int getLayoutResources() {
        return R.layout.activity_web_example;
    }
}
