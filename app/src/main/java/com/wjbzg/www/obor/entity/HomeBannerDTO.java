package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * 首页轮播图
 */

public class HomeBannerDTO implements Serializable {
    //跳转url 备用
    private String url;
    //广告展示图片地址
    private String img;
    //标题
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HomeBannerDTO{" +
                "url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
