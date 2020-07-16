package com.jp.service;

public interface IUserService {
    //注册账号
    boolean adduser(String username,String password);

    boolean addAdmin(String username, String password);
}
