package org.jeecg.modules.system.controller.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.modules.system.entity.SysUser;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserBo extends SysUser {

    @Schema(description = "所选角色")
    private String selectedRoles;

    @Schema(description = "所选部门")
    private String selectedDeparts;

    @Schema(description = "填写的公司名称")
    @NotBlank(message = "公司名称不得为空", groups = {AddGroup.class})
    private String companyName;
}
