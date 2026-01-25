package ohh.net.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.common.exception.CustomException;
import ohh.net.web.mapper.LinkTypeMapper;
import ohh.net.model.Link;
import ohh.net.model.LinkType;
import ohh.net.common.utils.Result;
import ohh.net.web.service.LinkService;
import ohh.net.common.utils.Paging;
import ohh.net.vo.PageVo;
import ohh.net.vo.link.LinkFilterVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "网站管理")
@RestController
@RequestMapping("/link")
@Transactional
public class LinkController {
    @Resource
    private LinkService linkService;
    @Resource
    private LinkTypeMapper linkTypeMapper;

    @RateLimit
    @PostMapping
    @NoTokenRequired
    @Operation(summary = "新增网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Link link, @RequestHeader(value = "Authorization", required = false) String token) throws Exception {
        linkService.add(link, token);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Link data = linkService.getById(id);
        if (data == null) return Result.error("该数据不存在");
        linkService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result delBatch(@RequestBody List<Integer> ids) {
        linkService.removeByIds(ids);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Link link) {
        linkService.updateById(link);
        return Result.success();
    }

    @RateLimit
    @GetMapping("/{id}")
    @Operation(summary = "获取网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Link> get(@PathVariable Integer id) {
        Link data = linkService.getById(id);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/list")
    @Operation(summary = "获取网站列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Link>> list(@RequestBody LinkFilterVo filterVo) {
        List<Link> data = linkService.list(filterVo);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询网站列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestBody LinkFilterVo filterVo, PageVo pageVo) {
        Page<Link> data = linkService.paging(filterVo, pageVo);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }

    @RateLimit
    @GetMapping("/type")
    @Operation(summary = "获取网站类型列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<List<LinkType>> typeList() {
        List<LinkType> data = linkTypeMapper.selectList(null);
        return Result.success(data);
    }

    @PatchMapping("/audit/{id}")
    @Operation(summary = "审核指定网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result auditWeb(@PathVariable Integer id) {
        Link data = linkService.getById(id);

        if (data == null) throw new CustomException(400, "该网站不存在");

        data.setAuditStatus(1);
        linkService.updateById(data);
        return Result.success();
    }
}
