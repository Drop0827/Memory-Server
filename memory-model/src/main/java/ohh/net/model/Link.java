package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("link")
public class Link extends BaseModel {
    @Schema(description = "网站标题", example = "这是一个网站", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "网站描述", example = "这是一个网站的描述", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
    @Schema(description = "网站邮箱", example = "liuyuyang1024@yeah.net")
    private String email;
    @Schema(description = "网站类型", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer typeId;
    @TableField(exist = false)
    private LinkType type;
    @Schema(description = "网站图片", example = "http://127.0.0.1:5000/1.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String image;
    @Schema(description = "网站链接", example = "/", requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;
    @Schema(description = "订阅地址", example = "/")
    private String rss;
    @Schema(description = "评论是否审核通过", example = "1")
    private Integer auditStatus;
    @TableField("`order`")
    @Schema(description = "网站顺序", example = "1")
    private Integer order;
}
