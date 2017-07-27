package com.hover.hf.ui.saoyisao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hover.hf.R;
import com.hover.hf.ui.base.BaseActivity;
import com.mylhyl.zxing.scanner.common.Intents;

import butterknife.Bind;

/**
 * 纯文本显示
 */
public class TextActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.textView3)
    TextView textView3;

    public static void gotoActivity(Activity activity, Bundle bundle) {
        activity.startActivity(new Intent(activity, TextActivity.class).putExtras(bundle));
    }

    @Override
    public void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            textView3.setText(extras.getString(Intents.Scan.RESULT));
    }
}
