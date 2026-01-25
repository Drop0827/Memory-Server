package ohh.net.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ohh.net.model.Comment;
import ohh.net.vo.PageVo;
import ohh.net.vo.comment.CommentFilterVo;

import java.util.List;

public interface CommentService extends IService<Comment> {
    void add(Comment comment) throws Exception;

    Comment get(Integer id);

    Page<Comment> getArticleCommentList(Integer articleId, PageVo pageVo);

    List<Comment> list(CommentFilterVo filterVo);

    Page<Comment> paging(CommentFilterVo filterVo, PageVo pageVo);
}
