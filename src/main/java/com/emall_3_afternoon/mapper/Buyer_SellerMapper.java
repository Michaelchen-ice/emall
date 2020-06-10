package com.emall_3_afternoon.mapper;

import com.emall_3_afternoon.entity.Buyer_Seller_Info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Buyer_SellerMapper {
    @Select("select * from buyer_seller_info where status=#{status}")
    List<Buyer_Seller_Info> getBuyerList(int status);

    @Select("select * from buyer_seller_info where b_s_id=#{b_s_id}")
    Buyer_Seller_Info getUser(int b_s_id);

    @Insert("insert into buyer_seller_info(b_s_name,nickname," +
            "sex,icon,telephone,home,home_detail, email) " +
            "values(#{b_s_name},#{nickname},#{sex},#{icon},#{telephone}" +
            "#{home},#{home_detail},#{email})")
    int registerBuyer(Buyer_Seller_Info buyer_seller_info);

    @Update("UPDATE buyer_seller_info SET b_s_name = #{b_s_name},nickname = #{nickname},pwd = #{pwd},telephone = #{telephone},email = #{email} WHERE b_s_id = #{b_s_id} ")
    void updateUser(Buyer_Seller_Info buyer_seller_info);

//        buyer_seller_info.setB_s_name(request.getParameter("name"));
//        buyer_seller_info.setNickname(request.getParameter("username"));
//        buyer_seller_info.setPwd(request.getParameter("password"));
//        buyer_seller_info.setTelephone(request.getParameter("telephone"));
//        buyer_seller_info.setEmail(request.getParameter("email"));
}
