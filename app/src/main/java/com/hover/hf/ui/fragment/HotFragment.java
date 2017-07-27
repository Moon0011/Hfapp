package com.hover.hf.ui.fragment;

import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.hover.hf.AppContext;
import com.hover.hf.adapter.HotAdapter;
import com.hover.hf.adapter.base.BaseListAdapter;
import com.hover.hf.bean.Hot;
import com.hover.hf.bean.HqRespBean;
import com.hover.hf.common.Urls;
import com.hover.hf.ui.base.BaseListFragment;
import com.hover.hf.util.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by hover on 2017/6/16.
 */

public class HotFragment extends BaseListFragment {
    private boolean isFirst = true;
    private int page = 1;

    public static HotFragment newInstance() {
        HotFragment hotFragment = new HotFragment();
        return hotFragment;
    }

    @Override
    protected BaseListAdapter getListAdapter() {
        return new HotAdapter(this);
    }

    @Override
    protected Type getType() {
        return new TypeToken<HqRespBean<Hot>>() {
        }.getType();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void requestData() {
        super.requestData();
        if (!isFirst) {
            ++page;
        }
        if (mIsRefresh) {
            page = 1;
        }
        StringBuffer sbf = new StringBuffer(Urls.REQHOT);
        sbf.append("?").append("page=").append(page);
        Log.e("hw", "page = " + page);
        getHotList(sbf.toString());
    }

    //    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//    }
    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
        isFirst = false;
    }

    private void getHotList(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("hw", "onError E = " + e.toString() + " , id = " + id);
                        onRequestError(id);
                        onRequestFinish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("hw", "onResponse response = " + response.toString() + " , id = " + id);
                        try {
                            HqRespBean<Hot> resultBean = AppContext.createGson().fromJson(response, getType());
                            if (resultBean != null && resultBean.getData() != null) {
                                setListData(resultBean);
                            } else {
                                if (!StringUtils.isEmpty(response)) {
                                    int perpage = resultBean.getMeta().getPagination().getPer_page();
                                    int size = resultBean.getData().size();
                                    if (size < perpage) {
                                        setFooterType(TYPE_NO_MORE);
                                    } else {
                                        setFooterType(TYPE_NORMAL);
                                    }
                                } else {
                                    setFooterType(TYPE_NO_MORE);
                                }
                            }
                            onRequestFinish();
                        } catch (Exception e) {
                            Log.e("hw", "e = " + e.toString());
                            e.printStackTrace();
                            onRequestError(id);
                            onRequestFinish();
                        }
                    }
                });
    }

}
