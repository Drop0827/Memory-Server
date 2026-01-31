package ohh.net.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.common.exception.CustomException;
import ohh.net.model.Wall;
import ohh.net.model.WallCate;
import ohh.net.common.utils.Result;
import ohh.net.web.service.WallService;
import ohh.net.common.utils.Paging;
import ohh.net.vo.PageVo;
import ohh.net.vo.wall.WallFilterVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "留言管理")
@RestController
@RequestMapping("/wall")
@Transactional
public class WallController {
    @Resource
    private WallService wallService;

    @RateLimit
    @NoTokenRequired
    @PostMapping
    @Operation(summary = "新增留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Wall wall) throws Exception {
        wallService.add(wall);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Wall data = wallService.getById(id);
        if (data == null)
            return Result.error("删除留言失败：该留言不存在");
        wallService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result<String> batchDel(@RequestBody List<Integer> ids) {
        wallService.removeByIds(ids);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Wall wall) {
        wallService.updateById(wall);
        return Result.success();
    }

    @RateLimit
    @GetMapping("/{id}")
    @Operation(summary = "获取留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Wall> get(@PathVariable Integer id) {
        Wall data = wallService.get(id);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/list")
    @Operation(summary = "获取留言列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Wall>> list(@RequestBody WallFilterVo filterVo) {
        List<Wall> list = wallService.list(filterVo);
        return Result.success(list);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询留言列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<Map<String, Object>> paging(@RequestBody WallFilterVo filterVo, PageVo pageVo) {
        Page<Wall> list = wallService.paging(filterVo, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/cate/{cateId}")
    @Operation(summary = "获取指定分类中所有留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<Map<String, Object>> getCateWallList(@PathVariable Integer cateId, PageVo pageVo) {
        Page<Wall> list = wallService.getCateWallList(cateId, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @RateLimit
    @GetMapping("/cate")
    @Operation(summary = "获取留言分类列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result<List<WallCate>> getCateList() {
        List<WallCate> list = wallService.getCateList();
        return Result.success(list);
    }

    @PatchMapping("/audit/{id}")
    @Operation(summary = "审核指定留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 10)
    public Result<String> auditWall(@PathVariable Integer id) {
        Wall data = wallService.getById(id);

        if (data == null)
            throw new CustomException(400, "该留言不存在");

        data.setAuditStatus(1);
        wallService.updateById(data);
        return Result.success();
    }

    @PatchMapping("/choice/{id}")
    @Operation(summary = "设置与取消精选留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 11)
    public Result<String> updateChoice(@PathVariable Integer id) {
        wallService.updateChoice(id);
        return Result.success();
    }
}
