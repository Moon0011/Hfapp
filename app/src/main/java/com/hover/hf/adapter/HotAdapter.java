package com.hover.hf.adapter;

import com.hover.hf.R;
import com.hover.hf.adapter.base.BaseListAdapter;
import com.hover.hf.bean.Hot;

/**
 * Created by hover on 2017/6/16.
 */

public class HotAdapter extends BaseListAdapter<Hot> {
    public HotAdapter(Callback callback) {
        super(callback);
    }

    @Override
    protected void convert(ViewHolder vh, Hot item, int position) {
        vh.setText(R.id.title, item.getTitle());
        vh.setText(R.id.source, item.getSource());
        vh.setText(R.id.title, item.getTitle());
        vh.setImageForNet(R.id.icon, item.getThumb(), R.mipmap.robot);
    }

    @Override
    protected int getLayoutId(int position, Hot item) {
        return R.layout.hq_list_item;
    }
}
