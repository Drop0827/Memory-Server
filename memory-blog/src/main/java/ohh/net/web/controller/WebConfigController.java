package ohh.net.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.RateLimit;
import ohh.net.common.utils.Result;
import ohh.net.web.service.WebConfigService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import ohh.net.model.WebConfig;

@Tag(name = "网站配置管理")
@RestController
@RequestMapping("/web_config")
@Transactional
public class WebConfigController {

    @Resource
    private WebConfigService webConfigService;

    @RateLimit
    @Operation(summary = "获取网站配置列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    @GetMapping("/list")
    public Result<List<WebConfig>> list() {
        List<WebConfig> data = webConfigService.list();
        return Result.success("获取成功", data);
    }

    @RateLimit
    @Operation(summary = "根据名称获取网站配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    @GetMapping("/name/{name}")
    public Result<WebConfig> getByName(@PathVariable String name) {
        WebConfig webConfig = webConfigService.getByName(name);
        return webConfig != null ? Result.success("获取成功", webConfig) : Result.error("配置不存在");
    }

    @RateLimit
    @Operation(summary = "根据ID获取网站配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    @GetMapping("/{id}")
    public Result<WebConfig> getById(@PathVariable Integer id) {
        WebConfig webConfig = webConfigService.getById(id);
        return webConfig != null ? Result.success("获取成功", webConfig) : Result.error("配置不存在");
    }

    @RateLimit
    @Operation(summary = "根据ID更新网站配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    @PatchMapping("/json/{id}")
    public Result<String> updateJsonValue(@PathVariable Integer id, @RequestBody Map<String, Object> jsonValue) {
        boolean success = webConfigService.updateJsonValue(id, jsonValue);
        return success ? Result.success() : Result.error();
    }

    @RateLimit
    @Operation(summary = "根据名称更新网站配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    @PatchMapping("/json/name/{name}")
    public Result<String> updateJsonValueByName(@PathVariable String name, @RequestBody Map<String, Object> jsonValue) {
        WebConfig webConfig = webConfigService.getByName(name);
        if (webConfig == null) {
            return Result.error("配置不存在");
        }
        boolean success = webConfigService.updateJsonValue(webConfig.getId(), jsonValue);
        return success ? Result.success() : Result.error();
    }
}