package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\8\30 0030.
 */

public class BaseUserInfo implements Serializable {
    //行业名
    private String industryName;
    //行业id
    private String industry;
    //企业名
    private String company_name;
    //地址
    private String address;
    //联系电话
    private String phone;
    //联系油箱
    private String mail;
    //消息数 未读
    private String msg_num;
    //头像地址
    private String head_img;
    //联系人
    private String contacts;
    //认证状态 0未认证 1审核 2认证通过
    private String business_state;
    //企业简介
    private String content;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMsg_num() {
        return msg_num;
    }

    public void setMsg_num(String msg_num) {
        this.msg_num = msg_num;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getBusiness_state() {
        return business_state;
    }

    public void setBusiness_state(String business_state) {
        this.business_state = business_state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseUserInfo{" +
                "industryName='" + industryName + '\'' +
                ", industry='" + industry + '\'' +
                ", company_name='" + company_name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", msg_num='" + msg_num + '\'' +
                ", head_img='" + head_img + '\'' +
                ", contacts='" + contacts + '\'' +
                ", business_state='" + business_state + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
