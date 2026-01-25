package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "footprint", autoResultMap = true)
public class Footprint extends BaseModel {
    @Schema(description = "标题", example = "这是一个标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "地址", example = "这是一个地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;
    @Schema(description = "内容", example = "这是一段内容")
    private String content;
    @Schema(description = "坐标纬度", example = "116.413782,39.902957", requiredMode = Schema.RequiredMode.REQUIRED)
    private String position;
    @TableField(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "图片", example = "[]")
    private List<String> images;
}
