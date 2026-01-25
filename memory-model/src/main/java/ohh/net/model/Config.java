package ohh.net.model;

import com.baomidou.mybatisplus.annotation.*; // 建议直接用 * 包含所有
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
// 重点：必须加上 autoResultMap = true，否则 JSON 映射会失效
@TableName(value = "env_config", autoResultMap = true)
@Schema(description = "配置实体")
public class Config {

    @TableId(type = IdType.AUTO)
    @Schema(description = "环境配置ID")
    private Integer id;

    @Schema(description = "配置名称", example = "database_config", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    // MyBatis-Plus 会自动将数据库里的 JSON 字符串转为 Java 的 Map
    @TableField(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "配置值(JSON格式)", example = "{\"name\":\"OHH\"}", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> value;

    @Schema(description = "配置备注")
    private String notes;
}