package com.emall_3_afternoon.service;

import com.emall_3_afternoon.entity.Goods_Info;
import com.emall_3_afternoon.entity.Goods_Photo_Path_Info;
import com.emall_3_afternoon.entity.Goods_Property_Info;
import com.emall_3_afternoon.mapper.Goods_InfoMapper;
import com.emall_3_afternoon.mapper.Goods_Photo_Path_InfoMapper;
import com.emall_3_afternoon.mapper.Goods_Property_InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class S_Goods_Info {
    @Autowired
    private Goods_InfoMapper goods_infoMapper;
    @Autowired
    private Goods_Photo_Path_InfoMapper goods_photo_path_infoMapper;
    @Autowired
    private Goods_Property_InfoMapper goods_property_infoMapper;

    public List<Goods_Info> getGoodsList(String field, String field_value, int start, int page_size) {
        return goods_infoMapper.getGoodsList(field, field_value, start, page_size);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int insertGoods_Info(Goods_Info goods_info,
                                List<Goods_Photo_Path_Info> goods_photo_path_infoList,
                                List<Goods_Property_Info> goods_property_infoList) {
        int flag = 0;
        int goods_id = -1;
        //插入goods_info 一条数据
        flag = goods_infoMapper.insertGoods_Info(goods_info);
        goods_id = goods_info.getGoods_id();
        //插入图片路径， 多条数据
        for (Goods_Photo_Path_Info goods_photo_path_info : goods_photo_path_infoList) {
            goods_photo_path_info.setGoods_id(goods_id);
            flag = goods_photo_path_infoMapper.insertGoods_Photo_Path_Info(goods_photo_path_info);
        }
        //插入属性值 多条
        for (Goods_Property_Info goods_property_info : goods_property_infoList) {
            goods_property_info.setGoods_id(goods_id);
            flag = goods_property_infoMapper.insertGoods_Property(goods_property_info);
        }

        return flag;
    }


    public Goods_Info getAGoods_Info(int goods_id) {
        return goods_infoMapper.getAGoods_Info(goods_id);
    }

    public String[] getGoodIDs() {
        return goods_infoMapper.goods_ids();
    }
}
