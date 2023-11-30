package org.jeecg.modules.cable.controller.systemCommon.store;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.EcbStoreBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.EcbStoreDealBo;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.EcbStoreSortBo;
import org.jeecg.modules.cable.controller.systemCommon.store.bo.StoreBo;
import org.jeecg.modules.cable.controller.systemCommon.store.vo.StoreVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbStore;
import org.jeecg.modules.cable.model.systemCommon.EcbStoreModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "仓库管理--系统接口", description = "仓库管理--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "111", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbStore")
public class EcbStoreController {

    @Resource
    EcbStoreModel ecbStoreModel;

    @Operation(summary = "获取仓库列表")
    @PostMapping({"/getList"})
    public Result<StoreVo> getList(@RequestBody StoreBo bo) {
        return Result.ok(ecbStoreModel.getListAndCount(bo));
    }

    @Operation(summary = "获取仓库")
    @PostMapping({"/getObject"})
    public Result<EcbStore> getObject(@Validated @RequestBody EcbStoreBaseBo bo) {
        return Result.ok(ecbStoreModel.getObject(bo));
    }


    @Operation(summary = "编辑仓库")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcbStoreDealBo bo) {
        return Result.ok(ecbStoreModel.deal(bo));
    }

    @Operation(summary = "仓库排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbStoreSortBo> boList) {
        ecbStoreModel.sort(boList);
        return Result.ok();
    }

    @Operation(summary = "删除仓库")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbStoreBaseBo bo) {
        ecbStoreModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "设置默认仓库")
    @PostMapping({"/dealDefault"})
    public Result<?> defaultType(@Validated @RequestBody EcbStoreBaseBo bo) {
        ecbStoreModel.dealDefault(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbStoreBaseBo bo) {
        return Result.ok(ecbStoreModel.start(bo));
    }
}
