package com.tgcity.profession.imageselector.bean;

import android.text.TextUtils;

/**
 * 图片实体
 *
 * @author Nereo
 * @date 2015/4/7
 */
public class ImageBean {
    private String path;
    private String name;
    private long time;

    public ImageBean(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            ImageBean other = (ImageBean) o;
            return TextUtils.equals(this.path, other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
