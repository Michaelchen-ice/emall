package com.emall_3_afternoon.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.emall_3_afternoon.entity.BuyerCart;
import com.emall_3_afternoon.entity.Goods_Info;
import com.emall_3_afternoon.entity.Store_Info;
import com.emall_3_afternoon.entity.Store_Item;
import com.emall_3_afternoon.service.S_Goods_Info;
import com.emall_3_afternoon.service.S_Store_Info;
import com.emall_3_afternoon.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CBuyerCart {
    @Autowired
    private S_Goods_Info s_goods_info;
    @Autowired
    private S_Store_Info s_store_info;
    @Autowired
    private RedisUtil redis;//用来从redis里面获取value或者设置value。

    @RequestMapping(value = "show_cart", method = RequestMethod.GET)
    public String showCart(HttpServletRequest request, Model model) {
        //根据传入的用户id.不考虑jd模式的cookie。从redis获取购物车信息，并显示出来。
        //1:获取用户的id或者name。。可以从session里面获取。
        HttpSession session = request.getSession();
        String b_s_id = session.getAttribute("b_s_id").toString();
        //写死了,也可以通过用户名作为key去存储。但是要保证用户名是唯一的。
        BuyerCart buyerCart = this.getABuyerCart(b_s_id);

        //通过model传入到thymeleaf页面处理。
        model.addAttribute("cart_item_list", buyerCart.getStore_itemList());

        return "show_cart";
    }

    @RequestMapping(value = "get_cart_item_list", method = RequestMethod.GET)
    @ResponseBody
    public List<Store_Item> getCartItemList(HttpServletRequest request) {
        //根据传入的用户id.不考虑jd模式的cookie。从redis获取购物车信息，并显示出来。
        //1:获取用户的id或者name。。可以从session里面获取。
        HttpSession session = request.getSession();
        String b_s_id = session.getAttribute("b_s_id").toString();
        //写死了,也可以通过用户名作为key去存储。但是要保证用户名是唯一的。
        BuyerCart buyerCart = this.getABuyerCart(b_s_id);

        return buyerCart.getStore_itemList();
    }


    @RequestMapping(value = "add_cart", method = RequestMethod.POST)
    public String addCart(HttpServletRequest request, RedirectAttributes attributes) {
        int goods_id = Integer.parseInt(request.getParameter("goods_id"));
        int goods_sum = Integer.parseInt(request.getParameter("goods_sum"));
        //根据id，获取要插入到购物车的对象.这里不处理，全部交给购物车这个类去处理。控制器只负责初始化和调度
        //Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);

        //1:获取用户的id或者name。。可以从session里面获取。
        HttpSession session = request.getSession();
        String b_s_id = session.getAttribute("b_s_id").toString();
        //写死了,也可以通过用户名作为key去存储。但是要保证用户名是唯一的。
        BuyerCart buyerCart = this.getABuyerCart(b_s_id);
        //添加商品进入购物车.
        Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);
        int store_id = goods_info.getStore_id();
        Store_Info store_info = s_store_info.getStoreInfo(store_id);
        goods_info.setGoods_id(goods_id);
        buyerCart.addStore_Item(goods_info, store_info, goods_sum);
        //要保存回redis.其实就是序列化
        String fromObject = JSON.toJSONString(buyerCart);
        redis.set(b_s_id, fromObject.toString());

        //保存成功之后，跳转到购物车。
        attributes.addAttribute("b_s_id", b_s_id);

        return "redirect:/show_cart";
    }

    public BuyerCart getABuyerCart(String b_s_id) {
        String buyer_cart_value = redis.get(b_s_id);
        BuyerCart buyerCart = null;
        //从redis里面获取购物车,获取购物车也有2种情况，1：获取到了。2：获取不到.
        //如何去获取。在redis里面根据keyname:用户名,keyvalue:存储购物车信息。
        //从redis获取完之后。对keyvalue进行反序列化生成对象。
        if (buyer_cart_value == "" || buyer_cart_value.equals(null) || buyer_cart_value == null)
            buyerCart = new BuyerCart();
        else {//if表示没有值，else表示有值，所以对他进行反序列化。
            buyerCart = JSON.parseObject(buyer_cart_value,
                    new TypeReference<BuyerCart>() {
                    });
        }
        return buyerCart;
    }

    @RequestMapping(value = "add_cart_ajax", method = RequestMethod.POST)
    @ResponseBody
    public String addCartAjax(HttpServletRequest request) {
        int goods_id = Integer.parseInt(request.getParameter("goods_id"));
        int goods_sum = Integer.parseInt(request.getParameter("goods_sum"));
        //根据id，获取要插入到购物车的对象.这里不处理，全部交给购物车这个类去处理。控制器只负责初始化和调度
        //Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);

        //1:获取用户的id或者name。。可以从session里面获取。
        HttpSession session = request.getSession();
        String b_s_id = session.getAttribute("b_s_id").toString();
        //写死了,也可以通过用户名作为key去存储。但是要保证用户名是唯一的。
        BuyerCart buyerCart = this.getABuyerCart(b_s_id);
        //添加商品进入购物车.
        Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);
        int store_id = goods_info.getStore_id();
        Store_Info store_info = s_store_info.getStoreInfo(store_id);
        System.out.println(goods_info.toString());
        System.out.println(store_info.toString());
        goods_info.setGoods_id(goods_id);
        buyerCart.addStore_Item(goods_info, store_info, goods_sum);
        //要保存回redis.其实就是序列化
        String fromObject = JSON.toJSONString(buyerCart);
        redis.set(b_s_id, fromObject.toString());

        return "1";
    }

    @RequestMapping(value = "update_cart", method = RequestMethod.POST)
    @ResponseBody
    public String updateCart(HttpServletRequest request, @RequestBody String json) {
        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
        int goods_id = Integer.parseInt(jsonObject.get("good_id").toString());
        int goods_sum = Integer.parseInt(jsonObject.get("good_sum").toString());
        HttpSession session = request.getSession();
        String b_s_id = session.getAttribute("b_s_id").toString();
        //写死了,也可以通过用户名作为key去存储。但是要保证用户名是唯一的。
        BuyerCart buyerCart = this.getABuyerCart(b_s_id);
        //修改购物车里面的商品的数量和金额.
        Goods_Info goods_info = s_goods_info.getAGoods_Info(goods_id);
        int store_id = goods_info.getStore_id();
        Store_Info store_info = s_store_info.getStoreInfo(store_id);
        System.out.println(goods_info.toString());
        System.out.println(store_info.toString());
        goods_info.setGoods_id(goods_id);
        buyerCart.updateStore_Item(goods_info, store_info, goods_sum);
        //要保存回redis.其实就是序列化
        String fromObject = JSON.toJSONString(buyerCart);
        redis.set(b_s_id, fromObject.toString());

        return "1";
    }

    @RequestMapping(value = "delete_goods_in_cart/{goods_id}/{store_id}", method = RequestMethod.GET)
    public String deleteGoodsInCart(HttpServletRequest request, @PathVariable int goods_id, @PathVariable int store_id) {
        HttpSession session = request.getSession();
        String b_s_id = session.getAttribute("b_s_id").toString();
        //写死了,也可以通过用户名作为key去存储。但是要保证用户名是唯一的。
        BuyerCart buyerCart = this.getABuyerCart(b_s_id);
        //删除只需要2个参数
        buyerCart.deleteStore_Item(goods_id, store_id);
        //要保存回redis.其实就是序列化
        String fromObject = JSON.toJSONString(buyerCart);
        redis.set(b_s_id, fromObject.toString());

        return "redirect:/show_cart";

    }

}
