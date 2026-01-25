package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("wall")
public class Wall extends BaseModel {
    @Schema(description = "留言人名称", example = "神秘人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "分类id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cateId;

    @TableField(exist = false)
    @Schema(description = "留言分类", example = "全部")
    private WallCate cate;

    @Schema(description = "留言墙颜色", example = "#92e6f54d")
    private String color;

    @Schema(description = "留言内容", example = "这是一段内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "邮箱", example = "3311118881@qq.com")
    private String email;

    @Schema(description = "评论是否审核通过", example = "1")
    private Integer auditStatus;

    @Schema(description = "设置与取消精选", example = "1")
    private Integer isChoice;
}
