package org.jeecg.modules.system.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * 登录表单
 */
@Data
@Tag(name = "微信登录对象", description = "微信登录对象")
public class WxLoginModel {
    @Schema(description = "账号")
    private String username;
    @Schema(description = "密码")
    private String password;

}
