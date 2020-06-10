package com.emall_3_afternoon.mapper;

import com.emall_3_afternoon.entity.Order_Info;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Order_InfoMapper {
    @Select("select * from order_info where b_s_id = #{b_s_id}")
    @Results({ //解决订单明细的存储问题。根据order_id来进行查找，调用
            // com.emall_5_afternoon.mapper.Order_ItemMapper接口下的getOrder_Item_InfoList方法。
            //把获取的list<XXXXX>的数据填充到order_item_infoList.
            @Result(column = "order_id", property = "order_item_infoList",
                    many = @Many(select = "com.emall_3_afternoon.mapper.Order_Item_InfoMapper" +
                            ".getOrder_Item_InfoList",
                            fetchType = FetchType.EAGER))
    })
    List<Order_Info> getOrder_InfoList(int b_s_id);

    @Insert("insert into order_info(order_id, order_no, order_money, order_time, store_id, " +
            "order_status, pay_way, pay_status, pay_time,b_s_id) " +
            "values(#{order_id}, #{order_no}, #{order_money}, #{order_time}, #{store_id}, " +
            "#{order_status}, #{pay_way}, #{pay_status}, #{pay_time}, #{b_s_id})")
    int insertOrder_Info(Order_Info order_info);
}