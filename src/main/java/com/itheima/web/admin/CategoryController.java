package com.itheima.web.admin;

import com.github.pagehelper.PageInfo;
import com.itheima.model.ResponseData.ArticleResponseData;
import com.itheima.model.domain.Category;
import com.itheima.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/admin")
@Api(tags = "分类模块")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private ICategoryService categoryService;


    @GetMapping(value = "/category/addCategory")
    @ResponseBody
    @ApiOperation("添加分类")
    public ArticleResponseData addCategory(String title) {
        System.out.println(title);
        Category category = new Category();
        category.setParentId(0);
        category.setTitle(title);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        categoryService.addCategory(category);
        return ArticleResponseData.ok();
    }

    @GetMapping(value = "/category")
    @ApiOperation("查询所有分类并且反射到后台分类列表")
    public String index(
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "count", defaultValue = "10") int count,
                           HttpServletRequest request) {
        PageInfo<Category> pageInfo = categoryService.selectCategoryWithPage(page, count);
        request.setAttribute("categorys", pageInfo);
        return "back/category_list";
    }

    @PostMapping(value = "/category/deleteCategory")
    @ResponseBody
    @ApiOperation("根据id删除分类")
    public ArticleResponseData deleteCategory(@RequestParam int id) {
        categoryService.deleteCategory(id);
        return ArticleResponseData.ok();
    }

    @GetMapping(value = "/category/updateCategory")
    @ResponseBody
    @ApiOperation("根据id修改分类")
    public ArticleResponseData updateCategory(@RequestParam int id, @RequestParam String title) {
        try {
            categoryService.updateCategoryWithId(id, title);
            logger.info("分类更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("分类更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

}
