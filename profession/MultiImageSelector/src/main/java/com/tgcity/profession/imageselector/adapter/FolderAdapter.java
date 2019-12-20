package com.tgcity.profession.imageselector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tgcity.profession.imageselector.bean.FolderBean;
import com.tgcity.profession.multiimageselector.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹Adapter
 *
 * @author Nereo
 * @date 2015/4/7
 * Updated by nereo on 2016/1/19.
 */
public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<FolderBean> mFolderBeans = new ArrayList<>();
    private int lastSelected = 0;

    public FolderAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 设置数据集
     */
    public void setData(List<FolderBean> folderBeans) {
        if (folderBeans != null && folderBeans.size() > 0) {
            mFolderBeans = folderBeans;
        } else {
            mFolderBeans.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolderBeans.size() + 1;
    }

    @Override
    public FolderBean getItem(int i) {
        if (i == 0) {
            return null;
        }
        return mFolderBeans.get(i - 1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.mis_list_item_folder, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null) {
            if (i == 0) {
                holder.name.setText(R.string.mis_folder_all);
                holder.path.setText("/sdcard");
                holder.size.setText(String.format("%d%s", getTotalImageSize(), mContext.getResources().getString(R.string.mis_photo_unit)));
                if (mFolderBeans.size() > 0) {
                    FolderBean folderBean = mFolderBeans.get(0);
                    if (folderBean != null) {
                       /* Picasso.with(mContext)
                                .load(new File(folderBean.getCover().getPath()))
                                .error(R.drawable.mis_default_error)
                                .resizeDimen(R.dimen.mis_folder_cover_size, R.dimen.mis_folder_cover_size)
                                .centerCrop()
                                .into(holder.cover);*/

                        Glide.with(mContext)
                                .load(new File(folderBean.getCover().getPath()))
                                .error(R.drawable.mis_default_error)
                                .centerCrop()
                                .into(holder.cover);
                    } else {
                        holder.cover.setImageResource(R.drawable.mis_default_error);
                    }
                }
            } else {
                holder.bindData(getItem(i));
            }
            if (lastSelected == i) {
                holder.indicator.setVisibility(View.VISIBLE);
            } else {
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    private int getTotalImageSize() {
        int result = 0;
        if (mFolderBeans != null && mFolderBeans.size() > 0) {
            for (FolderBean folderBean : mFolderBeans) {
                result += folderBean.getImageBeans().size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if (lastSelected == i) {
            return;
        }

        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    class ViewHolder {
        ImageView cover;
        TextView name;
        TextView path;
        TextView size;
        ImageView indicator;

        ViewHolder(View view) {
            cover = view.findViewById(R.id.cover);
            name = view.findViewById(R.id.name);
            path = view.findViewById(R.id.path);
            size = view.findViewById(R.id.size);
            indicator = view.findViewById(R.id.indicator);
            view.setTag(this);
        }

        void bindData(FolderBean data) {
            if (data == null) {
                return;
            }
            name.setText(data.getName());
            path.setText(data.getPath());
            if (data.getImageBeans() != null) {
                size.setText(String.format("%d%s", data.getImageBeans().size(), mContext.getResources().getString(R.string.mis_photo_unit)));
            } else {
                size.setText("*" + mContext.getResources().getString(R.string.mis_photo_unit));
            }
            if (data.getCover() != null) {
                // 显示图片
                Glide.with(mContext)
                        .load(new File(data.getCover().getPath()))
                        .placeholder(R.drawable.mis_default_error)
//                        .resizeDimen(R.dimen.mis_folder_cover_size, R.dimen.mis_folder_cover_size)
                        .centerCrop()
                        .into(cover);
            } else {
                cover.setImageResource(R.drawable.mis_default_error);
            }
        }
    }

}
