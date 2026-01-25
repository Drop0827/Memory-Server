package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.dto.article.ArticleFormDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("article")
@Schema(description = "文章实体")
public class Article extends ArticleFormDTO {
    @Schema(description = "文章浏览量")
    private Integer view;

    @Schema(description = "文章评论数量")
    private Integer comment;

    @TableField(exist = false)
    @Schema(description = "分类列表")
    private List<Cate> cateList = new ArrayList<>();

    @TableField(exist = false)
    @Schema(description = "标签列表")
    private List<Tag> tagList = new ArrayList<>();

    @TableField(exist = false)
    private Map<String, Object> prev;

    @TableField(exist = false)
    private Map<String, Object> next;
}