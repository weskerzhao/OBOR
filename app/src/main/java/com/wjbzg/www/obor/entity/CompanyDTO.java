package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * 企业列表DTO
 */

public class CompanyDTO implements Serializable {
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

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "company_id='" + company_id + '\'' +
                ", industry='" + industry + '\'' +
                ", industryName='" + industryName + '\'' +
                ", company_name='" + company_name + '\'' +
                ", img_company='" + img_company + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
