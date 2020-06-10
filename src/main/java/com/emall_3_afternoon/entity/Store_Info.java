package com.emall_3_afternoon.entity;

public class Store_Info {
    private int store_id;
    private String store_name;
    private String store_describe;
    private int b_s_id;
    private int link_man_id;
    private int credit;

    @Override
    public String toString() {
        return "Store_Info{" +
                "store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                ", store_describe='" + store_describe + '\'' +
                ", b_s_id=" + b_s_id +
                ", link_man_id=" + link_man_id +
                ", credit=" + credit +
                '}';
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_describe() {
        return store_describe;
    }

    public void setStore_describe(String store_describe) {
        this.store_describe = store_describe;
    }

    public int getB_s_id() {
        return b_s_id;
    }

    public void setB_s_id(int b_s_id) {
        this.b_s_id = b_s_id;
    }

    public int getLink_man_id() {
        return link_man_id;
    }

    public void setLink_man_id(int link_man_id) {
        this.link_man_id = link_man_id;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
