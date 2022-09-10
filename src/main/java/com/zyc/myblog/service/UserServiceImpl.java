package com.zyc.myblog.service;

import com.zyc.myblog.dao.UserRepository;
import com.zyc.myblog.po.User;
import com.zyc.myblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Autowired
    public void setSysRoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username,password);
        return user;
    }
}
