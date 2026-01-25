package ohh.net.dto.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentFormDTO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "评论者名称", example = "宇阳", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "评论者头像", example = "yuyang.jpg")
    private String avatar;

    @Schema(description = "评论者邮箱", example = "liuyuyang1024@yeah.net")
    private String email;

    @Schema(description = "评论者网站", example = "https://blog.liuyuyang.net")
    private String url;

    @Schema(description = "评论内容", example = "这是一段评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "该评论所绑定的文章ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer articleId;

    @Schema(description = "二级评论", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer commentId;

    @Schema(description = "评论是否审核通过", example = "1")
    private Integer auditStatus;

    @Schema(description = "创建时间", example = "1723533206613", requiredMode = Schema.RequiredMode.REQUIRED)
    private String createTime;
}
