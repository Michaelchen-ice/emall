package com.emall_3_afternoon.mapper;

import com.emall_3_afternoon.entity.Store_Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Store_InfoMapper {
    @Select("select * from store_info where store_id = #{store_id}")
    Store_Info getAStoreInfo(int store_id);
}
