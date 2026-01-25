package ohh.net.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ohh.net.dto.user.EditPassDTO;
import ohh.net.dto.user.UserDTO;
import ohh.net.dto.user.UserInfoDTO;
import ohh.net.dto.user.UserLoginDTO;
import ohh.net.model.User;
import ohh.net.vo.PageVo;
import ohh.net.vo.user.UserFilterVo;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    User get(Integer id);
    void add(UserDTO data);
    void del(Integer id);
    void delBatch(List<Integer> ids);
    void edit(UserInfoDTO data);
    List<User> list(UserFilterVo filterVo);
    Page<User> paging(UserFilterVo filterVo, PageVo pageVo);
    Map<String, Object> login(UserLoginDTO user);
    void editPass(EditPassDTO data);
    void check(String token);
}
