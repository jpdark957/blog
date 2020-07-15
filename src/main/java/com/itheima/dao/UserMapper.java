package com.itheima.dao;

import com.itheima.model.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM t_user WHERE username=#{username}")
    User getuserByusername(String username);

    @Insert("INSERT INTO t_user (`username`,`password`,`created`) VALUES (#{username},#{password},#{created})")
    void addUser(User user);

    @Insert("INSERT INTO t_user_authority (`user_id`,`authority_id`) VALUES (#{userId},#{authorityId})")
    void userauth(Integer userId,Integer authorityId);

    @Insert("INSERT INTO t_user (`username`, `password`, `created`) VALUES (#{user.username}, #{user.password}, #{user.created})")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="user.id", before=false, resultType=int.class)  //插入后返回id值
    int addAdmin(@Param("user") User user);
}
