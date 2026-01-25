package ohh.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("article_config")
@Schema(description = "文章配置信息")
public class ArticleConfig {

    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "文章状态", example = "默认（default） 不在首页显示（no_home） 全站隐藏（hide）")
    private String status;

    @Schema(description = "文章密码", example = "123456")
    private String password;

    @Schema(description = "是否为文章草稿", example = "0-发布, 1-草稿")
    private Integer isDraft;

    @Schema(description = "是否为加密文章", example = "0-不加密, 1-加密")
    private Integer isEncrypt;

    @Schema(description = "是否严格删除", example = "0-逻辑删除, 1-物理删除")
    private Integer isDel;

    @Schema(description = "文章ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer articleId;
}