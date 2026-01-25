package ohh.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("tag")
@Schema(description = "标签实体对象")
public class Tag {

    @TableId(type = IdType.AUTO)
    @Schema(description = "标签ID")
    private Integer id;

    @Schema(description = "标签名称", example = "Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @TableField(exist = false)
    @Schema(description = "该标签关联的文章数量")
    private Integer count;
}