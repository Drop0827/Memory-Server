package ohh.net.vo;

import io.swagger.v3.oas.annotations.media.Schema; // 1. 换成 Schema 导包
import lombok.Data;

@Data
@Schema(description = "分页查询参数")
public class PageVo {

    @Schema(description = "页码", defaultValue = "1", example = "1")
    private Integer page = 1;

    @Schema(description = "每页显示数量", defaultValue = "5", example = "5")
    private Integer size = 5;
}