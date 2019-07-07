package com.example.home.swipetab.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.home.swipetab.webview.MyWebViewClient;
import com.example.home.swipetab.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

@SuppressLint("ValidFragment")
public class Tab1 extends Fragment {
    private Context mContext;
    private WebView superSafeWebView;

    @SuppressLint("ValidFragment")
    public Tab1 (Context mContext){
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String daumUrl = "http://218.50.84.160:8080/sangjun/";
        View inf_view = inflater.inflate(R.layout.layout_1,container,false);

        superSafeWebView =inf_view.findViewById(R.id.mWebview);
        superSafeWebView.setWebViewClient(new MyWebViewClient(mContext));
        superSafeWebView.getSettings().setJavaScriptEnabled(true);
        superSafeWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        superSafeWebView.loadUrl(daumUrl);
        URL url = null;
        try {
            url = new URL("http://218.50.84.160:8080/sangjun/login");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection httop = (HttpURLConnection) url.openConnection();
            httop.setRequestMethod("GET");
            InputStream inputStream = httop.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            Log.i(" ddddddddddddddddddddd",line);

            httop.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inf_view;
    }
}
