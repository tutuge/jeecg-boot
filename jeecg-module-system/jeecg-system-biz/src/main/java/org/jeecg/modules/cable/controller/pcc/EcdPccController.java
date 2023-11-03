package org.jeecg.modules.cable.controller.pcc;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.pcc.bo.EcdPccBo;
import org.jeecg.modules.cable.model.efficiency.EcdPccModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EcdPccController {
    @Resource
    EcdPccModel ecdPccModel;

    //load
    @PostMapping({"/ecableErpPc/ecdPcc/load"})
    public Result<?> load(@RequestBody EcdPccBo bo) {
        ecdPccModel.load(bo);
        return Result.ok();
    }
}
