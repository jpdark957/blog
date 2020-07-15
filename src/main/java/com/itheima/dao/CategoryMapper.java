package com.itheima.dao;

import com.itheima.model.domain.Category;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CategoryMapper {
    //添加分页
    @Insert("INSERT INTO t_category (parentId, title, create_time, update_time) " +
            " VALUES (#{parentId}, #{title}, #{createTime}, #{updateTime})")
    void addCategory(Category category);

    //查询所有分类
    @Select("SELECT * FROM t_category ORDER BY create_time DESC")
    List<Category> selectCategoryWithPage();

    //根据id删除
    @Delete("DELETE FROM t_category WHERE id = #{id}")
    void deleteCategory(int id);

    //根据id修改分类
    @Update("UPDATE t_category SET title = #{title}, update_time = #{date} WHERE id = #{id}")
    void updateCategoryWithId(int id, String title, Date date);

    @Select("SELECT * FROM t_category WHERE id = #{id}")
    Category queryCategoryByid(int id);
}
