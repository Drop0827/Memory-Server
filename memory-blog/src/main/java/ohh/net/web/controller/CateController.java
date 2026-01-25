package ohh.net.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.dto.cate.CateFormDTO;
import ohh.net.model.Cate;
import ohh.net.common.utils.Result;
import ohh.net.result.cate.CateArticleCount;
import ohh.net.web.service.CateService;
import ohh.net.common.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/cate")
@Transactional
public class CateController {
    @Resource
    private CateService cateService;

    @PostMapping
    @Operation(summary = "新增分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody CateFormDTO cateFormDTO) {
        Cate cate = BeanUtil.copyProperties(cateFormDTO, Cate.class);
        cateService.save(cate);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        cateService.del(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            boolean e = cateService.isExistTwoCate(id);
            if (!e) return Result.error();
            cateService.removeById(id);
        }

        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody CateFormDTO cateFormDTO) {
        Cate cate = BeanUtil.copyProperties(cateFormDTO, Cate.class);
        cateService.updateById(cate);
        return Result.success();
    }

    @RateLimit
    @GetMapping("/{id}")
    @Operation(summary = "获取分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Cate> get(@PathVariable Integer id) {
        Cate data = cateService.get(id);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/list")
    @Operation(summary = "获取分类列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Cate>> list(@Parameter(description = "默认为tree树性结构，设置为list表示列表结构") @RequestParam(defaultValue = "recursion") String pattern) {
        List<Cate> data = cateService.list(pattern);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询分类列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        Page<Cate> data = cateService.paging(page, size);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }

    @RateLimit
    @GetMapping("/article/count")
    @Operation(summary = "获取每个分类中的文章数量")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<List<CateArticleCount>> cateArticleCount() {
        List<CateArticleCount> list = cateService.cateArticleCount();
        return Result.success(list);
    }
}
