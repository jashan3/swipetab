package com.example.home.swipetab.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.SafeBrowsingResponse;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {
    final static String TAG = "MyWebViewClient";
    private Context mContext;

    public MyWebViewClient (Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Toast.makeText(mContext,"onPageStarted",Toast.LENGTH_SHORT).show();
        Log.i(TAG,"## onPageStarted");
        super.onPageStarted(view, url, favicon);
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.i(TAG,"## onPageStarted");
        return super.shouldOverrideUrlLoading(view, request);
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

}
