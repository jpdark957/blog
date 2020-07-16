package com.jp.service.impl;

import com.jp.dao.UserMapper;
import com.jp.model.domain.User;
import com.jp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    //注册账号
    @Transactional
    @Override
    public boolean adduser(String username, String password) {
        //判断账号是否存在
        User flag = userMapper.getuserByusername(username);
        if (flag!=null){
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreated(new Date());
        //将账号保存到数据库
        userMapper.addUser(user);
        //赋予角色
        User flag2 = userMapper.getuserByusername(username);
        userMapper.userauth(flag2.getId(), 2);

        return true;
    }

    @Override
    public boolean addAdmin(String username, String password) {
        User user1 = userMapper.getuserByusername(username);
        if(user1 != null) {
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreated(new Date());
        userMapper.addAdmin(user);
        //赋予角色
        userMapper.userauth(user.getId(), 1);
        return true;
    }
}
