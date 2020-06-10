package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Field_Dictionary;
import com.emall_3_afternoon.entity.Goods_Info;
import com.emall_3_afternoon.entity.Goods_Photo_Path_Info;
import com.emall_3_afternoon.entity.Goods_Property_Info;
import com.emall_3_afternoon.service.S_Field_Dictionary;
import com.emall_3_afternoon.service.S_Goods_Info;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class C_Goods_Info {
    @Autowired
    private S_Goods_Info s_goods_info;

    @RequestMapping("show_goods_list")
    public String showGoodsList(HttpServletRequest request, Model model) {
        int store_id = Integer.parseInt(request.getParameter("store_id"));
        List<Goods_Info> goods_infoList = null;
        //传入分页的信息
        int page_size = 10;
        int start = 1;
        String field = "";
        String field_value = "";
        goods_infoList = s_goods_info.getGoodsList(field, field_value, start, page_size);

        return "show_goods_list";
    }

    @RequestMapping("show_input_goods")
    public String showInputGoods_Info(HttpServletRequest request, Model model) {
        String dictionary_code = request.getParameter("three");
        //System.out.println(dictionary_code);
        model.addAttribute("dictionary_code", dictionary_code);
        return "input_goods";
    }

    @RequestMapping(value = "image_upload", method = RequestMethod.POST)
    public @ResponseBody
    String upload(MultipartFile file, HttpServletRequest request) {
        try {
            //String pathName = System.getProperty("user.dir")
            //        + "\\src\\main\\resources\\static\\upload\\";
            String pathName = request.getServletContext().getRealPath("/upload/");
            pathName += file.getOriginalFilename();
            //System.out.println(pathName);
            FileUtils.writeByteArrayToFile(new File(pathName), file.getBytes());
            return "/upload/" + file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping(value = "insert_goods_info", method = RequestMethod.POST)
    public String insertGoods_Info(HttpServletRequest request, Goods_Info goods_info, RedirectAttributes attributes) {
        goods_info.setGoods_actual_price(goods_info.getGoods_price());
        goods_info.setStore_id(1);
        //init 图片路径,写的不好的地方，不想改了。
        List<Goods_Photo_Path_Info> goods_photo_path_infoList = new ArrayList<Goods_Photo_Path_Info>();
        String image_one = request.getParameter("image_one");
        if (image_one.equals(null) || image_one.trim() != "") {
            Goods_Photo_Path_Info goods_photo_path_info = new Goods_Photo_Path_Info();
            goods_photo_path_info.setPath_name(image_one);
            goods_photo_path_infoList.add(goods_photo_path_info);
        }


        String image_two = request.getParameter("image_two");
        if (image_two.equals(null) || image_two.trim() != "") {
            Goods_Photo_Path_Info goods_photo_path_info = new Goods_Photo_Path_Info();
            goods_photo_path_info.setPath_name(image_two);
            goods_photo_path_infoList.add(goods_photo_path_info);
        }

        String image_three = request.getParameter("image_three");
        if (image_three.equals(null) || image_three.trim() != "") {
            Goods_Photo_Path_Info goods_photo_path_info = new Goods_Photo_Path_Info();
            goods_photo_path_info.setPath_name(image_three);
            goods_photo_path_infoList.add(goods_photo_path_info);
        }

        String image_four = request.getParameter("image_four");
        if (image_four.equals(null) || image_four.trim() != "") {
            Goods_Photo_Path_Info goods_photo_path_info = new Goods_Photo_Path_Info();
            goods_photo_path_info.setPath_name(image_four);
            goods_photo_path_infoList.add(goods_photo_path_info);
        }

        String image_five = request.getParameter("image_five");
        //request.getSession().getAttribute('a') == null; 如果改为equals则得不到想要的答案。
        //而image_five == null 则得不到想要的答案。和String对象比较有关系。
        if (image_five.equals(null) || image_five.trim() != "") {
            Goods_Photo_Path_Info goods_photo_path_info = new Goods_Photo_Path_Info();
            goods_photo_path_info.setPath_name(image_five);
            goods_photo_path_infoList.add(goods_photo_path_info);
        }
        //init 对应属性 图像应该参考属性来写。这样废码就少了。
        int field_value_length = Integer.parseInt(request.getParameter("field_value_length"));
        List<Goods_Property_Info> goods_property_infoList = new ArrayList<Goods_Property_Info>();
        for (int i = 0; i < field_value_length; i++) {
            String field_name = request.getParameter("hidden_field" + i);
            String field_value = request.getParameter("field" + i);
            Goods_Property_Info goods_property_info = new Goods_Property_Info();
            goods_property_info.setProperty_name(field_name);
            goods_property_info.setProperty_value(field_value);
            goods_property_infoList.add(goods_property_info);
        }
        //test
        /*for (Goods_Property_Info goods_property_info : goods_property_infoList) {
            System.out.println(goods_property_info.getProperty_name() + "---" + goods_property_info.getProperty_value());
        }
        for (Goods_Photo_Path_Info goods_photo_path_info : goods_photo_path_infoList){
            System.out.println(goods_photo_path_info.getPath_name());
        }*/
        int flag = 0;
        flag = s_goods_info.insertGoods_Info(goods_info, goods_photo_path_infoList, goods_property_infoList);
        if (flag == 0)
            return "error";
        else {
            attributes.addAttribute("store_id", 1);
            return "redirect:showGoodsList";
        }
    }
}
