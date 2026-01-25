package ohh.net.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ohh.net.model.Article;
import ohh.net.model.Cate;
import ohh.net.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
