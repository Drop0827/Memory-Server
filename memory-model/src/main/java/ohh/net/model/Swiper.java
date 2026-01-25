package ohh.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("swiper")
public class Swiper {
    @TableId(type = IdType.AUTO)
    @Schema(description = "轮播图ID")
    private Integer id;
    @Schema(description = "轮播图标题", example = "这是一个轮播图", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "轮播图", example = "http://127.0.0.1:5000/1.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String image;
    @Schema(description = "轮播图描述", example = "这是一个轮播图的描述")
    private String description;
    @Schema(description = "轮播图链接", example = "/")
    private String url;
}
