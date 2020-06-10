package com.emall_3_afternoon.mapper;

import com.emall_3_afternoon.entity.Buyer_Seller_Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Buyer_SellerMapper {
    @Select("select * from buyer_seller_info where status=#{status}")
    List<Buyer_Seller_Info> getBuyerList(int status);

    @Insert("insert into buyer_seller_info(b_s_name,nickname," +
            "sex,icon,telephone,home,home_detail, email) " +
            "values(#{b_s_name},#{nickname},#{sex},#{icon},#{telephone}" +
            "#{home},#{home_detail},#{email})")
    int registerBuyer(Buyer_Seller_Info buyer_seller_info);
}
