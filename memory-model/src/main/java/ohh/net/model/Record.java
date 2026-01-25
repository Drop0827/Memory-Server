package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("record")
public class Record extends BaseModel {
    @Schema(description = "内容", example = "大前端永远滴神！", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
    @Schema(description = "图片", example = "[]")
    private String images;
}
