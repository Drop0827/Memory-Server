package ohh.net.vo.link;

import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.vo.FilterVo;
import lombok.Data;

@Data
public class LinkFilterVo extends FilterVo {
    @Schema(description = "0表示获取待审核的友联 | 1表示获取审核通过的友联（默认）")
    private Integer status = 1;
}
