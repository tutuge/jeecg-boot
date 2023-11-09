package org.jeecg.modules.cable.controller.userEcable.silk;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.validate.AddGroup;
import org.jeecg.common.validate.EditGroup;
import org.jeecg.modules.cable.controller.userEcable.silk.bo.EcubSilkBaseBo;
import org.jeecg.modules.cable.controller.userEcable.silk.bo.EcubSilkEditBo;
import org.jeecg.modules.cable.controller.userEcable.silk.bo.EcubSilkSortBo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.model.userEcable.EcuSilkServiceModel;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "型号类型管理--用户接口", description = "型号类型管理--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "302", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecSilk")
public class EcuSilkController {
    @Resource
    EcuSilkServiceModel ecuSilkServiceModel;
    @Resource
    EcuSilkService ecuSilkService;

    @Operation(summary = "型号管理-分页列表查询", description = "型号管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(EcuSilk ecuSilk,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<EcuSilk> page = new Page<>(pageNo, pageSize);
        IPage<EcuSilk> pageList = ecuSilkService.selectPage(page, ecuSilk);
        return Result.OK(pageList);
    }


    @Operation(summary = "根据主键查询", description = "根据主键查询")
    @PostMapping(value = "/getObject")
    public Result<EcuSilk> getObject(@RequestBody EcubSilkBaseBo bo) {
        EcuSilk ecSilk = new EcuSilk();
        ecSilk.setEcusId(bo.getEcusId());
        EcuSilk object = ecuSilkService.getObject(ecSilk);
        return Result.OK(object);
    }


    @Operation(summary = "型号管理-添加", description = "型号管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@Validated(AddGroup.class) @RequestBody EcuSilk ecSilk) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecSilk.setEcuId(sysUser.getUserId());
        ecSilk.setCompanyId(sysUser.getEcCompanyId());
        ecuSilkServiceModel.save(ecSilk);
        return Result.OK("添加成功！");
    }

    @Operation(summary = "型号管理-修改", description = "型号管理-修改")
    @PostMapping(value = "/edit")
    public Result<?> edit(@Validated(EditGroup.class) @RequestBody EcubSilkEditBo ecSilk) {
        EcuSilk ec = new EcuSilk();
        BeanUtils.copyProperties(ecSilk, ec);
        ec.setUpdateTime(new Date());
        ecuSilkService.updateById(ec);
        return Result.OK("修改成功！");
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcubSilkSortBo> bos) {
        ecuSilkServiceModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcubSilkBaseBo bo) {
        return Result.ok(ecuSilkServiceModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcubSilkBaseBo bo) {
        ecuSilkServiceModel.delete(bo);
        return Result.ok();
    }


    //@Operation(summary = "根据silkName获取丝列号列表")
    //@PostMapping({"/getListPassSilkName"})
    // public Result<List<EcuSilk>> getListPassSilkName(@RequestBody EcubSilkStartBo bo) {
    //    return Result.ok(ecuSilkModel.getListPassSilkName(bo));
    //}

    //@Operation(summary = "获取所有丝型号列表")
    //@PostMapping({"/getListSilkName"})
    // public Result<List<EcuSilk>> getListSilkName(@RequestBody EcbSilkBo bo) {
    //    return Result.ok(ecSilkModel.getListSilkName(bo));
    //}
}
