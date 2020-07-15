package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Category;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICategoryService {
    //添加分类
    public void addCategory(Category category);

    //分页查询分类列表
    PageInfo<Category> selectCategoryWithPage(int page, int count);

    //根据id删除分类
    void deleteCategory(int id);

    //根据id修改分类
    void updateCategoryWithId(int id, String title);
}
