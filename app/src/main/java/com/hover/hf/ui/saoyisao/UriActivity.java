package com.hover.hf.ui.saoyisao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hover.hf.R;
import com.hover.hf.ui.base.BaseActivity;
import com.mylhyl.crlayout.SwipeRefreshWebView;
import com.mylhyl.zxing.scanner.common.Intents;
import com.mylhyl.zxing.scanner.result.URIResult;

import butterknife.Bind;


/**
 * URI显示
 */
public class UriActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.webView)
    SwipeRefreshWebView swipeRefreshWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_uri;
    }

    @Override
    public void initView() {
        if (toolbar != null) {
            toolbar.setTitle("二维码扫描");
            toolbar.setSubtitle("网页连接");
        }

    }

    @Override
    public void initData() {
        String uri = ((URIResult) getIntent().getSerializableExtra(Intents.Scan.RESULT)).getUri();
        swipeRefreshWebView.getScrollView().loadUrl(uri);
        swipeRefreshWebView.getScrollView().setWebViewClient(new SampleWebViewClient());
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            swipeRefreshWebView.autoRefresh();
            return true;
        }
    }

    public static void gotoActivity(Activity activity, Bundle bundle) {
        activity.startActivity(new Intent(activity, UriActivity.class).putExtras(bundle));
    }

    public static void gotoActivity(Activity activity) {
        activity.startActivity(new Intent(activity, UriActivity.class));
    }

}
