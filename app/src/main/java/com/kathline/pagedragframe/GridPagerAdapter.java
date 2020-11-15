package com.kathline.pagedragframe;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kathline.pagedrag2.viewpager.BaseDragPageAdapter;
import com.kathline.pagedrag2.bean.DragInfo;
import com.kathline.pagedrag2.DragListenerDispatcher;

import java.util.List;

public class GridPagerAdapter extends BaseDragPageAdapter<App> {

    public GridPagerAdapter(Context context, List<App> list, DragListenerDispatcher<ViewPager, DragInfo> pagerListener) {
        super(context, 4, 4, list, pagerListener);
    }

    @Override
    public BaseDragPageAdapter.DragAdapter generateItemAdapter(List<App> data, int pageIndex) {
        return new ItemAdapter(data, pageIndex);
    }

    class ItemAdapter extends DragAdapter<ItemViewHolder> {

        public ItemAdapter(List<App> list, int pageIndex) {
            super(list, pageIndex);
        }

        @Override
        public int getPositionForId(long id) {
            for (int i = 0; i < getData().size(); i++) {
                if (getData().get(i).hashCode() == id) {
                    return i;
                }
            }
            return RecyclerView.NO_POSITION;
        }

        @Override
        public long getItemId(int position) {
            return getData().get(position).hashCode();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType, int pageIndex) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);

            return new ItemViewHolder(inflate, pageIndex);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position, int pageIndex) {
            App info = data.get(position);
            holder.iv_app_icon.setImageDrawable(info._icon);
            holder.tv_app_name.setText(info._label);
        }
    }

    private static class ItemViewHolder extends PageViewHolder {

        private final ImageView iv_app_icon;
        private final TextView tv_app_name;

        ItemViewHolder(View itemView, int pageIndex) {
            super(itemView, pageIndex);
            iv_app_icon = itemView.findViewById(R.id.iv_app_icon);
            tv_app_name = itemView.findViewById(R.id.tv_app_name);
        }
    }

}
