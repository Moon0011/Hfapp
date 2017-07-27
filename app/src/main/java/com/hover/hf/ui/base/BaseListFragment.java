package com.hover.hf.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hover.hf.AppOperator;
import com.hover.hf.R;
import com.hover.hf.adapter.base.BaseListAdapter;
import com.hover.hf.bean.HqRespBean;
import com.hover.hf.cache.CacheManager;
import com.hover.hf.view.EmptyLayout;
import com.hover.hf.widget.SuperRefreshLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * T as the base bean
 * Created by huanghaibin
 * on 16-5-23.
 */
public abstract class BaseListFragment<T> extends BaseFragment implements
        SuperRefreshLayout.SuperRefreshLayoutListener,
        AdapterView.OnItemClickListener, BaseListAdapter.Callback,
        View.OnClickListener {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOADING = 1;
    public static final int TYPE_NO_MORE = 2;
    public static final int TYPE_ERROR = 3;
    public static final int TYPE_NET_ERROR = 4;
    protected String CACHE_NAME = getClass().getName();
    protected ListView mListView;
    protected SuperRefreshLayout mRefreshLayout;
    protected EmptyLayout mErrorLayout;
    protected BaseListAdapter<T> mAdapter;
    protected HqRespBean<T> mBean;
    protected boolean mIsRefresh;
    private View mFooterView;
    private ProgressBar mFooterProgressBar;
    private TextView mFooterText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mListView = (ListView) root.findViewById(R.id.listView);
        mRefreshLayout = (SuperRefreshLayout) root.findViewById(R.id.superRefreshLayout);
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);
        mErrorLayout = (EmptyLayout) root.findViewById(R.id.error_layout);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_view_footer, null);
        mFooterText = (TextView) mFooterView.findViewById(R.id.tv_footer);
        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pb_footer);
        mListView.setOnItemClickListener(this);
        setFooterType(TYPE_LOADING);
        mErrorLayout.setOnLayoutClickListener(this);
        if (isNeedFooter())
            mListView.addFooterView(mFooterView);
        //
        mListView.setSelection(0);
    }

    @Override
    protected void initData() {
        super.initData();
        //when open this fragment,read the obj
        mAdapter = getListAdapter();
        mListView.setAdapter(mAdapter);

        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                mBean = (HqRespBean<T>) CacheManager.readObject(getActivity(), CACHE_NAME);
                //if is the first loading
                if (mBean == null) {
                    mBean = new HqRespBean<T>();
                    mBean.setData(new ArrayList<T>());
                    onRefreshing();
                } else {
                    mRoot.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addItem(mBean.getData());
                            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                            mRefreshLayout.setVisibility(View.VISIBLE);
//                            onRefreshing();
                            mRefreshLayout.setCanLoadMore();
                            setFooterType(TYPE_NORMAL);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        onRefreshing();
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

    protected void requestData() {
        setFooterType(TYPE_LOADING);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected void onRequestError(int code) {
        setFooterType(TYPE_NET_ERROR);
        if (mAdapter.getDatas().size() == 0)
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
    }

    protected void onRequestFinish() {
        mRefreshLayout.onLoadComplete();
        mIsRefresh = false;
    }

    protected void setListData(final HqRespBean<T> resultBean) {
        if (mIsRefresh) {
            //cache the time
            mAdapter.clear();
            mAdapter.addItem(resultBean.getData());
            mRefreshLayout.setCanLoadMore();
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    if (resultBean != null) {
                        CacheManager.saveObject(getActivity(), resultBean, CACHE_NAME);
                    }
                }
            });
        } else {
            mAdapter.addItem(resultBean.getData());
        }
        int perpage = resultBean.getMeta().getPagination().getPer_page();
        int size = resultBean.getData().size();
        if (size < perpage) {
            setFooterType(TYPE_NO_MORE);
        } else {
            setFooterType(TYPE_NORMAL);
        }
        if (mAdapter.getDatas().size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mRefreshLayout.setVisibility(View.VISIBLE);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.NODATA);
        }
    }


    @Override
    public Date getSystemTime() {
        return new Date();
    }

    protected abstract BaseListAdapter<T> getListAdapter();

    protected abstract Type getType();

    protected boolean isNeedFooter() {
        return true;
    }

    protected void setFooterType(int type) {
        switch (type) {
            case TYPE_NORMAL:
                mFooterText.setText(getResources().getString(R.string.footer_type_addmore));
                mFooterProgressBar.setVisibility(View.GONE);
                break;
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
