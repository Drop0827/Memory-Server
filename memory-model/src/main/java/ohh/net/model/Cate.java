package ohh.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.dto.cate.CateFormDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cate")
@Schema(description = "分类实体对象")
public class Cate extends CateFormDTO {

    @TableField(exist = false)
    @Schema(description = "子分类列表（树形结构）")
    private List<Cate> children = new ArrayList<>();
}