package ohh.net.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ohh.net.model.Article;
import ohh.net.service.ArticleService;
import ohh.net.web.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
