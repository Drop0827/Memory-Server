package ohh.net.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.common.annotation.NoTokenRequired;
import ohh.net.common.annotation.RateLimit;
import ohh.net.model.Record;
import ohh.net.common.utils.Result;
import ohh.net.web.service.RecordService;
import ohh.net.common.utils.Paging;
import ohh.net.vo.FilterVo;
import ohh.net.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "说说管理")
@RestController
@RequestMapping("/record")
@Transactional
public class RecordController {
    @Resource
    private RecordService recordService;

    @PostMapping
    @Operation(summary = "新增说说")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Record record) {
        // NOTE: 数据库 images 字段为 JSON 类型，空字符串不是有效的 JSON，需要转为空数组
        if (record.getImages() == null || record.getImages().isEmpty()) {
            record.setImages("[]");
        }
        recordService.save(record);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除说说")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        recordService.removeById(id);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑说说")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Record record) {
        // NOTE: 数据库 images 字段为 JSON 类型，空字符串不是有效的 JSON，需要转为空数组
        if (record.getImages() == null || record.getImages().isEmpty()) {
            record.setImages("[]");
        }
        recordService.updateById(record);
        return Result.success();
    }

    @RateLimit
    @GetMapping("/{id}")
    @Operation(summary = "获取说说")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Record> get(@PathVariable Integer id) {
        Record data = recordService.getById(id);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/list")
    @Operation(summary = "获取说说列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Record>> list(@RequestBody FilterVo filterVo) {
        List<Record> data = recordService.list(filterVo);
        return Result.success(data);
    }

    @RateLimit
    @NoTokenRequired
    @PostMapping("/paging")
    @Operation(summary = "分页查询说说列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<Map<String, Object>> paging(@RequestBody FilterVo filterVo, PageVo pageVo) {
        Page<Record> data = recordService.paging(filterVo, pageVo);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }
}
