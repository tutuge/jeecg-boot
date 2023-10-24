package org.jeecg.modules.cable.controller.user.udesc;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescPageBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuSortBo;
import org.jeecg.modules.cable.controller.user.udesc.vo.UDescVo;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.model.user.EcuDescModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiSupport(order =580)
@Tag(name = "备注管理")
@RestController
public class EcuDescController {
    @Resource
    EcuDescModel ecuDescModel;

    @Operation(summary = "获取回显数据")
    @PostMapping({"/ecableErpPc/ecuDesc/getObject"})
    public Result<EcuDesc> getObject(@RequestBody EcuDescBo bo) {
        return Result.ok(ecuDescModel.getObject(bo));
    }

    @Operation(summary = "获取报价说明列表")
    @PostMapping({"/ecableErpPc/ecuDesc/getList"})
    public Result<UDescVo> getList(@RequestBody EcuDescPageBo bo) {
        return Result.ok(ecuDescModel.getList(bo));
    }

    @Operation(summary = "编辑数据")
    @PostMapping({"/ecableErpPc/ecuDesc/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecuDescModel.deal(request));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecuDesc/start"})
    public Result<String> start(@RequestBody EcuDescBo bo) {
        return Result.ok(ecuDescModel.start(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecuDesc/sort"})
    public Result<?> sort(@RequestBody EcuSortBo bo) {
        ecuDescModel.sort(bo);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecuDesc/delete"})
    public Result<?> delete(@RequestBody EcuDescBo bo) {
        ecuDescModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "设置默认")
    //设置默认
    @PostMapping({"/ecableErpPc/ecuDesc/defaultType"})
    public Result<?> defaultType(@RequestBody EcuDescBo bo) {
        ecuDescModel.defaultType(bo);
        return Result.ok();
    }
}
