package com.jp.web.admin;

import com.github.pagehelper.PageInfo;
import com.jp.model.ResponseData.ArticleResponseData;
import com.jp.model.ResponseData.CommentResponseData;
import com.jp.model.ResponseData.StaticticsBo;
import com.jp.model.domain.Article;
import com.jp.model.domain.Comment;
import com.jp.service.IArticleService;
import com.jp.service.ICommentService;
import com.jp.service.ISiteService;
import com.jp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * @Classname AdminController
 * @Description 后台管理模块
 * @Date 2019-3-14 10:39
 * @Created by CrazyStone
 */
@Controller
@RequestMapping("/admin")
@Api(tags = "后台模块")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ISiteService siteServiceImpl;
    @Autowired
    private IArticleService articleServiceImpl;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;

    // 管理中心起始页
    @GetMapping(value = {"", "/index"})
    @ApiOperation("反射到后台首页并查询数据")
    public String index(HttpServletRequest request) {
        // 获取最新的5篇博客、评论以及统计数据
        List<Article> articles = siteServiceImpl.recentArticles(5);
        List<Comment> comments = siteServiceImpl.recentComments(5);
        StaticticsBo staticticsBo = siteServiceImpl.getStatistics();
        // 向Request域中存储数据
        request.setAttribute("comments", comments);
        request.setAttribute("articles", articles);
        request.setAttribute("statistics", staticticsBo);
        return "back/index";
    }

    // 向文章发表页面跳转
    @GetMapping(value = "/article/toEditPage")
    @ApiOperation("反射到文章发表页面")
    public String newArticle( ) {
        return "back/article_edit";
    }
    // 发表文章
    @PostMapping(value = "/article/publish")
    @ResponseBody
    @ApiOperation("发表文章")
    public ArticleResponseData publishArticle(Article article) {
//        if (article.getCategories()) {
//            article.setCategories(1);
//        }
        try {
            articleServiceImpl.publish(article);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
    // 跳转到后台文章列表页面
    @GetMapping(value = "/article")
    @ApiOperation("反射到后台文章列表")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "10") int count,
                        HttpServletRequest request) {
        PageInfo<Article> pageInfo = articleServiceImpl.selectArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    // 向文章修改页面跳转
    @GetMapping(value = "/article/{id}")
    @ApiOperation("反射到修改文章页面")
    public String editArticle(@PathVariable("id") String id, HttpServletRequest request) {
        Article article = articleServiceImpl.selectArticleWithId(Integer.parseInt(id));
        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());
        return "back/article_edit";
    }

    // 文章修改处理
    @PostMapping(value = "/article/modify")
    @ResponseBody
    @ApiOperation("修改文章")
    public ArticleResponseData modifyArticle(Article article) {
        try {
            articleServiceImpl.updateArticleWithId(article);
            logger.info("文章更新成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    // 文章删除
    @PostMapping(value = "/article/delete")
    @ResponseBody
    @ApiOperation("删除文章")
    public ArticleResponseData delete(@RequestParam int id) {
        try {
            articleServiceImpl.deleteArticleWithId(id);
            logger.info("文章删除成功");
            return ArticleResponseData.ok();
        } catch (Exception e) {
            logger.error("文章删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    /**
     * 跳转到后台评论列表页面
     * @param page
     * @param count
     * @param request
     * @return back/article_list页面
     */
    @GetMapping(value = "/comments")
    public String comments(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "count", defaultValue = "5") int count,
                           HttpServletRequest request) {
        PageInfo pageInfo = commentService.selectCommentWithAllPage(page, count);
        request.setAttribute("comments", pageInfo);
        return "back/comments_list";
    }

    /**
     * 根据id删除评论
     *
     * @param id
     * @return CommentResponseData信息
     */
    @PostMapping(value = "/comments/delete")
    @ResponseBody
    public CommentResponseData commentDelete(@RequestParam int id){
        try {
            commentService.deleteCommentWithId(id);
            logger.info("评论删除成功");
            return CommentResponseData.ok();
        } catch (Exception e) {
            logger.error("评论删除失败，错误信息: "+e.getMessage());
            return CommentResponseData.fail();
        }
    }

    @GetMapping(value = "/add")
    @ApiOperation("跳转到注册页面")
    public String reg() {
        return "back/add";
    }

    @PostMapping("/addAdmin")
    @ApiOperation("添加管理员")
    public String addAdmin(String username, String password, HttpServletRequest request) {
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        userService.addAdmin(username, hashpw);
        this.index(request);
//        return "back/add";
        return "back/index";
    }

}

