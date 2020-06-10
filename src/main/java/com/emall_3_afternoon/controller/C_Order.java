package com.emall_3_afternoon.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emall_3_afternoon.entity.*;
import com.emall_3_afternoon.service.S_Address_Info;
import com.emall_3_afternoon.service.S_Goods_Info;
import com.emall_3_afternoon.service.S_Order;
import com.emall_3_afternoon.service.S_Store_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class C_Order {
    @Autowired
    private S_Order s_order;
    @Autowired
    private S_Goods_Info s_goods_info;
    @Autowired
    private S_Store_Info s_store_info;
    @Autowired
    private S_Address_Info s_address_info;

    @RequestMapping("go_to_order_forms")
    public String getOrderForms(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Goods_Item goods_item = new Goods_Item();
        int goods_id = Integer.parseInt(request.getParameter("goods_id"));
        int goods_sum = Integer.parseInt(request.getParameter("goods_sum"));
        //假定程序能走如这个请求，则session没有过期。如果过期，在过滤器/拦截器中已经判断，并返回到login界面
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        List address_info_list = s_address_info.getAddressList(b_s_id);
        Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);
        goods_info.setGoods_id(goods_id);
        goods_item.setGoods_info(goods_info); //设置商品
        goods_item.setAccount(goods_sum);
        goods_item.setGoods_money(goods_sum * goods_info.getGoods_actual_price());
        Store_Info store_info = s_store_info.getStoreInfo(goods_info.getStore_id());
        model.addAttribute("addr", address_info_list);
        model.addAttribute("goods", goods_item);
        model.addAttribute("stores", store_info);
        return "cart_order_form";
    }

    @RequestMapping("go_to_cart_order_form")
    public String getCartForm(HttpServletRequest request, Model model, @RequestParam("json") String str) {
        HttpSession session = request.getSession();
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        JSONObject object = JSON.parseObject(str);
        String json = object.getString("json");
        JSONObject arrays = JSON.parseObject(json);
        System.out.println(json);
        JSONArray ids = arrays.getJSONArray("id");
        JSONArray sums = arrays.getJSONArray("sum");
        System.out.println(ids.toJSONString());
        System.out.println(sums.toJSONString());
        int[] id = new int[ids.size()];
        int[] sum = new int[sums.size()];
        jsonToArrays(id, sum, ids, sums);
        List<Order_Info> order_infoList = initMix(id, sum, b_s_id);
        List address_info_list = s_address_info.getAddressList(b_s_id);
        double money = 0.0;
        for (Order_Info info : order_infoList) {
            money += info.getOrder_money();
        }
        model.addAttribute("orders", order_infoList);
        model.addAttribute("addrs", address_info_list);
        model.addAttribute("money", money);
        return "cart_order_form_mix";
    }

    public List<Order_Info> initMix(int[] id, int sum[], int b_s_id) {
        int store_id = -1;
        int store_id_new = 0;
        int store_index = 0;
        double sum_store = 0.0;
        List<Order_Info> order_infoList = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {

            int good_id = id[i];
            Goods_Info goods_info = s_goods_info.getAGoods_Info(good_id);

            //order_item_info
            Order_Item_Info order_item_info = new Order_Item_Info();
            Date now = new Date();//调用时间函数生成
            order_item_info.setOrder_item_time(now); //order_time
            order_item_info.setGoods_id(good_id); //good_id
            order_item_info.setGoods_name(goods_info.getGoods_name());
            order_item_info.setGoods_actual_price(goods_info.getGoods_actual_price());
            order_item_info.setGoods_sum(sum[i]); //good_count
            order_item_info.setGoods_money(goods_info.getGoods_actual_price() * sum[i]); //good_sum_money
            System.out.println(goods_info.toString());
            order_item_info.setGoods_property(goods_info.getGoods_describe()); //good_describe
            System.out.println(order_item_info.getGoods_property());
            order_item_info.setGoods_photo_path(goods_info.getGoods_photo_path_infoList().get(0).getPath_name());
            //判断是否相同店铺
            store_id_new = goods_info.getStore_id();
            //不相同
            if (store_id_new != store_id) {
                //order_info
                sum_store = 0.0;
                store_id = goods_info.getStore_id();
                Order_Info order_info = new Order_Info();
                order_info.setStore_id(store_id); //store_id
                Store_Info store_info = s_store_info.getStoreInfo(store_id);
                order_info.setStore_name(store_info.getStore_name());
                double goods_money = order_item_info.getGoods_money();
                sum_store += goods_money;
                order_info.setOrder_money((float) sum_store); //good_money
                String order_no = getOrderNo();
                order_info.setOrder_no(order_no); //order_no
                Date order_time = new Date();//调用时间函数生成
                order_info.setOrder_time(order_time); //order_time
                order_info.setOrder_status(0); //order_status
                order_info.setPay_way("支付宝"); //pay_way
                order_info.setPay_status(0); //pay_status
                order_info.setPay_time(order_time); //pay_time
                store_id = store_id_new;
                store_id_new = -1;
                order_info.setB_s_id(b_s_id);
                order_info.getOrder_item_infoList().add(order_item_info);
                order_infoList.add(order_info);
                store_index = order_infoList.size() - 1;
            } else {
                //相同店铺
                double goods_money = order_item_info.getGoods_money();
                sum_store += goods_money;
                order_infoList.get(store_index).setOrder_money((float) sum_store);
                order_infoList.get(store_index).getOrder_item_infoList().add(order_item_info);
                store_index = order_infoList.size() - 1;
            }
        }
        return order_infoList;
    }

    @RequestMapping(value = "get_order_list_by_b_s_id", method = RequestMethod.GET)
    public String getOrderListByB_s_id(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        //写死了后面请同学们自己改好
//        int b_s_id = 1;
        List<Order_Info> order_infoList = s_order.getOrder_InfoList(b_s_id);
        for (Order_Info orderInfo : order_infoList) {
            Store_Info store_info = s_store_info.getStoreInfo(orderInfo.getStore_id());
            orderInfo.setStore_name(store_info.getStore_name());
            for (Order_Item_Info o : orderInfo.getOrder_item_infoList()) {
                int goods_id = o.getGoods_id();
                Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);
                o.setGoods_name(goods_info.getGoods_name());
                o.setGoods_photo_path(goods_info.getGoods_photo_path_infoList().get(0).getPath_name());
                o.setGoods_actual_price(goods_info.getGoods_actual_price());
            }
        }
        model.addAttribute("order_info_list", order_infoList);
        return "order_info_list";
    }

    @RequestMapping(value = "carts_to_order", method = RequestMethod.POST)
    public String cartsToOrder(HttpServletRequest request, RedirectAttributes attributes, @RequestParam("json") String str) {
        HttpSession session = request.getSession();
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        Logistics_Info logistics_info = initLogistics_Info(request);
        JSONObject object = JSON.parseObject(str);
        String json = object.getString("json");
        JSONObject arrays = JSON.parseObject(json);
        System.out.println(json);
        JSONArray ids = arrays.getJSONArray("id");
        JSONArray sums = arrays.getJSONArray("sum");
        System.out.println(ids.toJSONString());
        System.out.println(sums.toJSONString());
        int[] id = new int[ids.size()];
        int[] sum = new int[sums.size()];
        jsonToArrays(id, sum, ids, sums);
        System.out.println(Arrays.toString(id));
        System.out.println(Arrays.toString(sum));
        List<Order_Info> order_infoList = initMix(id, sum, b_s_id);
        s_order.cartToOrder(order_infoList, logistics_info);
        attributes.addAttribute("b_s_id", b_s_id);
        return "redirect:get_order_list_by_b_s_id";
    }

    @RequestMapping(value = "goods_to_order", method = RequestMethod.POST)
    public String goodsToOrder(HttpServletRequest request, RedirectAttributes attributes) {
        HttpSession session = request.getSession();
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        Order_Item_Info order_item_info = initOrder_Item_Info(request);
        Order_Info order_info = initOrder_Info(request, b_s_id);
        Logistics_Info logistics_info = initLogistics_Info(request);
        int flag = 0;
        flag = s_order.goodsToOrder(order_info, order_item_info, logistics_info);
        if (flag == 0)
            return "error";
        //以下二行代码用于重定向，并带参数传递
        attributes.addAttribute("b_s_id", b_s_id);
        return "redirect:get_order_list_by_b_s_id";
    }

    @RequestMapping(value = "cart_to_order", method = RequestMethod.POST)
    public String cartToOrder(HttpServletRequest request, RedirectAttributes attributes) {
        int b_s_id = 1;
        Logistics_Info logistics_info = initLogistics_Info(request);
        int flag = 0;
//        flag = s_order.goodsToOrder(order_info, order_item_info, logistics_info);
        if (flag == 0)
            return "error";
        //以下二行代码用于重定向，并带参数传递
        attributes.addAttribute("b_s_id", b_s_id);
        return "redirect:get_order_list_by_b_s_id";
    }

    public void jsonToArrays(int[] id, int[] sum, JSONArray ids, JSONArray sums) {
        for (int i = 0; i < ids.size(); i++) {
            id[i] = Integer.parseInt(ids.get(i).toString());
        }
        for (int j = 0; j < sums.size(); j++) {
            sum[j] = Integer.parseInt(sums.get(j).toString());
        }
    }

    public Order_Info initOrder_Info(HttpServletRequest request, int b_s_id) {
        Order_Info order_info = new Order_Info();
        order_info.setStore_id(Integer.parseInt(request.getParameter("store_id"))); //store_id
        order_info.setOrder_money(Float.parseFloat(request.getParameter("order_money"))); //good_money
        String order_no = getOrderNo();
        order_info.setOrder_no(order_no); //order_no
        Date now = new Date();//调用时间函数生成
        order_info.setOrder_time(now); //order_time
        order_info.setOrder_status(0); //order_status
        order_info.setPay_way("支付宝"); //pay_way
        order_info.setPay_status(0); //pay_status
        order_info.setPay_time(now); //pay_time
        order_info.setB_s_id(b_s_id);
        return order_info;
    }

    public String getOrderNo() {
        String time = System.currentTimeMillis() / 1000 + "";
        String[] letter = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String randomLetter = letter[getRandom(25)];
        return time + randomLetter;
    }

    private int getRandom(int n) {
        // 0<= random and random < n
        Random ran = new Random();
        int random = ran.nextInt(n);
        return random;
    }

    public Order_Item_Info initOrder_Item_Info(HttpServletRequest request) {
        Order_Item_Info order_item_info = new Order_Item_Info();
        Date now = new Date();//调用时间函数生成
        order_item_info.setOrder_item_time(now); //order_time
        order_item_info.setGoods_id(Integer.parseInt(request.getParameter("goods_id"))); //good_id
        int good_id = order_item_info.getGoods_id();
        order_item_info.setGoods_sum(Integer.parseInt(request.getParameter("goods_sum"))); //good_count
        order_item_info.setGoods_money(Float.parseFloat(request.getParameter("goods_money"))); //good_sum_money
        Goods_Info goods_info = s_goods_info.getAGoods_Info(good_id);
        System.out.println(goods_info.toString());
        order_item_info.setGoods_property(goods_info.getGoods_describe()); //good_describe
        System.out.println(order_item_info.getGoods_property());
        return order_item_info;
    }

    public Logistics_Info initLogistics_Info(HttpServletRequest request) {
        int log_id = Integer.parseInt(request.getParameter("log_id")); //addr_id
        Logistics_Info logistics_info = new Logistics_Info();
        logistics_info.setGoods_address("大龙驹坞");//不应该从界面获取，而应该从卖家那边获取。在此设计中并没有什么意义.
        Address_Info address_info = s_address_info.getAddress(log_id);
        logistics_info.setLink_man(address_info.getAddresssee());
        logistics_info.setLink_telephone(address_info.getTelephone());
        logistics_info.setAddress_1(address_info.getAddress());
        logistics_info.setAddress_detail(address_info.getAddress_detail());
        logistics_info.setLogistics_status(0);
        return logistics_info;
    }
}
