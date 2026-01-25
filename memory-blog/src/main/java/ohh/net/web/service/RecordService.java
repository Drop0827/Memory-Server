package ohh.net.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ohh.net.model.Record;
import ohh.net.vo.FilterVo;
import ohh.net.vo.PageVo;

import java.util.List;

public interface RecordService extends IService<Record> {
    List<Record> list(FilterVo filterVo);
    Page<Record> paging(FilterVo filterVo, PageVo pageVo);
}
