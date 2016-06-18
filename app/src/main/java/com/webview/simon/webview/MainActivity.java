package com.webview.simon.webview;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etAddress;

    private View progress;

    private WebView webView;

    int screeWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        etAddress = (EditText) findViewById(R.id.et_address);
        progress = findViewById(R.id.progressbar);
        webView = (WebView) findViewById(R.id.wv);
        screeWidth = getResources().getDisplayMetrics().widthPixels;
        webView.getSettings().setJavaScriptEnabled(true);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progress.getLayoutParams();
        params.width = 0;
        progress.setLayoutParams(params);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //加载开始
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //加载完成
                etAddress.setText(url);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progress.getLayoutParams();
                params.width = 0;
                progress.setLayoutParams(params);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //访问进度改变

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progress.getLayoutParams();
                params.width = (int) (screeWidth * newProgress * 0.1f / 100);
                progress.setLayoutParams(params);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //接收标题
                TextView t = (TextView) findViewById(R.id.tv_text);
                t.setText(title);
            }
        });
        etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //全选
                etAddress.setText(etAddress.getText().toString());//添加这句后实现效果
                Spannable content = etAddress.getText();
                Selection.selectAll(content);
            }
        });
    }

    public void doClick(View v) {
        //// TODO: webView 加载网页
        String path = etAddress.getText().toString();
        if (path.length()!=0) {
            webView.loadUrl(path);
        }
        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
