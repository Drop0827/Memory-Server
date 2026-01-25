package ohh.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("assistant")
@Schema(description = "AI助手实体")
public class Assistant {

    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "助手名称", example = "DeepSeek", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * key 是数据库关键字，使用反引号包裹
     */
    @TableField("`key`")
    @Schema(description = "API 密钥", example = "sk-xxxxxxxxxxxxxxxx")
    private String key;

    @Schema(description = "API 地址", example = "https://api.deepseek.com")
    private String url;

    @Schema(description = "API 模型", example = "deepseek-chat")
    private String model;

    @Schema(description = "是否为默认助手", example = "0-否, 1-是")
    private Integer isDefault = 0;
}