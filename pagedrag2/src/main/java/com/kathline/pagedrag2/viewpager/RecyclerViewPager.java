package com.kathline.pagedrag2.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;

public class RecyclerViewPager extends BaseViewPager {
    public RecyclerViewPager(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@link BaseGridPagerAdapter}
     */
    public void release() {
        PagerAdapter adapter = getAdapter();
        if(adapter instanceof BasePagerAdapter){
            ((BasePagerAdapter) adapter).release();
        }
        removeAllViews();
    }

    public void restore() {
        PagerAdapter adapter = getAdapter();
        if(adapter instanceof BasePagerAdapter) {
            ((BasePagerAdapter) adapter).setContext(getContext());
            adapter.notifyDataSetChanged();
        }
    }
}
