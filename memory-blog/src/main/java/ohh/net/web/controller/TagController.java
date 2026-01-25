package ohh.net.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.model.Tag;
import ohh.net.common.utils.Result;
import ohh.net.web.service.TagService;
import ohh.net.common.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

// 适配 Spring Boot 3 (Jakarta)
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;


@io.swagger.v3.oas.annotations.tags.Tag(name = "标签管理")
@RestController
@RequestMapping("/tag")
@Transactional
public class TagController {
    @Resource
    private TagService tagService;

    @PostMapping
    @Operation(summary = "新增标签")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Tag tag) {
        tagService.save(tag);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除标签")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Tag data = tagService.getById(id);
        if (data == null) return Result.error("该数据不存在");
        tagService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除标签")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        tagService.removeByIds(ids);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑标签")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Tag tag) {
        tagService.updateById(tag);
        return Result.success();
    }

    @RateLimit
    @GetMapping("/{id}")
    @Operation(summary = "获取标签")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Tag> get(@PathVariable Integer id) {
        Tag data = tagService.getById(id);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/list")
    @Operation(summary = "获取标签列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Tag>> list() {
        List<Tag> data = tagService.list();
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询标签列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        Page<Tag> data = tagService.list(page, size);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }

    @RateLimit
    @GetMapping("/article/count")
    @Operation(summary = "统计每个标签下的文章数量")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result staticArticleCount() {
        List<Tag> list = tagService.staticArticleCount();
        return Result.success(list);
    }
}