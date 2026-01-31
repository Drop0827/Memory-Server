package ohh.net.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import ohh.net.common.utils.Result;
import ohh.net.model.EnvConfig;
import ohh.net.web.service.EnvConfigService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "环境配置管理")
@RestController
@RequestMapping("/env_config")
public class EnvConfigController {
    @Resource
    private EnvConfigService envConfigService;

    @Operation(summary = "获取环境配置列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    @GetMapping("/list")
    public Result<List<EnvConfig>> list() {
        List<EnvConfig> data = envConfigService.list();
        return Result.success("获取成功", data);
    }

    @Operation(summary = "根据ID获取环境配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    @GetMapping("/{id}")
    public Result<EnvConfig> getById(
            @Parameter(description = "环境配置ID", required = true, example = "1") @PathVariable Integer id) {
        EnvConfig envConfig = envConfigService.getById(id);
        return envConfig != null ? Result.success("获取成功", envConfig) : Result.error("配置不存在");
    }

    @Operation(summary = "根据名称获取环境配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    @GetMapping("/name/{name}")
    public Result<EnvConfig> getByName(
            @Parameter(description = "配置名称", required = true, example = "database_config") @PathVariable String name) {
        EnvConfig envConfig = envConfigService.getByName(name);
        return envConfig != null ? Result.success("获取成功", envConfig) : Result.error("配置不存在");
    }

    @Operation(summary = "根据ID获取配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    @PatchMapping("/json/{id}")
    public Result<String> updateJsonValue(
            @Parameter(description = "环境配置ID", required = true, example = "1") @PathVariable Integer id,
            @Parameter(description = "JSON配置值", required = true) @RequestBody Map<String, Object> jsonValue) {
        boolean success = envConfigService.updateJsonValue(id, jsonValue);
        return success ? Result.success("JSON配置更新成功") : Result.error("更新失败");
    }

    @Operation(summary = "根据ID更新配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    @PatchMapping("/{id}/field/{fieldName}")
    public Result<String> updateJsonFieldValue(
            @Parameter(description = "环境配置ID", required = true, example = "1") @PathVariable Integer id,
            @Parameter(description = "字段名称", required = true, example = "host") @PathVariable String fieldName,
            @Parameter(description = "字段值", required = true) @RequestBody Object value) {
        boolean success = envConfigService.updateJsonFieldValue(id, fieldName, value);
        return success ? Result.success() : Result.error();
    }

    @Operation(summary = "获取高德地图配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    @GetMapping("/gaode_map")
    public Result<Map<String, Object>> getGaodeMapConfig() {
        EnvConfig envConfig = envConfigService.getByName("gaode_map");
        return Result.success(envConfig.getValue());
    }
}