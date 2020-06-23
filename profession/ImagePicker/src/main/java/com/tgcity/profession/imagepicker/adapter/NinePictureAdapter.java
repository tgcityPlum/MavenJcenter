package com.tgcity.profession.imagepicker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tgcity.profession.imagepicker.R;
import com.tgcity.profession.imagepicker.bean.ImageItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jeremy
 * @date 2019/5/30
 * @des
 */
public class NinePictureAdapter extends BaseAdapter {
    private List<ImageItem> itemList;
    private WeakReference<Activity> activity;
    private int limiteSize = 9;

    public NinePictureAdapter(Activity activity, List<ImageItem> itemList) {
        this.itemList = itemList;
        this.activity = new WeakReference<>(activity);
        if (this.itemList == null) {
            this.itemList = new ArrayList<>();
        }
    }

    public void setLimiteSize(int limiteSize) {
        this.limiteSize = limiteSize;
    }

    @Override
    public int getCount() {
        if (itemList.size() >= limiteSize) {
            return limiteSize;
        }
        return itemList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PictureViewHolder holder;
        if (convertView == null) {
            holder = new PictureViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_circle_post_picture_item, parent, false);
            holder.ivPic = convertView.findViewById(R.id.iv_pic);
            holder.ivDelete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (PictureViewHolder) convertView.getTag();
        }

        holder.ivDelete.setVisibility(View.VISIBLE);

        if (position < itemList.size()) {
            Glide.with(convertView.getContext())
                    .load(itemList.get(position).path)
                    .placeholder(R.color.color_EEEEEE)
                    .error(R.color.color_EEEEEE)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivPic);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
            Glide.with(convertView.getContext())
                    .load("")
                    .placeholder(R.mipmap.add_4)
                    .error(R.mipmap.add_4)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivPic);
        }

        if (position >= limiteSize) {
            holder.ivPic.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
        }

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPictureDeleteListener != null) {
                    onPictureDeleteListener.onPictureDelete(itemList.get(position));
                }
                if(itemList.size() > position){
                    itemList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    public class PictureViewHolder {
        ImageView ivPic;
        ImageView ivDelete;
    }

    public interface OnPictureDeleteListener {
        void onPictureDelete(ImageItem imageItem);
    }

    private OnPictureDeleteListener onPictureDeleteListener;

    public void setOnPictureDeleteListener(OnPictureDeleteListener onPictureDeleteListener) {
        this.onPictureDeleteListener = onPictureDeleteListener;
    }
}
