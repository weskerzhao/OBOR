package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * 行业下拉列表
 */

public class IndustryDTO implements Serializable {

    //行业id
    private String industry_id;
    //行业名
    private String industryName;
    //序列号
    private String seq;
    //
    private String nums;

    public String getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(String industry_id) {
        this.industry_id = industry_id;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    @Override
    public String toString() {
        return "industryDTO{" +
                "industry_id='" + industry_id + '\'' +
                ", industryName='" + industryName + '\'' +
                ", seq='" + seq + '\'' +
                ", nums='" + nums + '\'' +
                '}';
    }
}
