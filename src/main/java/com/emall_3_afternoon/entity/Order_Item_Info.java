package com.emall_3_afternoon.entity;

import java.util.Date;

public class Order_Item_Info {
    private int order_item_id;
    private int order_id;
    private Date order_item_time;
    private int goods_id;
    private String goods_name;
    private int goods_sum;
    private float goods_actual_price;
    private float goods_money;
    private String goods_property;
    private String goods_photo_path;

    public float getGoods_actual_price() {
        return goods_actual_price;
    }

    public void setGoods_actual_price(float goods_actual_price) {
        this.goods_actual_price = goods_actual_price;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_photo_path() {
        return goods_photo_path;
    }

    public void setGoods_photo_path(String goods_photo_path) {
        this.goods_photo_path = goods_photo_path;
    }

    public int getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getOrder_item_time() {
        return order_item_time;
    }

    public void setOrder_item_time(Date order_item_time) {
        this.order_item_time = order_item_time;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_sum() {
        return goods_sum;
    }

    public void setGoods_sum(int goods_sum) {
        this.goods_sum = goods_sum;
    }

    public float getGoods_money() {
        return goods_money;
    }

    public void setGoods_money(float goods_money) {
        this.goods_money = goods_money;
    }

    public String getGoods_property() {
        return goods_property;
    }

    public void setGoods_property(String goods_property) {
        this.goods_property = goods_property;
    }
}