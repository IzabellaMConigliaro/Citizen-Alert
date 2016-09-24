package com.ihc.cefet.cidadealerta;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.mypopsy.maps.StaticMap;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RequestListener<StaticMap, GlideDrawable> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private List<Item> objects;
    private static final int EMPTY_VIEW = 10;

    private static Context mContext;
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private static int mShortAnimationDuration;

    public static FoldingCellListAdapter newInstance(Context context, List<Item> items) {
        FoldingCellListAdapter pa = new FoldingCellListAdapter(null);
        pa.setContext(context);
        return new FoldingCellListAdapter(items);
    }

    public void setContext(Context var) {
        mContext = var;
        mShortAnimationDuration = 150;

    }

    public FoldingCellListAdapter(List<Item> items){
        this.objects = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty_statement, parent, false);
            //return EmptyViewHolder.newInstance(view);
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return ViewHolder.newInstance(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case EMPTY_VIEW:
//                ((EmptyViewHolder) holder).textViewTryAgain.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((BaseActivity) context).finish();
//                    }
//                });
                break;

            default:
                final ViewHolder viewHolder = (ViewHolder) holder;
                final Item item = objects.get(position);

                if (unfoldedIndexes.contains(position)) {
                    viewHolder.cell_layout.unfold(true);
                } else {
                    viewHolder.cell_layout.fold(true);
                }

                // bind data from selected element to view through view holder
                viewHolder.category.setText(item.getCategory());
                viewHolder.address.setText(item.getAddress());
                viewHolder.date.setText(item.getDate());
                viewHolder.status.setText(item.getStatus());
                viewHolder.title.setText(item.getCategory());
                if (TextUtils.isEmpty(item.getDescription())) {
                    viewHolder.description_layout.setVisibility(View.GONE);
                } else {
                    viewHolder.description.setText(item.getDescription());
                    viewHolder.description_layout.setVisibility(View.VISIBLE);
                }
                if(item.getImage() != -1) {
                    Glide
                            .with(mContext)
                            .load(item.getImage())
                            .fitCenter()
                            .crossFade()
                            .into(viewHolder.image);

                    viewHolder.image.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.image.setVisibility(View.GONE);
                }
                String[] addressSplit = item.getAddress().split(" - ");
                try {
                    viewHolder.contentToAddress1.setText(addressSplit[0]);
                    viewHolder.contentToAddress2.setText(addressSplit[1]);
                } catch (Exception e) {

                }
                viewHolder.contentDeliveryTime.setText(item.getTime());
                viewHolder.contentDeliveryDate.setText(item.getDateShort());
                viewHolder.contentNameView.setText(item.getUser());
                if(item.getAvatar() != -1) {
                    viewHolder.content_avatar.setImageDrawable(mContext.getResources().getDrawable(item.getAvatar()));
                } else {
                    viewHolder.content_avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_user4));
                }
                viewHolder.favorites.setText(mContext.getString(R.string.favorites, item.getFavoriteCount()));

                if(item.getStatusCod() == 0) {
                    viewHolder.statusbar_view.setBackgroundColor(mContext.getResources().getColor(R.color.opened));
                } else if(item.getStatusCod() == 1) {
                    viewHolder.statusbar_view.setBackgroundColor(mContext.getResources().getColor(R.color.recognized));
                } else {
                    viewHolder.statusbar_view.setBackgroundColor(mContext.getResources().getColor(R.color.closed));
                }

                viewHolder.favoriteButton2.setFavorite(item.isFavorited());
                viewHolder.favoriteButton.setFavorite(item.isFavorited());

                viewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setFavorited(!item.isFavorited());

                        viewHolder.favoriteButton2.setFavorite(item.isFavorited());
                        viewHolder.favoriteButton.setFavorite(item.isFavorited());
                    }
                });

                viewHolder.favoriteButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setFavorited(!item.isFavorited());

                        viewHolder.favoriteButton2.setFavorite(item.isFavorited());
                        viewHolder.favoriteButton.setFavorite(item.isFavorited());
                    }
                });

                viewHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        zoomImageFromThumb(viewHolder.content, viewHolder.expanded_image, viewHolder.image, item.getImage());
                    }
                });

                Glide.with(mContext).load(new StaticMap().center(item.getLat() + "," + item.getLng()).size(320, 120).zoom(17)
                        .marker(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLng())))
                        //.placeholder(PLACEHOLDER)
                        //.error(R.drawable.frown_cloud)
                        .listener(this)
                        .into(viewHolder.map);

                viewHolder.map_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewHolder.google_map_layout.getVisibility() == View.GONE) {
                            expand(viewHolder.google_map_layout);
                        } else {
                            collapse(viewHolder.google_map_layout, false, null, 0);
                        }
                    }
                });

                viewHolder.map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strUri = "http://maps.google.com/maps?q=loc:" + item.getLat() + "," + item.getLng() + " (" + item.getCategory() + ")";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                        mContext.startActivity(intent);
                    }
                });

                viewHolder.cell_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (viewHolder.google_map_layout.getVisibility() == View.VISIBLE) {
                            collapse(viewHolder.google_map_layout, true, v, position);

                        } else {
                            ((FoldingCell) v).toggle(false);
                            // register in adapter that state for selected cell is toggled
                            ((FoldingCellListAdapter)((BaseActivity) mContext).getAdapter()).registerToggle(position);
                        }

                    }
                });


                // set custom btn handler for list item from that item
