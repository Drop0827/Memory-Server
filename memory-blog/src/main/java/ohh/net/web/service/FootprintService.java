package ohh.net.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ohh.net.model.Footprint;
import ohh.net.vo.FilterVo;

import java.util.List;

public interface FootprintService extends IService<Footprint> {
    List<Footprint> list(FilterVo filterVo);
}
