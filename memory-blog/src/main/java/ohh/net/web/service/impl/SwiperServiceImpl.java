package ohh.net.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ohh.net.web.mapper.SwiperMapper;
import ohh.net.model.Swiper;
import ohh.net.web.service.SwiperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

@Service
@Transactional
public class SwiperServiceImpl extends ServiceImpl<SwiperMapper, Swiper> implements SwiperService {
    @Resource
    private SwiperMapper SwiperMapper;

    @Override
    public Page<Swiper> list(Integer page, Integer size) {
        QueryWrapper<Swiper> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<Swiper> result = new Page<>(page, size);
        SwiperMapper.selectPage(result, queryWrapper);

        return result;
    }
}