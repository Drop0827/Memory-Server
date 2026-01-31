package ohh.net.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.common.utils.Paging;
import ohh.net.model.Rss;
import ohh.net.common.utils.Result;
import ohh.net.vo.PageVo;
import ohh.net.web.service.RssService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.*;

@Tag(name = "订阅管理")
@RestController
@RequestMapping("/rss")
public class RssController {
    @Resource
    private RssService rssService;

    @RateLimit
    @NoTokenRequired
    @GetMapping("/list")
    @Operation(summary = "获取订阅内容")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<List<Rss>> list() {
        List<Rss> list = rssService.list();
        return Result.success(list);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询订阅内容")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<Map<String, Object>> paging(PageVo pageVo) {
        Page<Rss> data = rssService.paging(pageVo);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }
}
