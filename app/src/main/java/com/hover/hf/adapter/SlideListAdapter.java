package com.hover.hf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hover.hf.R;

import java.util.List;

/**
 * Created by hover on 2017/6/19.
 */

public class SlideListAdapter extends BaseAdapter {
    private List<String> mDatas;
    private LayoutInflater mInflater;

    public SlideListAdapter(List<String> datas, Context context) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.slide_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mDatas.get(position));
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

}
