package com.tgcity.profession.imageselector.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tgcity.profession.imageselector.bean.ImageBean;
import com.tgcity.profession.multiimageselector.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片Adapter
 *
 * @author Nereo
 * @date 2015/4/7
 * Updated by nereo on 2016/1/19.
 */
public class ImageGridAdapter extends BaseAdapter {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private LayoutInflater mInflater;
    private boolean showCamera;
    private boolean showSelectIndicator = true;

    private List<ImageBean> mImageBeans = new ArrayList<>();
    private List<ImageBean> mSelectedImageBeans = new ArrayList<>();

    private final int mGridWidth;

    public ImageGridAdapter(Context context, boolean showCamera, int column) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width;
        Point size = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getSize(size);
        }
        width = size.x;
        mGridWidth = width / column;
    }

    /**
     * 显示选择指示器
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b) {
        if (showCamera == b) {
            return;
        }

        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     */
    public void select(ImageBean imageBean) {
        if (mSelectedImageBeans.contains(imageBean)) {
            mSelectedImageBeans.remove(imageBean);
        } else {
            mSelectedImageBeans.add(imageBean);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for (String path : resultList) {
            ImageBean imageBean = getImageByPath(path);
            if (imageBean != null) {
                mSelectedImageBeans.add(imageBean);
            }
        }
        if (mSelectedImageBeans.size() > 0) {
            notifyDataSetChanged();
        }
    }

    private ImageBean getImageByPath(String path) {
        if (mImageBeans != null && mImageBeans.size() > 0) {
            for (ImageBean imageBean : mImageBeans) {
                if (imageBean.getPath().equalsIgnoreCase(path)) {
                    return imageBean;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     */
    public void setData(List<ImageBean> imageBeans) {
        mSelectedImageBeans.clear();

        if (imageBeans != null && imageBeans.size() > 0) {
            mImageBeans = imageBeans;
        } else {
            mImageBeans.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera) {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImageBeans.size() + 1 : mImageBeans.size();
    }

    @Override
    public ImageBean getItem(int i) {
        if (showCamera) {
            if (i == 0) {
                return null;
            }
            return mImageBeans.get(i - 1);
        } else {
            return mImageBeans.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (isShowCamera()) {
            if (i == 0) {
                view = mInflater.inflate(R.layout.mis_list_item_camera, viewGroup, false);
                return view;
            }
        }

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.mis_list_item_image, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (holder != null) {
            holder.bindData(getItem(i));
        }

        return view;
    }

    class ViewHolder {
        ImageView image;
        ImageView indicator;
        View mask;

        ViewHolder(View view) {
            image = view.findViewById(R.id.image);
            indicator = view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
            view.setTag(this);
        }

        void bindData(final ImageBean data) {
            if (data == null) {
                return;
            }
            // 处理单选和多选状态
            if (showSelectIndicator) {
                indicator.setVisibility(View.VISIBLE);
                if (mSelectedImageBeans.contains(data)) {
                    // 设置选中状态
                    indicator.setImageResource(R.drawable.mis_btn_selected);
                    mask.setVisibility(View.VISIBLE);
                } else {
                    // 未选择
                    indicator.setImageResource(R.drawable.mis_btn_unselected);
                    mask.setVisibility(View.GONE);
                }
            } else {
                indicator.setVisibility(View.GONE);
            }
            File imageFile = new File(data.getPath());
            if (imageFile.exists()) {
                // 显示图片
                Glide.with(mContext)
                        .load(imageFile)
                        .placeholder(R.drawable.mis_default_error)
//                        .tag(MultiImageSelectorFragment.TAG)
//                        .resize(mGridWidth, mGridWidth)
                        .centerCrop()
                        .into(image);
            } else {
                image.setImageResource(R.drawable.mis_default_error);
            }
        }
    }

}
