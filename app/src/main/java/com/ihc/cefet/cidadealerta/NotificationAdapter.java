package com.ihc.cefet.cidadealerta;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 1;
    private List<Item> mData;
    private Context context;

    public NotificationAdapter(List<Item> data, Context context) {
        mData = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
            return new ItemNotification(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemNotification) {

            Item item = mData.get(position);

            if(position == 0 || position == 1) {
                ((ItemNotification) holder).content.setBackground(context.getResources().getDrawable(R.color.background));
            } else {
                ((ItemNotification) holder).content.setBackground(context.getResources().getDrawable(android.R.color.white));
            }

            if(item.getStatusCod() == 0) {
                ((ItemNotification) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.document));
            } else if(item.getStatusCod() == 1) {
                ((ItemNotification) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.hourglass));
            } else {
                ((ItemNotification) holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.check));
            }

            ((ItemNotification) holder).description.setText(Html.fromHtml(context.getResources().getString(R.string.notification_message, item.getCategory(), item.getAddress(), item.getStatus())));


            ((ItemNotification) holder).date.setText(item.getDateShort().replace("de 2016", ""));
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

    private Item getItem(int position) {
        return mData.get(position);
    }

    static class ItemNotification extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.description)
        TextView description;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.content)
        RelativeLayout content;

        ItemNotification(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}