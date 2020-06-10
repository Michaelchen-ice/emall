package com.emall_3_afternoon.service;

import com.emall_3_afternoon.entity.Address_Info;
import com.emall_3_afternoon.entity.Logistics_Info;
import com.emall_3_afternoon.entity.Order_Info;
import com.emall_3_afternoon.entity.Order_Item_Info;
import com.emall_3_afternoon.mapper.Address_InfoMapper;
import com.emall_3_afternoon.mapper.Logistics_InfoMapper;
import com.emall_3_afternoon.mapper.Order_InfoMapper;
import com.emall_3_afternoon.mapper.Order_Item_InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class S_Order {
    @Autowired
    private Order_InfoMapper order_infoMapper;
    @Autowired
    private Order_Item_InfoMapper order_itemMapper;
    @Autowired
    private Logistics_InfoMapper logistics_infoMapper;
    @Autowired
    private Address_InfoMapper address_infoMapper;

    public List<Order_Info> getOrder_InfoList(int b_s_id) {
        return order_infoMapper.getOrder_InfoList(b_s_id);
    }

    //插入数据到订单。一种是直接从商品到订单。一种是从购物车到订单
    //1:商品到订单。这是写的不好的典型案例，应该按2的标准设计实现
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int goodsToOrder(int goods_id, int store_id, int goods_sum, float goods_money, int b_s_id,
                            int address_id) {
        int flag = 0;
        //设置订单总表
        Order_Info order_info = new Order_Info();
        order_info.setB_s_id(b_s_id);
        order_info.setOrder_money(goods_money);
        order_info.setStore_id(store_id);
        Date now = new Date();//调用时间函数生成
        order_info.setOrder_time(now);

        //设置订单明细表
        Order_Item_Info order_item_info = new Order_Item_Info();
        order_item_info.setGoods_id(goods_id);
        order_item_info.setGoods_sum(goods_sum);
        order_item_info.setGoods_money(goods_money);
        order_item_info.setOrder_item_time(now);
        //设置物流信息
        Address_Info address_info = address_infoMapper.getAddress(address_id);
        Logistics_Info logistics_info = new Logistics_Info();
        logistics_info.setLink_man(address_info.getAddresssee());
        logistics_info.setLink_telephone(address_info.getTelephone());
        logistics_info.setAddress_1(address_info.getAddress());
        logistics_info.setAddress_detail(address_info.getAddress_detail());
        logistics_info.setLogistics_status(0);


        //分别insert order order_item 和logistics 3张表的数据
        flag = order_infoMapper.insertOrder_Info(order_info);
        //order_item 和logistics都需要order_info里面的order_id主键作为外键
        order_item_info.setOrder_id(order_info.getOrder_id());
        flag = order_itemMapper.insertOrder_Item_Info(order_item_info);
        //order_item 和logistics都需要order_info里面的order_id主键作为外键
        logistics_info.setOrder_id(order_info.getOrder_id());
        flag = logistics_infoMapper.insertLogistics_Info(logistics_info);

        return flag;
    }

    //2:商品插入到订单，接口参数尽可能少，尽可能一个函数(或者方法)只做一件事，初始化这其他地方做。或者
    //通过调用函数来完成初始化.
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int goodsToOrder(Order_Info order_info, Order_Item_Info order_item_info,
                            Logistics_Info logistics_info) {
        int flag = 0;
        //1插入订单总表
        flag = order_infoMapper.insertOrder_Info(order_info);
        int order_id = order_info.getOrder_id();//因为order_id是自增长的，所以只有插入之后才能取到
        //2:插入订单明细
        order_item_info.setOrder_id(order_id);
        flag = order_itemMapper.insertOrder_Item_Info(order_item_info);
        //3:插入物流总表。物流明细由物流点自动添加进去
        logistics_info.setOrder_id(order_id);
        flag = logistics_infoMapper.insertLogistics_Info(logistics_info);

        return flag;
    }

    //3:购物车导入到订单
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int cartToOrder(List<Order_Info> order_infoList, Logistics_Info logistics_info) {
        int flag = 0;
        //先进行第一重循环，把order_info循环插入
        for (Order_Info order_info : order_infoList) {
            flag = order_infoMapper.insertOrder_Info(order_info);
            int order_id = order_info.getOrder_id();
            //循环插入订单明细，有多少条插入多少条。order_info.getOrder_item_infoList()获取要插入的明细数据
            for (Order_Item_Info order_item_info : order_info.getOrder_item_infoList()) {
                order_item_info.setOrder_id(order_id);
                flag = order_itemMapper.insertOrder_Item_Info(order_item_info);
            }
            //最后插入物流总表信息。譬如寄给谁 地址，电话等信息
            logistics_info.setOrder_id(order_id);
            flag = logistics_infoMapper.insertLogistics_Info(logistics_info);
        }

        return flag = 0;
    }
}