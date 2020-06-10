package com.emall_3_afternoon.service;

import com.emall_3_afternoon.entity.Buyer_Seller_Info;
import com.emall_3_afternoon.mapper.Buyer_SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SBuyer_Seller {
    @Autowired
    private Buyer_SellerMapper buyer_sellerMapper;

    public List<Buyer_Seller_Info> getBuyerList(int status) {
        return buyer_sellerMapper.getBuyerList(status);
    }
}
