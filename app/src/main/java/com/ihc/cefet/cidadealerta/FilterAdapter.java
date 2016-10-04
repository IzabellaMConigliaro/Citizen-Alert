package com.ihc.cefet.cidadealerta;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private List<ItemFilter> mData;

    public FilterAdapter(List<String> data) {
        mData = new ArrayList<>();

        for(String s : data) {
            mData.add(new ItemFilter(s));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
            return new MyItem(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyItem) {
            ((MyItem) holder).title.setText(mData.get(position).getTitle());

            ((MyItem) holder).item_category_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.get(position).setSelected(!mData.get(position).isSelected());
                    ((MyItem) holder).title.setSelected(mData.get(position).isSelected());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    private String getItem(int position) {
        return mData.get(position).getTitle();
    }

    class MyItem extends RecyclerView.ViewHolder {
        TextView title;
        LinearLayoutCompat item_category_layout;

        public MyItem(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            item_category_layout = (LinearLayoutCompat) itemView.findViewById(R.id.item_category_layout);
        }
    }

    private class ItemFilter {
        String title;
        boolean selected;

        public ItemFilter(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}