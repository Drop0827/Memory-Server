package ohh.net.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FilterVo {
    @Schema(description = "根据关键词进行筛选")
    private String key;
    @Schema(description = "根据开始时间进行筛选")
    private String startDate;
    @Schema(description = "根据结束时间进行筛选")
    private String endDate;
}
