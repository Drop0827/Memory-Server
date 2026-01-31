package ohh.net.vo.wall;

import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.vo.FilterVo;
import lombok.Data;

import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WallFilterVo extends FilterVo {
    @Schema(description = "根据分类进行筛选")
    private Integer cateId;

    @Schema(description = "0表示获取待审核的留言 | 1表示获取审核通过的留言（默认）")
    private Integer status = 1;
}
