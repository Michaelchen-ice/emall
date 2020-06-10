package com.emall_3_afternoon.mapper;

import com.emall_3_afternoon.entity.Login_Result;
import com.emall_3_afternoon.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Login_InfoMapper {

    @Select("SELECT COUNT(*) as result, b_s_id,nickname FROM buyer_seller_info where `telephone` = #{tel} AND `pwd` = #{pwd};")
    public Login_Result loginCheck(User user);
}
