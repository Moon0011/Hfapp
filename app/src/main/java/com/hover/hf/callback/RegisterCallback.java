package com.hover.hf.callback;

import android.util.Log;

import com.google.gson.Gson;
import com.hover.hf.bean.RegisterBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by hover on 2017/6/18.
 */

public abstract class RegisterCallback extends Callback<RegisterBean> {
    @Override
    public RegisterBean parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Log.e("hw", "response = " + string);
        RegisterBean registerBean = new Gson().fromJson(string, RegisterBean.class);
        return registerBean;

    }
}
