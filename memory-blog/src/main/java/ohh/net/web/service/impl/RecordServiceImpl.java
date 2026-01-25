package ohh.net.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ohh.net.web.mapper.RecordMapper;
import ohh.net.model.Record;
import ohh.net.web.service.RecordService;
import ohh.net.common.utils.OhhUtils;
import ohh.net.vo.FilterVo;
import ohh.net.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private OhhUtils ohhUtils;

    @Override
    public List<Record> list(FilterVo filterVo) {
        QueryWrapper<Record> queryWrapper = ohhUtils.queryWrapperFilter(filterVo, "content");
        List<Record> list = recordMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Page<Record> paging(FilterVo filterVo, PageVo pageVo) {
        List<Record> list = list(filterVo);
        return ohhUtils.getPageData(pageVo, list);
    }
}