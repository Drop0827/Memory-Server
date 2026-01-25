package ohh.net.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.vo.FilterVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleFillterVo extends FilterVo {
    @Schema(description = "根据分类进行筛选")
    private Integer cateId;
    @Schema(description = "根据标签进行筛选")
    private Integer tagId;
    @Schema(description = "是否为草稿, 默认：0 | 草稿：1", example = "0")
    private Integer isDraft = 0;
    @Schema(description = "是否为严格删除, 默认：0 | 严格删除：1", example = "0")
    private Integer isDel = 0;
}
