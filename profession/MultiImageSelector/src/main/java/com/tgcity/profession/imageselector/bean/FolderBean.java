package com.tgcity.profession.imageselector.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * 文件夹
 *
 * @author Nereo
 * @date 2015/4/7
 */
public class FolderBean {
    private String name;
    private String path;
    private ImageBean cover;
    private List<ImageBean> imageBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ImageBean getCover() {
        return cover;
    }

    public void setCover(ImageBean cover) {
        this.cover = cover;
    }

    public List<ImageBean> getImageBeans() {
        return imageBeans;
    }

    public void setImageBeans(List<ImageBean> imageBeans) {
        this.imageBeans = imageBeans;
    }

    @Override
    public boolean equals(Object o) {
        try {
            FolderBean other = (FolderBean) o;
            return TextUtils.equals(other.path, path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
