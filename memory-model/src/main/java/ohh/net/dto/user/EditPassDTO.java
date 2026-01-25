package ohh.net.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EditPassDTO {
    @Schema(description = "旧账号", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldUsername;
    @Schema(description = "新账号", example = "thrivex666", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newUsername;
    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;
    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
