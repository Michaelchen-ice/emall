package com.emall_3_afternoon.service;

import com.emall_3_afternoon.entity.Field_Dictionary;
import com.emall_3_afternoon.mapper.Field_DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Field_Dictionary {
    @Autowired
    private Field_DictionaryMapper field_dictionaryMapper;

    public List<Field_Dictionary> getFieldList(String dictionary_code) {
        return field_dictionaryMapper.getFieldList(dictionary_code);
    }

    public String getFieldValue(String dictionary_code) {
        return field_dictionaryMapper.getFieldValue(dictionary_code);
    }
}
