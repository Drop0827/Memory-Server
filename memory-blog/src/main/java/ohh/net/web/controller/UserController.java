package ohh.net.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import ohh.net.dto.user.EditPassDTO;
import ohh.net.dto.user.UserDTO;
import ohh.net.dto.user.UserInfoDTO;
import ohh.net.dto.user.UserLoginDTO;
import ohh.net.model.User;
import ohh.net.common.utils.Result;
import ohh.net.web.service.UserService;
import ohh.net.common.utils.Paging;
import ohh.net.vo.PageVo;
import ohh.net.vo.user.UserFilterVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping
    @Operation(summary = "新增用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody UserDTO user) {
        userService.add(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        userService.del(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result<String> batchDel(@RequestBody List<Integer> ids) {
        userService.delBatch(ids);
        return Result.success();
    }

    @PatchMapping
    @Operation(summary = "编辑用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody UserInfoDTO user) {
        userService.edit(user);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<User> get(@PathVariable Integer id) {
        User data = userService.get(id);
        return Result.success(data);
    }

    @PostMapping("/list")
    @Operation(summary = "获取用户列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<User>> list(@RequestBody UserFilterVo filterVo) {
        List<User> list = userService.list(filterVo);
        return Result.success(list);
    }

    @PostMapping("/paging")
    @Operation(summary = "分页查询用户列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<Map<String, Object>> paging(UserFilterVo filterVo, PageVo pageVo) {
        Page<User> data = userService.paging(filterVo, pageVo);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<Map<String, Object>> login(@RequestBody UserLoginDTO user) {
        Map<String, Object> result = userService.login(user);
        return Result.success("登录成功", result);
    }

    @PatchMapping("/pass")
    @Operation(summary = "修改用户密码")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result<String> editPass(@RequestBody EditPassDTO data) {
        userService.editPass(data);
        return Result.success("密码修改成功");
    }

    @GetMapping("/check")
    @Operation(summary = "校验当前用户Token是否有效")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 10)
    public Result<String> checkPrem(String token) {
        userService.check(token);
        return Result.success();
    }

    @GetMapping("/author")
    @Operation(summary = "获取作者信息")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 11)
    public Result<User> getAuthor() {
        User data = userService.get(1);
        return Result.success(data);
    }
}
