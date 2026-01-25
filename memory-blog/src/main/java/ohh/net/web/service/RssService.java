package ohh.net.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ohh.net.model.Rss;
import ohh.net.vo.PageVo;

import java.util.List;

public interface RssService {
    List<Rss> list();
    
    Page<Rss> paging(PageVo pageVo);
}
