package ohh.net.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ohh.net.model.ArticleTag;
import ohh.net.web.mapper.ArticleTagMapper;
import ohh.net.web.service.ArticleTagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
