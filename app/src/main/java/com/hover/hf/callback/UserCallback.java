package com.hover.hf.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.hover.hf.bean.LoginRespBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by hover on 2017/6/18.
 */

public abstract class UserCallback extends Callback<LoginRespBean> {
    @Override
    public LoginRespBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("hw", "response = " + string);
        LoginRespBean loginRespBean = new Gson().fromJson(string, LoginRespBean.class);
        return loginRespBean;

    }
}
