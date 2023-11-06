package org.jeecg.modules.cable.controller.user.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EcuLogin;

@Schema(description = "用户注册")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EcuUserRegisterVo {

    @Schema(description = "用户信息")
    private EcUser ecUser;

    @Schema(description = "用户登录信息")
    private EcuLogin ecuLogin;

}
