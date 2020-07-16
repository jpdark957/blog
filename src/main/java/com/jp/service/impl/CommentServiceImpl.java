package com.jp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.dao.ArticleMapper;
import com.jp.dao.CommentMapper;
import com.jp.dao.StatisticMapper;
import com.jp.model.domain.Article;
import com.jp.model.domain.Comment;
import com.jp.model.domain.Statistic;
import com.jp.model.domain.CommentVo;
import com.jp.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * @Classname CommentServiceImpl
 * @Description TODO
 * @Date 2019-3-14 10:15
 * @Created by CrazyStone
 */

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private ArticleMapper articleMapper;

    // 根据文章id分页查询评论
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> commentList = commentMapper.selectCommentWithPage(aid);
        PageInfo<Comment> commentInfo = new PageInfo<>(commentList);
        return commentInfo;
    }

    // 用户发表评论
    @Override
    public void pushComment(Comment comment){
        commentMapper.pushComment(comment);
        // 更新文章评论数据量
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleCommentsWithId(statistic);
    }

//    /**
//     * 评论分页
//     *
//     * @param page
//     * @param count
//     * @return pageInfo
//     */
    /*
    @Override
    public PageInfo<CommentVo> selectCommentWithAllPage(int page, int count) {
        PageHelper.startPage(page, count);
        List<Comment> comment = commentMapper.selectArticleWithAllPage();
        List<CommentVo> commentVoList = CommentToCommentVoConverter.convert(comment);
        for (CommentVo commentVo : commentVoList) {
            Article article = articleMapper.selectArticleWithId(commentVo.getArticleId());
            commentVo.setTitle(article.getTitle());
        }
        PageInfo<CommentVo> pageInfo = new PageInfo<>(commentVoList);
        return pageInfo;
    }
     */

    /**
     * 评论分页
     * @param page
     * @param count
     * @return
     */
    @Override
    public PageInfo selectCommentWithAllPage(int page, int count) {
        PageHelper.startPage(page, 5);
        List<Comment> comments = commentMapper.selectArticleWithAllPage();
        PageInfo pageInfo = new PageInfo<>(comments);
        List<CommentVo> commentVoList = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            CommentVo commentVo = new CommentVo();
            BeanUtils.copyProperties(comments.get(i), commentVo);
            Article article = articleMapper.selectArticleWithId(commentVo.getArticleId());
            commentVo.setTitle(article.getTitle());
            commentVoList.add(commentVo);
        }
        pageInfo.setList(commentVoList);
        return pageInfo;
    }

    /**
     * 根据id删除评论
     *
     * @param id
     */
    @Override
    public void deleteCommentWithId(int id) {
        // 同时删除对应文章的评论数据
        commentMapper.deleteCommentWithCommentId(id);
    }

}

