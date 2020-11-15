package com.kathline.pagedragframe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kathline.pagedrag.adapter.DragPageAdapter;
import com.kathline.pagedrag.bean.PageData;
import com.kathline.pagedragframe.R;

import java.util.List;

public class MyAdapter extends DragPageAdapter<App> {
    public MyAdapter(Context context, PageData<App> pageData) {
        super(context, pageData, true);
    }

    @Override
    public ItemAdapter generateItemRecyclerAdapter(List<App> pageData, int pageIndex) {
        return new ItemAdapter(pageData, pageIndex);
    }

    private class ItemAdapter extends ItemDragAdapter<ItemViewHolder> {

        ItemAdapter(List<App> list, int pageIndex) {
            super(list, pageIndex);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
            return new ItemViewHolder(inflate);
        }

        @Override
        public void onBindItemViewHolder(ItemViewHolder holder, int position) {
            App info = data.get(position);
            holder.iv_app_icon.setImageDrawable(info._icon);
            holder.tv_app_name.setText(info._label);
        }

        @Override
        public void onBindItemViewHolder(ItemViewHolder holder, int position, List<Object> payloads) {
            if (payloads != null && !payloads.isEmpty()) {

            } else {
                onBindViewHolder(holder, position);
            }
        }

        @Override
        public long getStableItemId(int position) {
            return data.get(position).hashCode();
        }

        @Override
        public int getPositionForId(long itemId) {
            int size = data.size();
            for (int i = 0; i < size; i++) {
                int positionItemId = data.get(i).hashCode();
                if (positionItemId == itemId) {
                    return i;
                }
            }
            return RecyclerView.NO_POSITION;
        }

    }

    private static class ItemViewHolder extends DragViewHolder {

        private final ImageView iv_app_icon;
        private final TextView tv_app_name;

        ItemViewHolder(View itemView) {
            super(itemView);
            iv_app_icon = itemView.findViewById(R.id.iv_app_icon);
            tv_app_name = itemView.findViewById(R.id.tv_app_name);
        }
    }
}
