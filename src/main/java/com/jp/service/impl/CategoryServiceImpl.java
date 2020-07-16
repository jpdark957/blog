package com.jp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.dao.CategoryMapper;
import com.jp.model.domain.Category;
import com.jp.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService{
    @Autowired
    private CategoryMapper categoryMapper;

    //添加分类
    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }

    //分页查询分类
    @Override
    public PageInfo<Category> selectCategoryWithPage(int page, int count) {
        PageHelper.startPage(page, count);
        List<Category> categoryList = categoryMapper.selectCategoryWithPage();
        //分页
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        return pageInfo;
    }

    //根据id删除分类
    @Override
    public void deleteCategory(int id) {
        categoryMapper.deleteCategory(id);
    }

    //根据id修改分类
    @Override
    public void updateCategoryWithId(int id, String title) {
        categoryMapper.updateCategoryWithId(id, title, new Date());
    }
}
