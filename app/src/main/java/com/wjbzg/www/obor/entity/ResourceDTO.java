package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * 资源
 */

public class ResourceDTO implements Serializable{
    //需求表id
    private String rid;
    //需求分类id
    private String type_id;
    //需求分类名
    private String typeName;
    //需求标题名
    private String title;
    //区域id
    private String	region;
    //区域名
    private String regionName;
    //截止时间
    private String endTime;
    //联系电话
    private String provide;
    //我的提供
    private String phone;
    //我的需求
    private String need;
    //合作详情描述
    private String content;
    //添加时间
    private String time;
    //0审核中 1审核通过
    private String state;
    //用户上传图片地址 1
    private String img1;
    //用户上传图片地址 2
    private String img2;
    //用户上传图片地址 3
    private String img3;
    //所属企业公司id
    private String company_id;

    public String getRid() {
        return rid;
    }

    public void setRid(String nid) {
        this.rid = nid;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProvide() {
        return provide;
    }

    public void setProvide(String provide) {
        this.provide = provide;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "ResourceDTO{" +
                "nid='" + rid + '\'' +
                ", type_id='" + type_id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", title='" + title + '\'' +
                ", region='" + region + '\'' +
                ", regionName='" + regionName + '\'' +
                ", endTime='" + endTime + '\'' +
                ", provide='" + provide + '\'' +
                ", phone='" + phone + '\'' +
                ", need='" + need + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", img3='" + img3 + '\'' +
                ", company_id='" + company_id + '\'' +
                '}';
    }
}
