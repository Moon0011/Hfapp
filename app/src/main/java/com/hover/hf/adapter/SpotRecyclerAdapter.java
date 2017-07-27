package com.hover.hf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.hover.hf.R;
import com.hover.hf.bean.SpotData;
import com.hover.hf.bean.SpotInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hover on 2017/4/21.
 */

public class SpotRecyclerAdapter extends BaseRecyclerAdapter<SpotRecyclerAdapter.SimpleAdapterViewHolder> {
    @Bind(R.id.img)
    ImageView imgeView;
    @Bind(R.id.tv_indr)
    TextView tvIndr;
    @Bind(R.id.tv_addr)
    TextView tvAddr;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    private List<SpotInfo> list;
    private Context mContext;
    public SpotRecyclerAdapter(SpotData spotData, Context context) {
        this.list = spotData.getSpots();
        this.mContext = context;
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view);
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recylerview_new, parent, false);
        ButterKnife.bind(this, v);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SimpleAdapterViewHolder holder, int position, boolean isItem) {
        SpotInfo spot = list.get(position);
        holder.indr.setText(spot.getIntroduce());
        holder.addr.setText(spot.getAddress());
        holder.date.setText(spot.getDate());
        holder.price.setText(spot.getPrice());
        //加载网络图片  
        Picasso.with(mContext).load(spot.getImgurl()).into(holder.img);
    }

    public void setData(List<SpotInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView addr, price, date, indr;
        public ImageView img;

        public SimpleAdapterViewHolder(View itemView) {
            super(itemView);
            addr = tvAddr;
            price = tvPrice;
            date = tvDate;
            indr = tvIndr;
            img = imgeView;
        }
    }

//    public SpotInfo getItem(int position) {
//        if (position < list.size())
//            return list.get(position);
//        else
//            return null;
//    }
}
