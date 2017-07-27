package com.hover.hf.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hover.hf.AppManager;
import com.hover.hf.R;
import com.hover.hf.interf.BaseViewInterface;
import com.hover.hf.util.TDevice;

import org.kymjs.kjframe.utils.StringUtils;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import butterknife.ButterKnife;

/**
 * Created by hover on 2017/6/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {
    public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";

    private boolean _isVisible;
    private ProgressDialog _waitDialog;

    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;
    protected Context mContext;
    protected ImageOptions imageOptions;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (AppContext.getNightModeSwitch()) {
//            setTheme(R.style.AppBaseTheme_Night);
//        } else {
//            setTheme(R.style.AppBaseTheme_Light);
//        }
        mContext = this;
        AppManager.getAppManager().addActivity(this);
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mActionBar = getSupportActionBar();
        mInflater = getLayoutInflater();
        if (hasActionBar()) {
            initActionBar(mActionBar);
        }

        // 通过注解绑定控件
        ButterKnife.bind(this);

        init(savedInstanceState);
        initView();
        initData();
        _isVisible = true;

        imageOptions = new ImageOptions.Builder()
//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.mipmap.robot)
                .setFailureDrawableId(R.mipmap.robot)
                .build();
    }

    protected void onBeforeSetContentLayout() {
    }

    protected boolean hasActionBar() {
        return getSupportActionBar() != null;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected void initActionBar(ActionBar actionBar) {
        if (actionBar == null)
            return;
        if (hasBackButton()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } else {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setDisplayUseLogoEnabled(false);
            int titleRes = getActionBarTitle();
            if (titleRes != 0) {
                actionBar.setTitle(titleRes);
            }
        }
    }

    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar() && mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()) {
            TDevice.hideSoftKeyboard(getCurrentFocus());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    public void showToast(int msgResid, int icon, int gravity) {
//        showToast(getString(msgResid), icon, gravity);
//    }

//    public void showToast(String message, int icon, int gravity) {
//        CommonToast toast = new CommonToast(this);
//        toast.setMessage(message);
//        toast.setMessageIc(icon);
//        toast.setLayoutGravity(gravity);
//        toast.show();
//    }
//
//    @Override
//    public ProgressDialog showWaitDialog() {
//        return showWaitDialog(R.string.loading);
//    }
//
//    @Override
//    public ProgressDialog showWaitDialog(int resid) {
//        return showWaitDialog(getString(resid));
//    }
//
//    @Override
//    public ProgressDialog showWaitDialog(String message) {
//        if (_isVisible) {
//            if (_waitDialog == null) {
//                _waitDialog = DialogHelp.getWaitDialog(this, message);
//            }
//            if (_waitDialog != null) {
//                _waitDialog.setMessage(message);
//                _waitDialog.show();
//            }
//            return _waitDialog;
//        }
//        return null;
//    }
//
//    @Override
//    public void hideWaitDialog() {
//        if (_isVisible && _waitDialog != null) {
//            try {
//                _waitDialog.dismiss();
//                _waitDialog = null;
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }

    /** 初始化 Toolbar */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }
}
