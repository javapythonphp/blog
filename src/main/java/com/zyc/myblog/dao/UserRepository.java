package com.zyc.myblog.dao;

import com.zyc.myblog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;

@Resource
public interface UserRepository extends JpaRepository<User,Long> {

      User findByUsernameAndPassword(String username,String password);
}
