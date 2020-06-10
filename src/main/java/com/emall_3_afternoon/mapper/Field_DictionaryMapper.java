package com.emall_3_afternoon.mapper;

import com.emall_3_afternoon.entity.Field_Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Field_DictionaryMapper {
    @Select("select * from field_dictionary where dictionary_code like '"
            + "${dictionary_code}" + "__'")
    public List<Field_Dictionary> getFieldList(@Param("dictionary_code") String dictionary_code);

    @Select("select field_value from field_dictionary where dictionary_code = #{dictionary_code}")
    public String getFieldValue(String dictionary_code);
}
