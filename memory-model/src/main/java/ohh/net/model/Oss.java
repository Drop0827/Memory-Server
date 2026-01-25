package ohh.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("oss")
public class Oss {
    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "存储平台")
    private String platform;

    @Schema(description = "平台名称")
    @TableField(exist = false)
    private String platformName;

    @Schema(description = "Access Key")
    private String accessKey;

    @Schema(description = "Secret Key")
    private String secretKey;

    @Schema(description = "地域")
    private String endPoint;

    @Schema(description = "存储桶")
    private String bucketName;

    @Schema(description = "域名")
    private String domain;

    @Schema(description = "文件目录")
    private String basePath;

    /**
     * 是否启用 0:禁用 1：启用
     */
    @Schema(description = "是否启用 0:禁用 1：启用")
    private Integer isEnable;
}
