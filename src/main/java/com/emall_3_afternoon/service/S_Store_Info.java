package com.emall_3_afternoon.service;

import com.emall_3_afternoon.entity.Store_Info;
import com.emall_3_afternoon.mapper.Store_InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Store_Info {

    @Autowired
    private Store_InfoMapper store_infoMapper;

    public Store_Info getStoreInfo(int store_id) {
        return store_infoMapper.getAStoreInfo(store_id);
    }
}
