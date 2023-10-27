package org.jeecg.modules.cable.controller.systemEcable.silk;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkSortBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "型号管理--系统接口", description = "型号管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "301", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecSilk")
public class EcSilkController {
    @Resource
    EcSilkModel ecSilkModel;
    @Resource
    EcSilkService ecSilkService;

    @Operation(summary = "型号管理-分页列表查询", description = "型号管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(EcSilk ecSilk,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<EcSilk> queryWrapper = QueryGenerator.initQueryWrapper(ecSilk, req.getParameterMap());
        Page<EcSilk> page = new Page<>(pageNo, pageSize);
        IPage<EcSilk> pageList = ecSilkService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    @Operation(summary = "型号管理-添加", description = "型号管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@Validated(AddGroup.class) @RequestBody EcSilk ecSilk) {
        ecSilkModel.save(ecSilk);
        return Result.OK("添加成功！");
    }


    @Operation(summary = "获取丝型号")
    // 根据startType获取信息列表
    @PostMapping({"/getList"})
    public Result<List<EcSilk>> getList(@RequestBody EcbSilkBo bo) {
        return Result.ok(ecSilkModel.getList(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbSilkSortBo> bos) {
        ecSilkModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbSilkBaseBo bo) {
        return Result.ok(ecSilkModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbSilkBaseBo bo) {
        ecSilkModel.delete(bo);
        return Result.ok();
    }


    // @Operation(summary = "根据silkName获取丝列号列表")
    // // 根据silkName获取数据列表列表
    // @PostMapping({"/getListPassSilkName"})
    // public Result<List<EcSilk>> getListPassSilkName(@RequestBody EcbSilkStartBo bo) {
    //     return Result.ok(ecSilkModel.getListPassSilkName(bo));
    // }
    //
    // @Operation(summary = "获取所有丝型号列表")
    // // 获取数据列表列表
    // @PostMapping({"/getListSilkName"})
    // public Result<List<EcSilk>> getListSilkName(@RequestBody EcbSilkBo bo) {
    //     return Result.ok(ecSilkModel.getListSilkName(bo));
    // }
}
