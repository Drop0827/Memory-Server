package ohh.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BaseModel {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @Schema(description = "创建时间", example = "1723533206613", requiredMode = Schema.RequiredMode.REQUIRED)
    private String createTime;
}
