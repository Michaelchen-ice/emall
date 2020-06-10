package com.emall_3_afternoon.entity;

public class User {

    private String tel;
    private String pwd;

    public User(String tel, String pwd) {
        this.tel = tel;
        this.pwd = pwd;
    }

    public User() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "tel='" + tel + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
