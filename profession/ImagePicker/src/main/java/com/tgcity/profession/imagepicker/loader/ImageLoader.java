package com.tgcity.profession.imagepicker.loader;

import android.app.Activity;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * @author TGCity
 */
public interface ImageLoader extends Serializable {

    void displayImage(Activity activity, String path, ImageView imageView, int width, int height);

    void clearMemoryCache(Activity activity);
}
