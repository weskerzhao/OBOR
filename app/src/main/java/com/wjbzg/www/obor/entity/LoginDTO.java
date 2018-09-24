package com.wjbzg.www.obor.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\8\29 0029.
 */

public class LoginDTO implements Serializable{

    //用户账号id
    private String cid;
    //用户账号 用户手机号
    private String phone;
    //状态
    private String state;
    //密码
    private String psd;
    //注册时间
    private String register_time;
    //最后登陆时间
    private String last_time;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }



    @Override
    public String toString() {
        return "LoginDTO{" +
                "cid='" + cid + '\'' +
                ", phone='" + phone + '\'' +
                ", state='" + state + '\'' +
                ", psd='" + psd + '\'' +
                ", register_time='" + register_time + '\'' +
                ", last_time='" + last_time + '\'' +
                '}';
    }


}
