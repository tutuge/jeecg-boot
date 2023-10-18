package org.jeecg.modules.cable.controller.user.udesc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.udesc.vo.UDescVo;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.model.user.EcuDescModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "报价说明")
@RestController
public class EcuDescController {
    @Resource
    EcuDescModel ecuDescModel;

    @Operation(summary = "获取回显数据")
    @PostMapping({"/ecableErpPc/ecuDesc/getObject"})
    public Result<EcuDesc> getObject(HttpServletRequest request) {
        return Result.ok(ecuDescModel.getObject(request));
    }

    @Operation(summary = "获取报价说明列表")
    @PostMapping({"/ecableErpPc/ecuDesc/getList"})
    public Result<UDescVo> getList(HttpServletRequest request) {
        return Result.ok(ecuDescModel.getList(request));
    }

    @Operation(summary = "编辑数据")
    @PostMapping({"/ecableErpPc/ecuDesc/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecuDescModel.deal(request));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecuDesc/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecuDescModel.start(request));
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecuDesc/sort"})
    public Result<?> sort(HttpServletRequest request) {
        ecuDescModel.sort(request);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecuDesc/delete"})
    public Result<?> delete(HttpServletRequest request) {
        ecuDescModel.delete(request);
        return Result.ok();
    }

    @Operation(summary = "设置默认")
    //设置默认
    @PostMapping({"/ecableErpPc/ecuDesc/defaultType"})
    public Result<?> defaultType(HttpServletRequest request) {
         ecuDescModel.defaultType(request);
        return Result.ok();
    }
}