//        if (item.getRequestBtnClickListener() != null) {
//            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
//        } else {
//            // (optionally) add "default" handler if no handler found in item
//            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
//        }

                //viewHolder.favoriteButton.setFavorite(isFavorite(data.get(position)), false);

        }
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // get item for selected view
//        final Item item = getItem(position);
//        // if cell is exists - reuse it, if not - create the new one from resource
//        FoldingCell cell = (FoldingCell) convertView;
//        final ViewHolder viewHolder;
//        if (cell == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater vi = LayoutInflater.from(getContext());
//            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
//
//
//            // binding view parts to view holder
//            cell.setTag(viewHolder);
//        } else {
//            // for existing cell set valid valid state(without animation)
//            if (unfoldedIndexes.contains(position)) {
//                cell.unfold(true);
//            } else {
//                cell.fold(true);
//            }
//            viewHolder = (ViewHolder) cell.getTag();
//        }
//
//
//        return cell;
//    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    @Override
    public boolean onException(Exception e, StaticMap model, Target<GlideDrawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, StaticMap model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if(objects == null || objects.isEmpty()) {
            return EMPTY_VIEW;
        }

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return (objects != null && objects.size() > 0) ? objects.size() : 1;
    }

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addItem(List<Item> result){
        objects.addAll(result);
        notifyDataSetChanged();
    }


    // View lookup cache
    public static final class ViewHolder extends RecyclerView.ViewHolder {

        MaterialFavoriteButton favoriteButton;
        ImageView image;
        TextView description;
        TextView contentToAddress1;
        TextView contentToAddress2;
        TextView contentDeliveryTime;
        TextView contentDeliveryDate;
        TextView contentNameView;
        TextView favorites;
        ImageView statusbar_view;
        TextView category;
        MaterialFavoriteButton favoriteButton2;
        TextView status;
        TextView address;
        TextView date;
        TextView title;
        ImageView content_avatar;
        LinearLayout description_layout;
        ImageView expanded_image;
        LinearLayout content;
        ImageView map;
        LinearLayout map_layout;
        LinearLayout google_map_layout;
        FoldingCell cell_layout;

        static ViewHolder newInstance(View cell) {
            MaterialFavoriteButton favoriteButton = (MaterialFavoriteButton) cell.findViewById(R.id.favoriteButton);
            ImageView image = (ImageView) cell.findViewById(R.id.image);
            TextView description = (TextView) cell.findViewById(R.id.description);
            TextView  contentToAddress1 = (TextView) cell.findViewById(R.id.content_to_address_1);
            TextView contentToAddress2 = (TextView) cell.findViewById(R.id.content_to_address_2);
            TextView contentDeliveryTime = (TextView) cell.findViewById(R.id.content_delivery_time);
            TextView contentDeliveryDate = (TextView) cell.findViewById(R.id.content_delivery_date);
            TextView contentNameView = (TextView) cell.findViewById(R.id.content_name_view);
            TextView favorites = (TextView) cell.findViewById(R.id.favorites);
            ImageView statusbar_view = (ImageView) cell.findViewById(R.id.statusbar_view);
            TextView category = (TextView) cell.findViewById(R.id.category);
            MaterialFavoriteButton favoriteButton2 = (MaterialFavoriteButton) cell.findViewById(R.id.favoriteButton2);
            TextView status = (TextView) cell.findViewById(R.id.status);
            TextView address = (TextView) cell.findViewById(R.id.address);
            TextView date = (TextView) cell.findViewById(R.id.date);
            TextView title = (TextView) cell.findViewById(R.id.title);
            ImageView content_avatar = (ImageView) cell.findViewById(R.id.content_avatar);
            LinearLayout description_layout = (LinearLayout) cell.findViewById(R.id.description_layout);
            ImageView expanded_image = (ImageView) cell.findViewById(R.id.expanded_image);
            LinearLayout content = (LinearLayout) cell.findViewById(R.id.content);
            ImageView map = (ImageView) cell.findViewById(R.id.map);
            LinearLayout map_layout = (LinearLayout) cell.findViewById(R.id.map_layout);
            LinearLayout google_map_layout = (LinearLayout) cell.findViewById(R.id.google_map_layout);
            FoldingCell cell_layout = (FoldingCell) cell.findViewById(R.id.cell);

            return new ViewHolder(cell, favoriteButton, image, description, contentToAddress1,
                    contentToAddress2, contentDeliveryTime, contentDeliveryDate, contentNameView,
                    favorites, statusbar_view, category, favoriteButton2, status, address,
                    date, title, content_avatar, description_layout, expanded_image, content,
                    map, map_layout, google_map_layout, cell_layout);
        }

        public ViewHolder(final View itemView, MaterialFavoriteButton favoriteButton,
                          ImageView image, TextView description,
                          TextView contentToAddress1, TextView contentToAddress2,
                          TextView contentDeliveryTime, TextView contentDeliveryDate,
                          TextView contentNameView, TextView favorites,
                          ImageView statusbar_view, TextView category,
                          MaterialFavoriteButton favoriteButton2, TextView status,
                          TextView address, TextView date, TextView title,
                          ImageView content_avatar, LinearLayout description_layout,
                          ImageView expanded_image, LinearLayout content, ImageView map,
                          LinearLayout map_layout, LinearLayout google_map_layout,
                          FoldingCell cell_layout) {

            super(itemView);
            this.favoriteButton = favoriteButton;
            this.image = image;
            this.description = description;
            this.contentToAddress1 = contentToAddress1;
            this.contentToAddress2 = contentToAddress2;
            this.date = date;
            this.status = status;
            this.contentDeliveryTime = contentDeliveryTime;
            this.contentDeliveryDate = contentDeliveryDate;
            this.contentNameView = contentNameView;
            this.statusbar_view = statusbar_view;
            this.favorites = favorites;
            this.category = category;
            this.favoriteButton2 = favoriteButton2;
            this.address = address;
            this.title = title;
            this.content_avatar = content_avatar;
            this.description_layout = description_layout;
            this.expanded_image = expanded_image;
            this.content = content;
            this.map = map;
            this.map_layout = map_layout;
            this.google_map_layout = google_map_layout;
            this.cell_layout = cell_layout;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }

    private void zoomImageFromThumb(LinearLayout content, ImageView expanded_image, final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = expanded_image;
        Glide
                .with(mContext)
                .load(imageResId)
                .centerCrop()
                .crossFade()
                .into(expanded_image);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        content.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


    private void expand(LinearLayout map) {
        map.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        map.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, map.getMeasuredHeight(), map);
        mAnimator.start();
    }

    private void collapse(final LinearLayout map, final boolean fold, final View v, final int position) {
        int finalHeight = map.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0, map);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                map.setVisibility(View.GONE);

                if(fold) {
                    ((FoldingCell) v).toggle(false);
                    // register in adapter that state for selected cell is toggled
                    ((FoldingCellListAdapter)((BaseActivity) mContext).getAdapter()).registerToggle(position);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end, final LinearLayout map) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = map.getLayoutParams();
                layoutParams.height = value;
                map.setLayoutParams(layoutParams);
            }
        });

        return animator;
    }

}