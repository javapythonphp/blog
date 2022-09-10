package com.zyc.myblog.service;

import com.zyc.myblog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
