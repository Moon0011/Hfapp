package com.hover.hf.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.hover.hf.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MainActivity2 extends AppCompatActivity {
    @Bind(R.id.btn_getjson)
    Button btnGetjson;
    @Bind(R.id.tv_showjson)
    TextView tvShowjson;
    @Bind(R.id.btn_web)
    Button btnWeb;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.btn_list)
    Button btnList;
//    private String url = "http://192.168.0.103:8080/JsonTest/getJson";
//    private String url = "http://192.168.0.103:8080/HSS/spotinfo_respJson.action";
    private String url = "http://192.168.0.103:63342/phplearn/response.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    @OnClick(R.id.btn_list)
    public void toList() {
        startActivity(new Intent(this, XRefreshViewActivity.class));
    }

    @OnClick(R.id.btn_getjson)
    public void getJson() {
        getJsonFromDjango(url);
    }

    @OnClick(R.id.btn_web)
    public void goWeb() {
//        webView.loadUrl("http://192.168.0.103:8080/roadArchitectWeb/login/login.jsp");
        webView.loadUrl("http://192.168.0.103:8080/HSS");
//        webView.loadUrl("http://192.168.0.103:8080/JsonTest/getJson?page=1");
        webView.loadUrl("http://192.168.0.103:8080/JsonTest/getJson");
    }

    private void getJsonFromDjango(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("django", "onError E = " + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("django", "onResponse JSONSTR = " + response);
                        tvShowjson.setText(response);
                    }
                });

    }
}
