package org.jeecg.modules.cable.controller.efficiency;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.efficiency.bo.PccBo;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.model.efficiency.EcduPccModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSort(10021)
@Tag(name = "获取省份对应信息")
@RestController
public class EcduPccController {
    @Resource
    EcduPccModel ecduPccModel;

    @Operation(summary = "获取省份对应信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecduPcc/getObject"})
    public Result<List<EcProvince>> getObject(@RequestBody PccBo bo, HttpServletRequest request) {
        return Result.ok(ecduPccModel.getObject(bo, request));
    }
}
