package ohh.net.dto.cate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema; // 使用 v3 包
import lombok.Data;

@Data
@Schema(description = "分类表单传输对象")
public class CateFormDTO {

    @TableId(type = IdType.AUTO)
    @Schema(description = "分类ID")
    private Integer id;

    @Schema(description = "分类名称", example = "大前端", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "分类链接", example = "/")
    private String url;

    @Schema(description = "分类标识", example = "dqd", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mark;

    @Schema(description = "分类图标", example = "🎉")
    private String icon;

    @Schema(description = "分类级别", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer level;

    @Schema(description = "类型", example = "cate | nav")
    private String type;

    /**
     * order 是数据库关键字，使用反引号包裹
     * 由于 Cate 继承自此类，保留此注解以确保 MyBatis-Plus 能正确映射
     */
    @TableField("`order`")
    @Schema(description = "分类顺序", example = "1")
    private Integer order;
}