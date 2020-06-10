package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Goods_Info;
import com.emall_3_afternoon.entity.Goods_Property_Info;
import com.emall_3_afternoon.entity.Store_Info;
import com.emall_3_afternoon.mapper.Goods_Property_InfoMapper;
import com.emall_3_afternoon.service.S_Goods_Info;
import com.emall_3_afternoon.service.S_Store_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class C_Goods_Show {

    @Autowired
    private S_Goods_Info s_goods_info;
    @Autowired
    private S_Store_Info s_store_info;
    @Autowired
    private Goods_Property_InfoMapper goods_property_infoMapper;

    @GetMapping("goodsDetails/{good_id}")
    public String showGoods(@PathVariable int good_id, Model model) {
        Goods_Info goods_info = s_goods_info.getAGoods_Info(good_id);
        int store_id = goods_info.getStore_id();
        Store_Info store_info = s_store_info.getStoreInfo(store_id);
        goods_info.setGoods_id(good_id);
        System.out.println(goods_info.getGoods_photo_path_infoList().toString());
        System.out.println(goods_info.getGoods_property_infoList().toString());
        model.addAttribute("good", goods_info);
        model.addAttribute("store", store_info);
        return "shopdetail";
    }

    @GetMapping("showGood_index")
    public String showGoods_index(Model model) {
        int i = 0;
        List<Goods_Info> goods_List = new ArrayList<>();
        String[] store_List = new String[100];
        String[] good_Id = s_goods_info.getGoodIDs();
        for (String id : good_Id) {
            Goods_Info goods_info = s_goods_info.getAGoods_Info(Integer.parseInt(id));
            int store_id = goods_info.getStore_id();
            Store_Info store_info = s_store_info.getStoreInfo(store_id);
            goods_info.setGoods_id(Integer.parseInt(id));
            goods_info.setDictionary_code(store_info.getStore_name());
            goods_List.add(goods_info);
        }
        System.out.println(goods_List);
        model.addAttribute("goods", goods_List);
        return "shop_show";
    }
}
