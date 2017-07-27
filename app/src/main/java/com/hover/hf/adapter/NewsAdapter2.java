package com.hover.hf.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hover.hf.R;
import com.hover.hf.bean.SpotData;
import com.hover.hf.bean.SpotInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hover on 2017/6/11.
 */

public class NewsAdapter2 extends BaseAdapter {
    private List<SpotInfo> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public NewsAdapter2(SpotData spotData, Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDatas = spotData.getSpots();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < mDatas.size())
            return mDatas.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("hw","getView");
        SpotInfo spot = mDatas.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.news_item_layout, null);
            holder = new ViewHolder();
            holder.indr = (TextView) convertView.findViewById(R.id.tv_indr);
            holder.addr = (TextView) convertView.findViewById(R.id.tv_addr);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.indr.setText(spot.getIntroduce());
        holder.addr.setText(spot.getAddress());
        holder.date.setText(spot.getDate());
        holder.price.setText(spot.getPrice());
        //加载网络图片  
        Picasso.with(mContext).load(spot.getImgurl()).into(holder.img);
        return convertView;


    }

    public void setData(List<SpotInfo> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    class ViewHolder {
        public TextView indr, addr, date, price;
        public ImageView img;
    }

}
