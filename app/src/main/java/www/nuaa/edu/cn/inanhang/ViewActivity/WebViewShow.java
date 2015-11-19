package www.nuaa.edu.cn.inanhang.ViewActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Timer;
import java.util.TimerTask;

import www.nuaa.edu.cn.inanhang.Base.BaseToolbarActivity;
import www.nuaa.edu.cn.inanhang.R;

import static www.nuaa.edu.cn.inanhang.Util.UIHelper.showProgressDialog;


/**
 * Created by yxy on 15/9/20.
 */
public class WebViewShow extends BaseToolbarActivity {
    MaterialDialog dialog = null;
    WebView webView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aawebviewshow);
        webView = (WebView) findViewById(R.id.awebViewshow);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        setToolbarTitle(title);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.loadUrl(url);
        dialog = showProgressDialog(this, "正在加载");
        webView.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                view.loadUrl(url);
//                return false;
//            }


            boolean timeout = true;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                timer = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        /*
                         * 超时后,首先判断页面加载进度,超时并且进度小于100,就执行超时后的动作
                         */
                        if (timeout) {
                            Log.d("testTimeout", "timeout...........");

                            timer.cancel();
                            timer.purge();
                        }
                    }
                };
                timer.schedule(tt, 5000);
            }

            public void onPageFinished(WebView view, String url) {
                Log.d("load", "loadfinish");
                dialog.dismiss();
                timer.cancel();
                timer.purge();
                timeout = false;
            }

        });
        webView.loadUrl(url);
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}