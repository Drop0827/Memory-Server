package ohh.net.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ohh.net.model.Link;
import ohh.net.vo.PageVo;
import ohh.net.vo.link.LinkFilterVo;

import java.util.List;

public interface LinkService extends IService<Link> {
    void add(Link link, String token) throws Exception;

    Link get(Integer cid);

    List<Link> list(LinkFilterVo filterVo);

    Page<Link> paging(LinkFilterVo filterVo, PageVo pageVo);
}
