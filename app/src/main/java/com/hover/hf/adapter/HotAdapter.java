package com.hover.hf.adapter;

import android.widget.ImageView;

import com.hover.hf.R;
import com.hover.hf.adapter.base.BaseListAdapter;
import com.hover.hf.bean.Hot;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by hover on 2017/6/16.
 */

public class HotAdapter extends BaseListAdapter<Hot> {
    protected ImageOptions imageOptions;

    public HotAdapter(Callback callback) {
        super(callback);
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

    @Override
    protected void convert(ViewHolder vh, Hot item, int position) {
        vh.setText(R.id.title, item.getTitle());
        vh.setText(R.id.source, item.getSource());
        vh.setText(R.id.title, item.getTitle());
//        vh.setImageForNet(R.id.icon, item.getThumb());
        x.image().bind((ImageView) vh.getView(R.id.icon),
                item.getThumb(),
                imageOptions,
                null);
    }

    @Override
    protected int getLayoutId(int position, Hot item) {
        return R.layout.hq_list_item;
    }
}
