package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Field_Dictionary;
import com.emall_3_afternoon.service.S_Field_Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class C_Field_Dictionary {
    @Autowired
    private S_Field_Dictionary s_field_dictionary;

    @RequestMapping("get_field_list")
    @ResponseBody
    public List<Field_Dictionary> getFieldList(HttpServletRequest request) {
        String dictionary_code = request.getParameter("dictionary_code");
        System.out.println(dictionary_code);
        List<Field_Dictionary> fieldDictionaryList = s_field_dictionary.getFieldList(dictionary_code);

        return fieldDictionaryList;
    }

    @RequestMapping("select_field")
    public String selectField(HttpServletRequest request) {
        return "select_field";
    }

    //应该放在字典类里面。同学们切记。
    @RequestMapping("get_field_value")
    @ResponseBody
    public String getFieldValue(HttpServletRequest request) {
        String dictionary_code = request.getParameter("dictionary_code");
        return s_field_dictionary.getFieldValue(dictionary_code);
    }
}
