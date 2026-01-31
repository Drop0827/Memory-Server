package ohh.net.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.common.exception.CustomException;
import ohh.net.model.Comment;
import ohh.net.dto.comment.CommentFormDTO;
import ohh.net.common.utils.Result;
import ohh.net.web.service.CommentService;
import ohh.net.common.utils.Paging;
import ohh.net.vo.PageVo;
import ohh.net.vo.comment.CommentFilterVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "评论管理")
@RestController
@RequestMapping("/comment")
@Transactional
public class CommentController {
    @Resource
    private CommentService commentService;

    @RateLimit
    @NoTokenRequired
    @PostMapping
    @Operation(summary = "新增评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody CommentFormDTO commentFormDTO) throws Exception {
        Comment comment = BeanUtil.copyProperties(commentFormDTO, Comment.class);
        commentService.add(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Comment data = commentService.getById(id);
        if (data == null)
            return Result.error("删除评论失败：该评论不存在");
        commentService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result<String> batchDel(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody CommentFormDTO commentFormDTO) {
        Comment comment = BeanUtil.copyProperties(commentFormDTO, Comment.class);
        commentService.updateById(comment);
        return Result.success();
    }

    @RateLimit
    @GetMapping("/{id}")
    @Operation(summary = "获取评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Comment> get(@PathVariable Integer id) {
        Comment data = commentService.get(id);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/list")
    @Operation(summary = "获取评论列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Comment>> list(@RequestBody CommentFilterVo filterVo) {
        List<Comment> list = commentService.list(filterVo);
        return Result.success(list);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询评论列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<Map<String, Object>> paging(@RequestBody CommentFilterVo filterVo, PageVo pageVo) {
        Page<Comment> list = commentService.paging(filterVo, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/article/{articleId}")
    @Operation(summary = "获取指定文章中所有评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<Map<String, Object>> getArticleCommentList(@PathVariable Integer articleId, PageVo pageVo) {
        Page<Comment> list = commentService.getArticleCommentList(articleId, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @PatchMapping("/audit/{id}")
    @Operation(summary = "审核指定评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result<String> auditComment(@PathVariable Integer id) {
        Comment data = commentService.getById(id);

        if (data == null)
            throw new CustomException(400, "该评论不存在");

        data.setAuditStatus(1);
        commentService.updateById(data);
        return Result.success();
    }
}
