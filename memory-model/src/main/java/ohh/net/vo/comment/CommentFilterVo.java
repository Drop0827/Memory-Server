package ohh.net.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.vo.FilterVo;
import lombok.Data;

import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommentFilterVo extends FilterVo {
    @Schema(description = "默认为树形结构，如果设置了list模式，则查询列表结构")
    private String pattern;

    @Schema(description = "0表示获取待审核的评论 | 1表示获取审核通过的评论（默认）")
    private Integer status = 1;

    @Schema(description = "根据内容关键词筛选")
    private String content;
}
