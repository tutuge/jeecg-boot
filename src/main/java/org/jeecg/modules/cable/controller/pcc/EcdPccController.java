package org.jeecg.modules.cable.controller.pcc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.pcc.bo.EcdPccBo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.model.efficiency.EcdPccModel;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "省市县地址接口--基础资料接口", description = "省市县地址接口--基础资料接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "22", parseValue = true)})})
@RestController
@Slf4j
public class EcdPccController {
    @Resource
    EcdPccModel ecdPccModel;

    @Operation(summary = "省市县加载")
    @PostMapping({"/ecableErpPc/ecdPcc/load"})
    public Result<?> load(@RequestBody EcdPccBo bo) {
        ecdPccModel.load(bo);
        return Result.ok();
    }
}
