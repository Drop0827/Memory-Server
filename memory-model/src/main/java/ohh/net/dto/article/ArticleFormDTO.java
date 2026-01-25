package ohh.net.dto.article; // 确认包名

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema; // 换成 v3
import ohh.net.model.ArticleConfig; // 引用你自己的 ohh.net
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "文章表单对象")
public class ArticleFormDTO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文章标题", example = "示例标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "文章介绍")
    private String description;

    @Schema(description = "文章主要内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "文章封面链接")
    private String cover;

    // --- 核心：必须告诉 MyBatis-Plus 数据库里没这些字段 ---
    @TableField(exist = false)
    @Schema(description = "绑定的分类ID")
    private List<Integer> cateIds;

    @TableField(exist = false)
    @Schema(description = "绑定的标签ID")
    private List<Integer> tagIds;

    @TableField(exist = false)
    @Schema(description = "文章配置项")
    private ArticleConfig config;
    // ---------------------------------------------

    @Schema(description = "创建时间", example = "1723533206613")
    private String createTime;
}