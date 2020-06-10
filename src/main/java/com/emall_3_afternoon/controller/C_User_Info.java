package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Buyer_Seller_Info;
import com.emall_3_afternoon.service.SBuyer_Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class C_User_Info {

    @Autowired
    private SBuyer_Seller sBuyer_seller;

    @RequestMapping("/getUserInfo")
    public String getUser(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        Buyer_Seller_Info buyer_seller_info = sBuyer_seller.getUser(b_s_id);
        model.addAttribute("buyer", buyer_seller_info);
        return "user_info";
    }

    @RequestMapping("/edit_user")
    public String updateUser(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int b_s_id = Integer.parseInt(session.getAttribute("b_s_id").toString());
        Buyer_Seller_Info buyer_seller_info = initUserInfo(request, b_s_id);
        sBuyer_seller.updateUser(buyer_seller_info);
        return "redirect:/getUserInfo";
    }

    public Buyer_Seller_Info initUserInfo(HttpServletRequest request, int b_s_id) {
        Buyer_Seller_Info buyer_seller_info = new Buyer_Seller_Info();
        buyer_seller_info.setB_s_id(b_s_id);
        buyer_seller_info.setB_s_name(request.getParameter("name"));
        buyer_seller_info.setNickname(request.getParameter("username"));
        buyer_seller_info.setPwd(request.getParameter("password"));
        buyer_seller_info.setTelephone(request.getParameter("telephone"));
        buyer_seller_info.setEmail(request.getParameter("email"));
        return buyer_seller_info;
    }
}
