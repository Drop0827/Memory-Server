package ohh.net.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import ohh.net.model.BaseModel;
import lombok.Data;

@Data
public class UserDTO extends BaseModel {
    @Schema(description = "用户账号", example = "liuyuyang", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "用户名称", example = "宇阳", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
