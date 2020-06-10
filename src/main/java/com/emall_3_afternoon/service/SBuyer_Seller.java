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

    public Buyer_Seller_Info getUser(int b_s_id) {
        return buyer_sellerMapper.getUser(b_s_id);
    }

    public void updateUser(Buyer_Seller_Info buyer_seller_info) {
        buyer_sellerMapper.updateUser(buyer_seller_info);
    }
}
