package ohh.net.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import ohh.net.common.annotation.RateLimit;
import ohh.net.common.utils.Result;
import ohh.net.model.PageConfig;
import ohh.net.web.service.PageConfigService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "页面配置管理")
@RestController
@RequestMapping("/page_config")
public class PageConfigController {
    @Resource
    private PageConfigService pageConfigService;

    @RateLimit
    @Operation(summary = "获取页面配置列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    @GetMapping("/list")
    public Result<List<PageConfig>> list() {
        List<PageConfig> data = pageConfigService.list();
        return Result.success("获取成功", data);
    }

    @RateLimit
    @Operation(summary = "根据名称获取页面配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    @GetMapping("/name/{name}")
    public Result<PageConfig> getByName(@Parameter(description = "配置名称", required = true, example = "home_page") @PathVariable String name) {
        PageConfig pageConfig = pageConfigService.getByName(name);
        return pageConfig != null ? Result.success("获取成功", pageConfig) : Result.error("配置不存在");
    }

    @RateLimit
    @Operation(summary = "根据ID获取页面配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    @GetMapping("/{id}")
    public Result<PageConfig> getById(@Parameter(description = "页面配置ID", required = true, example = "1") @PathVariable Integer id) {
        PageConfig pageConfig = pageConfigService.getById(id);
        return pageConfig != null ? Result.success("获取成功", pageConfig) : Result.error("配置不存在");
    }

    @RateLimit
    @Operation(summary = "根据ID更新页面配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    @PatchMapping("/json/{id}")
    public Result<String> updateJsonValue(
            @Parameter(description = "页面配置ID", required = true, example = "1") @PathVariable Integer id,
            @Parameter(description = "JSON配置值", required = true) @RequestBody Map<String, Object> jsonValue) {
        boolean success = pageConfigService.updateJsonValue(id, jsonValue);
        return success ? Result.success() : Result.error();
    }
} 