package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService extends IService<User> {
    R<String> sendMsg(User user, HttpSession session);

    R<User> login(Map map, HttpSession session);
}
