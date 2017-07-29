package com.hover.hf.ui.login;

import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hover.hf.AppContext;
import com.hover.hf.R;
import com.hover.hf.bean.RegisterBean;
import com.hover.hf.callback.RegisterCallback;
import com.hover.hf.common.Urls;
import com.hover.hf.ui.base.BaseActivity;
import com.hover.hf.util.CyptoUtils;
import com.hover.hf.util.TDevice;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by hover on 2017/6/19.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.et_username)
    AppCompatEditText etUsername;
    @Bind(R.id.et_password)
    AppCompatEditText etPassword;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.mobile_login)
    AppCompatEditText phoneNum;
    @Bind(R.id.verifi_code)
    AppCompatEditText verifiCode;
    @Bind(R.id.btn_getcode)
    Button btnGetcode;
    @Bind(R.id.et_password_phone)
    AppCompatEditText etPasswordPhone;
    @Bind(R.id.btn_register_phone)
    Button btnRegisterPhone;
    private String mUserName = "";
    private String mPassword = "";

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        etUsername.setText("qq");
        etPassword.setText("000000");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.register;
    }

    @OnClick({R.id.btn_register, R.id.btn_getcode, R.id.btn_register_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                mUserName = etUsername.getText().toString();
                mPassword = etPassword.getText().toString();
                String pwd = CyptoUtils.encode("hfapp_encode", mPassword);
                register(mUserName, CyptoUtils.encode("hfapp_encode", mPassword));
                break;
            case R.id.btn_getcode:
                RequestParams params = new RequestParams(Urls.REGISTER_PHONE);
                params.addParameter("phonenum", phoneNum.getText().toString().trim());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        //解析result
                        LogUtil.e("hw : " + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogUtil.e("onError:　" + ex.toString());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        LogUtil.e("onCancelled");
                    }
                });

                break;
            case R.id.btn_register_phone:
                break;
        }
    }

    private boolean prepareForRegister() {
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

    private void register(String account, String pwd) {
        if (prepareForRegister()) {
            return;
        }

        OkHttpUtils
                .post()
                .url(Urls.REGISTER)
                .addParams("username", account)
                .addParams("password", pwd)
                .build()
                .execute(new RegisterCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("hw", "exception = " + e.toString());
                    }

                    @Override
                    public void onResponse(RegisterBean response, int id) {
                        Log.e("hw", "onResponse = " + response.toString());
                        if (response != null) {
                            if (response.getData().isOk()) {
                                RegisterActivity.this.finish();
                            }
                        }
                        Toast.makeText(mContext, response.getData().getInfo(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
