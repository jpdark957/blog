package com.itheima.service;

import com.itheima.model.domain.User;

public interface IUserService {
    //注册账号
    boolean adduser(String username,String password);

    boolean addAdmin(String username, String password);
}
