package com.hover.hf.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hover.hf.AppContext;
import com.hover.hf.R;
import com.hover.hf.bean.LoginRespBean;
import com.hover.hf.callback.UserCallback;
import com.hover.hf.common.Urls;
import com.hover.hf.ui.base.BaseActivity;
import com.hover.hf.util.CyptoUtils;
import com.hover.hf.util.TDevice;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

import static com.umeng.socialize.a.b.d.i;

/**
 * Created by hover on 2017/6/17.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_username)
    AppCompatEditText etUsername;
    @Bind(R.id.et_password)
    AppCompatEditText etPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.iv_qq_login)
    ImageView ivQqLogin;
    @Bind(R.id.iv_wx_login)
    ImageView ivWxLogin;
    @Bind(R.id.iv_sina_login)
    ImageView ivSinaLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    private String mUserName = "";
    private String mPassword = "";
    private UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            AppContext.showToast("授权成功");
            if (data != null) {
                StringBuilder sb = new StringBuilder("{");
                Set<String> keys = data.keySet();
                int index = 0;
                for (String key : keys) {
                    index++;
                    String jsonKey = key;
                    if (jsonKey.equals("uid")) {
                        jsonKey = "openid";
                    }
                    sb.append(String.format("\"%s\":\"%s\"", jsonKey, data.get(key).toString()));
                    if (index != data.size()) {
                        sb.append(",");
                    }
                }
                sb.append("}");
                Log.e("hw", "thirdInfo = " + sb.toString());
                handleLogin(sb.toString());
            } else {
                AppContext.showToast("发生错误：" + i);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            AppContext.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            AppContext.showToast("已取消登陆");
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.login_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        etUsername.setText("qq");
        etPassword.setText("123456");
    }

    @OnClick({R.id.btn_login, R.id.iv_qq_login, R.id.iv_wx_login, R.id.iv_sina_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (!AppContext.getInstance().isLogin()) {
                    handleLogin();
                }
                break;
            case R.id.iv_qq_login:
                UMShareAPI.get(mContext).doOauthVerify(this, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.iv_wx_login:
                UMShareAPI.get(mContext).doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.iv_sina_login:
                UMShareAPI.get(mContext).doOauthVerify(this, SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return true;
        }
        if (etUsername.length() == 0) {
            etUsername.setError("请输入邮箱/用户名");
            etUsername.requestFocus();
            return true;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("请输入密码");
            etPassword.requestFocus();
            return true;
        }
        return false;
    }

    private void handleLogin() {
        handleLogin("");
    }

    private void handleLogin(String thirdlogin) {
        if (prepareForLogin()) {
            return;
        }
        mUserName = etUsername.getText().toString();
        mPassword = etPassword.getText().toString();
//        showWaitDialog(R.string.progress_login);
        login(mUserName, CyptoUtils.encode("hfapp_encode", mPassword), thirdlogin);
    }

    private void login(String userName, String password) {
        login(userName, password, null);
    }

    private void login(String userName, String password, String thirdloginInfo) {
        OkHttpUtils
                .post()
                .url(Urls.LOGIN)
                .addParams("username", userName)
                .addParams("password", password)
                .addParams("thirdloginInfo", thirdloginInfo)
                .build()
                .execute(new UserCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("hw", "exception = " + e.toString());
                    }

                    @Override
                    public void onResponse(LoginRespBean response, int id) {
                        if (response != null) {
                            Log.e("hw", "onResponse = " + response.toString());
                            if (response.getData().isLogin()) {
                                int loginType = response.getData().getLoginType();
                                if (loginType == 1) {

                                } else if (loginType == 0) {
                                    response.getData().setAccount(mUserName);
                                    response.getData().setPwd(mPassword);
                                    AppContext.getInstance().saveUserInfo(response.getData());
                                }
                                LoginActivity.this.finish();
                            } else {
                                AppContext.getInstance().cleanLoginInfo();
                            }
                            Toast.makeText(mContext, response.getData().getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "登录出错!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
