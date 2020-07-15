package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.model.domain.Comment;
import com.itheima.model.domain.CommentVo;

/**
 * @Classname ICommentService
 * @Description 文章评论业务处理接口
 * @Date 2019-3-14 10:13
 * @Created by CrazyStone
 */
public interface ICommentService {
    // 获取文章下的评论
    public PageInfo<Comment> getComments(Integer aid, int page, int count);

    // 用户发表评论
    public void pushComment(Comment comment);

    /**
     * 跳转到后台评论列表页面
     *
     * @param page
     * @param count
     * @return
     */
    PageInfo selectCommentWithAllPage(int page, int count);

    /**
     * 根据id删除评论
     *
     * @param id
     */
    void deleteCommentWithId(int id);

}

