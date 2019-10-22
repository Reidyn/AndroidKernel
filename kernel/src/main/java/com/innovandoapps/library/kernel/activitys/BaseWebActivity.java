package com.innovandoapps.library.kernel.activitys;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import com.innovandoapps.library.kernel.activitys.listener.OnErrorLoadWebListener;
import com.innovandoapps.library.kernel.activitys.listener.OnProgressLoadWebListener;

public abstract class BaseWebActivity extends BaseActivity {

    protected WebView webView;
    protected ProgressBar progresbar;
    protected String currentUrl = "";
    protected OnErrorLoadWebListener onErrorLoadWebListener;
    protected OnProgressLoadWebListener onProgressLoadWebListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = (WebView)findViewById(getWebViweId());
        if(getProgressId() > 0){
            progresbar = (ProgressBar)findViewById(getProgressId());
        }
        EnableJavaScript();
        webView.loadUrl(getUrl());
        cargarPagina();
    }

    public void setOnErrorLoadWebListener(OnErrorLoadWebListener onErrorLoadWebListener){
        this.onErrorLoadWebListener = onErrorLoadWebListener;
    }

    public void setOnProgressLoadWebListener(OnProgressLoadWebListener onProgressLoadWebListener){
        this.onProgressLoadWebListener = onProgressLoadWebListener;
    }

    @Override
    public void initController() {}

    @Override
    public int getLayoutResources() {
        return 0;
    }

    private void cargarPagina(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if(onErrorLoadWebListener != null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            onErrorLoadWebListener.OnErrorLoadWeb(request.getUrl().getEncodedPath(),error.getDescription().toString());
                        }else{
                            onErrorLoadWebListener.OnErrorLoadWeb(request.getUrl().getEncodedPath(),error.toString());
                        }
                    }else{
                        onErrorLoadWebListener.OnErrorLoadWeb(request.toString(),error.toString());
                    }
                }
                super.onReceivedError(view, request, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                currentUrl = url;
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(progresbar != null){
                    progresbar.setProgress(0);
                    progresbar.setVisibility(View.VISIBLE);
                    progresbar.setProgress(newProgress*1000);
                    progresbar.incrementProgressBy(newProgress);
                    if(newProgress == 100){
                        progresbar.setVisibility(View.GONE);
                    }
                }

                if(onProgressLoadWebListener != null){
                    onProgressLoadWebListener.OnProgressLoadWeb(newProgress);
                }

                super.onProgressChanged(view, newProgress);
            }
        });
    }

    private void EnableJavaScript(){
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    public abstract String getUrl();

    public abstract int getWebViweId();

    public abstract int getProgressId();

    public String getCurrentUrl(){
        return currentUrl;
    }
}
