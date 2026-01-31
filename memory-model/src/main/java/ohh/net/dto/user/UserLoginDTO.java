package ohh.net.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserLoginDTO {
    @Schema(description = "用户账号", example = "liuyuyang", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
