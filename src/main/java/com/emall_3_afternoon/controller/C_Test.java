package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Address_Info;
import com.emall_3_afternoon.entity.Goods_Info;
import com.emall_3_afternoon.entity.Goods_Photo_Path_Info;
import com.emall_3_afternoon.entity.Store_Info;
import com.emall_3_afternoon.mapper.Address_InfoMapper;
import com.emall_3_afternoon.mapper.Goods_Photo_Path_InfoMapper;
import com.emall_3_afternoon.service.S_Address_Info;
import com.emall_3_afternoon.service.S_Goods_Info;
import com.emall_3_afternoon.service.S_Store_Info;
import com.emall_3_afternoon.util.RedisUtil;
import com.emall_3_afternoon.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class C_Test {
    @Autowired
    private RedisUtil redis;
    @Autowired
    private Address_InfoMapper address_infoMapper;
    @Autowired
    private S_Goods_Info s_goods_info;
    @Autowired
    private S_Store_Info s_store_info;
    @Autowired
    private Goods_Photo_Path_InfoMapper goods_photo_path_infoMapper;
    @Autowired
    private S_Address_Info s_address_info;

    @RequestMapping("test_getAddressCount")
    @ResponseBody
    public int getAddressCount() {
        return address_infoMapper.getAddressCount(1);
    }

    @RequestMapping("test_redis")
    @ResponseBody
    public String testRedis() {
        RedisUtil redisUtil = (RedisUtil) SpringUtil.applicationContext.
                getBean("redisUtil");
        redisUtil.set("test_key_name", "value-1234567");
        return redisUtil.get("test_key_name");

    }

    @RequestMapping("test_S_goods_info")
    @ResponseBody
    public Goods_Info testGoodsInfo() {
        return s_goods_info.getAGoods_Info(1);
    }

    @RequestMapping("test_s_store_info")
    @ResponseBody
    public Store_Info testStoreInfo() {
        return s_store_info.getStoreInfo(1);
    }

    @RequestMapping("photoPath")
    @ResponseBody
    public List<Goods_Photo_Path_Info> getPhotoPath() {
        return goods_photo_path_infoMapper.getGoods_Photo_Path_List(1);
    }

    @RequestMapping("test_shop")
    public String shop() {
        return "shopdetail";
    }

    @RequestMapping("test_user_index")
    public String user_index() {
        return "user_index";
    }

    @RequestMapping("test_address")
    public String user_addr() {
        return "address";
    }


    @RequestMapping("getShop_index")
    public String shopppp() {
        return "shop_show";
    }

    @RequestMapping("getOrderInfo")
    public String orddder() {
        return "cart_order_form";
    }
}
