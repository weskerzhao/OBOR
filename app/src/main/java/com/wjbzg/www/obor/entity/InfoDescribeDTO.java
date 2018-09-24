package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * purpose 资讯说明/最新资讯
 */

public class InfoDescribeDTO implements Serializable{
    //资讯id
    private String info_id;
    //标题
    private String title;
    //展示图片地址
    private String img;
    //短评
    private String short_comment;
    //时间
    private String time;

    public String getInfo_id() {
        return info_id;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShort_comment() {
        return short_comment;
    }

    public void setShort_comment(String short_comment) {
        this.short_comment = short_comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "InfoDescribeDTO{" +
                "info_id='" + info_id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", short_comment='" + short_comment + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
