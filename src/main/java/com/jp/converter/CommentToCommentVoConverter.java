package com.jp.converter;

import com.jp.model.domain.Comment;
import com.jp.model.domain.CommentVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author eddie
 */
public class CommentToCommentVoConverter {

    public static CommentVo convert(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        return commentVo;
    }

    public static List<CommentVo> convert(List<Comment> comments) {
        return comments.stream().map(e -> convert(e)).collect(Collectors.toList());
    }

}
