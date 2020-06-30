package com.example.testwebview;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class InterWebviewSchema extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_webview_schema);
        initWebview();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebview() {
        WebView webview = findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            /** 拦截 */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Uri url = request.getUrl();
                String scheme = url.getScheme();
                Log.d("ccc", "shouldInterceptRequest: " + url);
                if (!Objects.equals(scheme, "htmm")) return null;
                // htmm 协议拦截
                String path = url.getPath();
                Log.d("ccc", "shouldInterceptRequest: " + path);
                return null;
//                String response = "<html>\n" +
//                        "<title>千度</title>\n" +
//                        "<body>\n" +
//                        "<a href=\"www.taobao.com\">千度</a>,比百度知道的多10倍\n" +
//                        "</body>\n" +
//                        "<html>";
//                return new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream(response.getBytes()));
            }
        });
        webview.loadUrl("http://172.31.199.145:9876/webview_schema/index.html");
//        webview.loadUrl("http://baidu.com");
    }
}