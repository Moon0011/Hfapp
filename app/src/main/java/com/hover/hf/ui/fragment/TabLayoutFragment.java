package com.hover.hf.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.view.View;

import com.hover.hf.R;
import com.hover.hf.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by hover on 2017/6/26.
 */

public class TabLayoutFragment extends BaseFragment {

    @Bind(R.id.tab)
    TabLayout tab;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private List<Pair<String, Fragment>> items;

    public static TabLayoutFragment newInstance() {
        TabLayoutFragment tabLayoutFragment = new TabLayoutFragment();
        return tabLayoutFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tab_layout_fragment;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        super.initData();
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("头条", new HotFragment()));
        items.add(new Pair<String, Fragment>("热点", new HotFragment()));
        items.add(new Pair<String, Fragment>("图片", new HotFragment()));
        items.add(new Pair<String, Fragment>("新闻", new HotFragment()));
        items.add(new Pair<String, Fragment>("综艺", new HotFragment()));
        items.add(new Pair<String, Fragment>("综艺", new HotFragment()));
        items.add(new Pair<String, Fragment>("综艺", new HotFragment()));
        items.add(new Pair<String, Fragment>("综艺", new HotFragment()));
        items.add(new Pair<String, Fragment>("综艺", new HotFragment()));

        viewPager.setAdapter(new MainAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(viewPager);
    }

    private class MainAdapter extends FragmentPagerAdapter {

        MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position).second;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).first;
        }
    }
}
