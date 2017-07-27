package com.hover.hf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hover.hf.ui.fragment.TabLayoutFragment;
import com.hover.hf.widget.adaptablebottomnav.adapter.FragmentStateAdapter;


public class ViewSwapperAdapter extends FragmentStateAdapter {

    private static final int INDEX_BUFFER = 0;
    private static final int INDEX_RETREAT = 1;
    private static final int INDEX_VALUES = 2;

    public ViewSwapperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INDEX_BUFFER:
                return TabLayoutFragment.newInstance();
            case INDEX_RETREAT:
                return TabLayoutFragment.newInstance();
            case INDEX_VALUES:
                return TabLayoutFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
