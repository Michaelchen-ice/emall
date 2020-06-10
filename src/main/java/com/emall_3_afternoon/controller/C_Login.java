package com.emall_3_afternoon.controller;

import com.emall_3_afternoon.entity.Login_Result;
import com.emall_3_afternoon.entity.User;
import com.emall_3_afternoon.service.S_Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class C_Login {

    @Autowired
    private S_Login s_login;

    @RequestMapping("login")
    public String gotoLogin() {
        return "login";
    }

    @RequestMapping("/check")
    public String checkLogin(@RequestParam("tel") String tel,
                             @RequestParam("pwd") String pwd,
                             HttpServletRequest request) {
        User user = new User(tel, pwd);
        Login_Result check = s_login.checkLogin(user);
        if (check.getResult() == 1) {
            HttpSession session = request.getSession();
            session.setAttribute("b_s_id", check.getB_s_id());
            session.setAttribute("username", check.getNickname());
            return "user_index";
        } else {
            return "login";
        }
    }

    @RequestMapping("/user_index")
    public String user_index() {
        return "user_index";
    }
}
