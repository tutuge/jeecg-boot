package org.jeecg.modules.cable.controller.systemEcable.silk;

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
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkEditBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkSortBo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.model.systemEcable.EcSilkServiceModel;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "型号系列管理--系统接口", description = "型号系列管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "301", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecSilk")
public class EcSilkController {
    @Resource
    EcSilkServiceModel ecSilkServiceModel;
    @Resource
    EcSilkService ecSilkService;

    @Operation(summary = "型号管理-分页列表查询", description = "型号管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(EcSilk ecSilk,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<EcSilk> page = new Page<>(pageNo, pageSize);
        IPage<EcSilk> pageList = ecSilkService.selectPage(page, ecSilk);
        return Result.OK(pageList);
    }


    @Operation(summary = "根据主键查询", description = "根据主键查询")
    @PostMapping(value = "/getObject")
    public Result<EcSilk> getObject(@RequestBody EcbSilkBaseBo bo) {
        EcSilk ecSilk = new EcSilk();
        ecSilk.setEcsId(bo.getEcsId());
        EcSilk object = ecSilkService.getObject(ecSilk);
        return Result.OK(object);
    }


    @Operation(summary = "型号管理-添加", description = "型号管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@Validated(AddGroup.class) @RequestBody EcSilk ecSilk) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecSilk.setEcaId(sysUser.getUserId());
        ecSilk.setEcaName(sysUser.getUsername());
        ecSilkServiceModel.save(ecSilk);
        return Result.OK("添加成功！");
    }

    @Operation(summary = "型号管理-修改", description = "型号管理-修改")
    @PostMapping(value = "/edit")
    public Result<?> edit(@Validated(EditGroup.class) @RequestBody EcbSilkEditBo ecSilk) {
        EcSilk ec = new EcSilk();
        BeanUtils.copyProperties(ecSilk, ec);
        ec.setUpdateTime(new Date());
        ecSilkService.updateById(ec);
        return Result.OK("修改成功！");
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbSilkSortBo> bos) {
        ecSilkServiceModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbSilkBaseBo bo) {
        return Result.ok(ecSilkServiceModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbSilkBaseBo bo) {
        ecSilkServiceModel.delete(bo);
        return Result.ok();
    }
}
