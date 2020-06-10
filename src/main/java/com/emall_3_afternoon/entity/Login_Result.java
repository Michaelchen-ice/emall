package com.emall_3_afternoon.entity;

public class Login_Result {
    private int result;
    private int b_s_id;
    private String nickname;

    public Login_Result(int result, int b_s_id, String nickname) {
        this.result = result;
        this.b_s_id = b_s_id;
        this.nickname = nickname;
    }

    public Login_Result() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getB_s_id() {
        return b_s_id;
    }

    public void setB_s_id(int b_s_id) {
        this.b_s_id = b_s_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Login_Result{" +
                "result=" + result +
                ", b_s_id=" + b_s_id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
