package com.emall_3_afternoon.service;

import com.emall_3_afternoon.entity.Login_Result;
import com.emall_3_afternoon.entity.User;
import com.emall_3_afternoon.mapper.Login_InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Login {

    @Autowired
    private Login_InfoMapper login_infoMapper;

    public Login_Result checkLogin(User user) {
        return login_infoMapper.loginCheck(user);
    }
}
