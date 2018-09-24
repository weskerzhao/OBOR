package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * 企业列表DTO
 */

public class CompanyDetaiDTO implements Serializable {
    //公司企业id
    private String company_id;
    //行业id
    private String industry;
    //行业名
    private String industryName;
    //公司名
    private String company_name;
    //公司展示图地址
    private String img_company;
    //联系地址
    private String address;

    //企业简介
    private String content;
    //邮箱
    private String mail;
    //联系人
    private String contacts;
    //联系电话
    private String phone;
    //关联用户id
    private String cid;
    //企业logo展示
    private String img_logo;
    //认证状态 0未认证 1审核 2认证通过
    private String business_state;
    //营业执照 正面照片
    private String img_business;
    //行业资格 照片
    private String img_industry;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getImg_company() {
        return img_company;
    }

    public void setImg_company(String img_company) {
        this.img_company = img_company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getImg_logo() {
        return img_logo;
    }

    public void setImg_logo(String img_logo) {
        this.img_logo = img_logo;
    }

    public String getBusiness_state() {
        return business_state;
    }

    public void setBusiness_state(String business_state) {
        this.business_state = business_state;
    }

    public String getImg_business() {
        return img_business;
    }

    public void setImg_business(String img_business) {
        this.img_business = img_business;
    }

    public String getImg_industry() {
        return img_industry;
    }

    public void setImg_industry(String img_industry) {
        this.img_industry = img_industry;
    }

    @Override
    public String toString() {
        return "CompanyDetaiDTO{" +
                "company_id='" + company_id + '\'' +
                ", industry='" + industry + '\'' +
                ", industryName='" + industryName + '\'' +
                ", company_name='" + company_name + '\'' +
                ", img_company='" + img_company + '\'' +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                ", mail='" + mail + '\'' +
                ", contacts='" + contacts + '\'' +
                ", phone='" + phone + '\'' +
                ", cid='" + cid + '\'' +
                ", img_logo='" + img_logo + '\'' +
                ", business_state='" + business_state + '\'' +
                ", img_business='" + img_business + '\'' +
                ", img_industry='" + img_industry + '\'' +
                '}';
    }
}
