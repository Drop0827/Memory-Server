package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.dto.comment.CommentFormDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("comment")
public class Comment extends CommentFormDTO {
    @Schema(description = "该评论所属的文章名称")
    @TableField(exist = false)
    private String articleTitle;

    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Comment> children = new ArrayList<>();
}
