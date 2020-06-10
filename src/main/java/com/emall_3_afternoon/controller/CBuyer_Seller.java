package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Buyer_Seller_Info;
import com.emall_3_afternoon.service.SBuyer_Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CBuyer_Seller {
    @Autowired
    private SBuyer_Seller s_buyer_seller;

    @RequestMapping("get_buyer_list")
    @ResponseBody
    public List<Buyer_Seller_Info> getBuyerList(HttpServletRequest request) {
        int status = Integer.parseInt(request.getParameter("status"));
        return s_buyer_seller.getBuyerList(status);
    }
}
