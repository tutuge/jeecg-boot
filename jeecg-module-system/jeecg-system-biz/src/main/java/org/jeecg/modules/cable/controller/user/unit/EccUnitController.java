package org.jeecg.modules.cable.controller.user.unit;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.unit.vo.UnitVo;
import org.jeecg.modules.cable.entity.user.EccUnit;
import org.jeecg.modules.cable.model.user.EccUnitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@ApiSupport(order =590)
@Tag(name = "默认单位管理")
@RestController
public class EccUnitController {
    @Resource
    EccUnitModel eccUnitModel;

    @Operation(summary = "获取单位回显数据")
    @PostMapping({"/ecableErpPc/eccUnit/getObject"})
    public Result<EccUnit> getObject(HttpServletRequest request) {
        return Result.ok(eccUnitModel.getObject(request));
    }

    @Operation(summary = "获取默认单位列表")
    @PostMapping({"/ecableErpPc/eccUnit/getList"})
    public Result<UnitVo> getList(HttpServletRequest request) {
        return Result.ok(eccUnitModel.getList(request));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/eccUnit/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(eccUnitModel.deal(request));
    }


    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/eccUnit/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(eccUnitModel.start(request));
    }


    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/eccUnit/sort"})
    public Result<?> sort(HttpServletRequest request) {
        eccUnitModel.sort(request);
        return Result.ok();
    }


    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/eccUnit/delete"})
    public Result<?> delete(HttpServletRequest request) {
         eccUnitModel.delete(request);
        return Result.ok();
    }
}
