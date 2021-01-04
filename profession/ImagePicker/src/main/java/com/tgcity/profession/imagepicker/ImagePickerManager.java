package com.tgcity.profession.imagepicker;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tgcity.profession.imagepicker.loader.ImageLoader;
import com.tgcity.profession.imagepicker.view.CropImageView;

/**
 * @author TGCity
 */
public class ImagePickerManager {

    public static void init() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
                Glide.with(activity)                             //配置上下文
//                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .load(path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.mipmap.default_image)           //设置错误图片
                        .placeholder(R.mipmap.default_image)     //设置占位图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                        .into(imageView);
            }

            @Override
            public void clearMemoryCache(Activity activity) {
                Glide.get(activity).clearMemory();
            }
        });
        //显示拍照按钮
        imagePicker.setShowCamera(true);
        //允许裁剪（单选才有效）
        imagePicker.setCrop(false);
        //是否按矩形区域保存
        imagePicker.setSaveRectangle(true);
        //选中数量限制
        imagePicker.setSelectLimit(4);
        //裁剪框的形状
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusWidth(800);
        //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);
        int size = 1080;
        //保存文件的宽度。单位像素
        imagePicker.setOutPutX(size);
        //保存文件的高度。单位像素
        imagePicker.setOutPutY(size);
    }
}
