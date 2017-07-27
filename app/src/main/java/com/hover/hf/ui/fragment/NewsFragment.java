//package com.hover.djangotest.ui.fragment;
//
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//
//import com.google.gson.reflect.TypeToken;
//import com.hover.djangotest.AppContext;
//import com.hover.djangotest.AppOperator;
//import com.hover.djangotest.adapter.NewsAdapter;
//import com.hover.djangotest.adapter.base.BaseListAdapter;
//import com.hover.djangotest.bean.Banner;
//import com.hover.djangotest.bean.News;
//import com.hover.djangotest.bean.PageBean;
//import com.hover.djangotest.bean.ResultBean;
//import com.hover.djangotest.cache.CacheManager;
//import com.hover.djangotest.ui.base.BaseListFragment;
//import com.hover.djangotest.widget.ViewNewsHeader;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//
//import java.lang.reflect.Type;
//
//import okhttp3.Call;
//
//public class NewsFragment extends BaseListFragment<News> {
//    public static final String NEWS_SYSTEM_TIME = "news_system_time";
//    private boolean isFirst = true;
//    private static final String NEWS_BANNER = "news_banner";
//    private ViewNewsHeader mHeaderView;
//    private Handler handler = new Handler();
//    private String url = "http://192.168.0.103:8081/webapp/public/index.php";
//    private String bannerurl = "http://192.168.0.103:8081/webapp/public/index.php/index/index/banner";
//
//    public static NewsFragment newInstance() {
//        NewsFragment newsFragment = new NewsFragment();
//        return newsFragment;
//    }
//
//    @Override
//    protected void initWidget(View root) {
//        super.initWidget(root);
//        mHeaderView = new ViewNewsHeader(getActivity());
//        AppOperator.runOnThread(new Runnable() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public void run() {
//                final PageBean<Banner> pageBean = (PageBean<Banner>) CacheManager.readObject(getActivity(), NEWS_BANNER);
//                if (pageBean != null) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            ((NewsAdapter) mAdapter).setSystemTime(AppContext.get(NEWS_SYSTEM_TIME, null));
//                            mHeaderView.initData(getImgLoader(), pageBean.getItems());
//                        }
//                    });
//                }
//            }
//        });
//
//        mHeaderView.setRefreshLayout(mRefreshLayout);
//        mListView.addHeaderView(mHeaderView);
//        getBannerList();
//    }
//
//    @Override
//    public void onRefreshing() {
//        super.onRefreshing();
//        if (!isFirst)
//            getBannerList();
//    }
//
//    @Override
//    protected void requestData() {
//        super.requestData();
//        getNews(url);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////        News news = mAdapter.getItem(position - 1);
////        if (news != null) {
////            int type = news.getType();
////            long newsId = news.getId();
////            UIHelper.showDetail(getActivity(), type, newsId, news.getHref());
////            TextView title = (TextView) view.findViewById(R.id.tv_title);
////            TextView content = (TextView) view.findViewById(R.id.tv_description);
////            updateTextColor(title, content);
////            saveToReadedList(HISTORY_NEWS, news.getId() + "");
////        }
//    }
//
//    @Override
//    protected BaseListAdapter<News> getListAdapter() {
//        return new NewsAdapter(this);
//    }
//
//    @Override
//    protected Type getType() {
//        return new TypeToken<ResultBean<PageBean<News>>>() {
//        }.getType();
//    }
//
//    @Override
//    protected void onRequestFinish() {
//        super.onRequestFinish();
//        isFirst = false;
//    }
//
//    private void getNews(String url) {
//        OkHttpUtils
//                .get()
//                .url(url)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("django", "onError E = " + e.toString());
////                        onRequestError(statusCode);
//                        onRequestFinish();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("django", "onResponse JSONSTR = " + response);
//                        try {
//                            ResultBean<PageBean<News>> resultBean = AppContext.createGson().fromJson(response, getType());
//                            if (resultBean != null && resultBean.isSuccess() && resultBean.getResult().getItems() != null) {
////                                onRequestSuccess(resultBean.getCode());
//                                setListData(resultBean);
//                            } else {
//                                setFooterType(TYPE_NO_MORE);
//                            }
//                            onRequestFinish();
//                        } catch (Exception e) {
//                            Log.e("hw", "e = " + e.toString());
//                            e.printStackTrace();
////                            onRequestError(statusCode);
//                            onRequestFinish();
//                        }
//                    }
//                });
//    }
//
//    private void getBannerList() {
//        OkHttpUtils
//                .get()
//                .url(bannerurl)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e("django", "onError E = " + e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("django", "onResponse JSONSTR = " + response);
//                        try {
//                            final ResultBean<PageBean<Banner>> resultBean = AppContext.createGson().fromJson(response, new TypeToken<ResultBean<PageBean<Banner>>>() {
//                            }.getType());
//                            if (resultBean != null && resultBean.isSuccess()) {
//                                AppOperator.runOnThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        CacheManager.saveObject(getActivity(), resultBean.getResult(), NEWS_BANNER);
//                                    }
//                                });
//                                mHeaderView.initData(getImgLoader(), resultBean.getResult().getItems());
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//}
