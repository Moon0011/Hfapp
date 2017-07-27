package com.hover.hf.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hover.hf.R;
import com.hover.hf.adapter.NewsAdapter2;
import com.hover.hf.bean.SpotData;
import com.hover.hf.widget.SuperRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by hover on 2017/6/10.
 */

public class NewsFragment2 extends Fragment implements
        SuperRefreshLayout.SuperRefreshLayoutListener {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOADING = 1;
    public static final int TYPE_NO_MORE = 2;
    public static final int TYPE_ERROR = 3;
    public static final int TYPE_NET_ERROR = 4;
    private NewsAdapter2 adapter;
    private Context mContext;
    private String url = "http://192.168.0.103:8081/webapp/public/index.php";
    protected ListView mListView;
    protected SuperRefreshLayout mRefreshLayout;
    protected boolean mIsRefresh;
    private View mFooterView;
    private ProgressBar mFooterProgressBar;
    private TextView mFooterText;

    public static NewsFragment2 newInstance() {
        NewsFragment2 newsFragment = new NewsFragment2();
        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View fragmentView = inflater.inflate(R.layout.news_fragment, container, false);
//        ButterKnife.bind(mContext, fragmentView);
        mListView = (ListView) fragmentView.findViewById(R.id.listView);
        mRefreshLayout = (SuperRefreshLayout) fragmentView.findViewById(R.id.superRefreshLayout);
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_view_footer, null);
        mFooterText = (TextView) mFooterView.findViewById(R.id.tv_footer);
        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pb_footer);
        setFooterType(TYPE_LOADING);
        if (isNeedFooter())
            mListView.addFooterView(mFooterView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
    }

    private void getNews(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("django", "onError E = " + e.toString());
                        onRequestError(id);
                        onRequestFinish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("django", "onResponse JSONSTR = " + response);
                        SpotData spotData = new Gson().fromJson(response, SpotData.class);
                        if (null != spotData) {
                            adapter = new NewsAdapter2(spotData, mContext);
                            mListView.setAdapter(adapter);
                        } else {
                            setFooterType(TYPE_NO_MORE);
                        }
                        onRequestFinish();
                    }
                });
    }

    @Override
    public void onRefreshing() {
        mIsRefresh = true;
        requestData();
    }

    @Override
    public void onLoadMore() {
        requestData();
    }

//    @Override
//    public void onLoadMore() {
//        requestData();
//    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onComplete() {
        mRefreshLayout.onLoadComplete();
        mIsRefresh = false;
    }

    protected void onRequestError(int code) {
        setFooterType(TYPE_NET_ERROR);
//        if (mAdapter.getDatas().size() == 0)
//            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
    }

    /**
     * request network data
     */
    protected void requestData() {
        getNews(url);
        setFooterType(TYPE_LOADING);
    }

    protected boolean isNeedFooter() {
        return true;
    }

    protected void setFooterType(int type) {
        switch (type) {
            case TYPE_NORMAL:
            case TYPE_LOADING:
                mFooterText.setText(getResources().getString(R.string.footer_type_loading));
                mFooterProgressBar.setVisibility(View.VISIBLE);
                break;
            case TYPE_NET_ERROR:
                mFooterText.setText(getResources().getString(R.string.footer_type_net_error));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            case TYPE_ERROR:
                mFooterText.setText(getResources().getString(R.string.footer_type_error));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
            case TYPE_NO_MORE:
                mFooterText.setText(getResources().getString(R.string.footer_type_not_more));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
        }
    }


}
