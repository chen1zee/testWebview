package com.example.testwebview;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class InterWebviewSchema extends AppCompatActivity {
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_webview_schema);
        initDialog();
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
                if (Objects.equals(path, "/aaa")) {

                    // 调用 原生逻辑
                    try {
                        Toast.makeText(InterWebviewSchema.this, "调用原生", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Looper.prepare();
                        Toast.makeText(InterWebviewSchema.this, "调用原生", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
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

    private void initDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setTitle("webview中调起原生")
                .setMessage("asdjkoaspdkop")
                .create();
//        alertDialog.show();
    }
}