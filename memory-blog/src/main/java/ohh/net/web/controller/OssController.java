package ohh.net.web.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.model.Oss;
import ohh.net.common.utils.Result;
import ohh.net.web.service.OssService;
import ohh.net.vo.oss.OssVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "存储管理")
@RestController
@RequestMapping("/oss")
@AllArgsConstructor
public class OssController {
    private final OssService ossService;

    @PostMapping
    @Operation(summary = "新增oss配置")
    @ApiOperationSupport(author = "laifeng", order = 1)
    public Result<String> add(@RequestBody Oss oss) {
        ossService.saveOss(oss);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除oss配置")
    @ApiOperationSupport(author = "laifeng", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Oss oss = ossService.getById(id);
        if (oss == null) return Result.error("删除oss配置失败：该配置不存在");
        if (oss.getIsEnable() == 1) return Result.error("删除oss配置失败：该配置正在使用中");
        ossService.delOss(id);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "更新oss配置")
    @ApiOperationSupport(author = "laifeng", order = 3)
    public Result<String> update(@RequestBody Oss oss) {
        ossService.updateOss(oss);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取oss配置")
    @ApiOperationSupport(author = "laifeng", order = 4)
    public Result<OssVo> get(@PathVariable Integer id) {
        Oss oss = ossService.getById(id);
        if (oss == null) {
            return Result.error("获取oss配置失败：该配置不存在");
        }
        OssVo vo = new OssVo();
        BeanUtils.copyProperties(oss, vo);
        if ("local".equals(vo.getPlatform())) {
            vo.setProjectPath(System.getProperty("user.dir"));
        }
        return Result.success(vo);
    }

    @PostMapping("/list")
    @Operation(summary = "获取oss配置列表")
    @ApiOperationSupport(author = "laifeng", order = 5)
    public Result<Object> page() {
        List<Oss> list = ossService.list();
        return Result.success(list);
    }

    @PatchMapping("/enable/{id}")
    @Operation(summary = "启用oss配置")
    @ApiOperationSupport(author = "laifeng", order = 6)
    public Result enable(@PathVariable Integer id) {
        ossService.enable(id);
        return Result.success();
    }

    @GetMapping("/getEnableOss")
    @Operation(summary = "获取当前启用的oss配置")
    @ApiOperationSupport(author = "laifeng", order = 8)
    public Result<Oss> getEnableOss() {
        return Result.success(ossService.getEnableOss());
    }

    @GetMapping("/platform")
    @Operation(summary = "获取目前支持的oss平台")
    @ApiOperationSupport(author = "宇阳", order = 9)
    public Result<List<Map>> getPlatform() {
        return Result.success(ossService.getPlatform());
    }
}