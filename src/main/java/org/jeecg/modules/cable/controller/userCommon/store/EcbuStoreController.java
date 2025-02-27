package org.jeecg.modules.cable.controller.userCommon.store;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.store.bo.EcbuStoreBaseBo;
import org.jeecg.modules.cable.controller.userCommon.store.bo.EcbuStoreDealBo;
import org.jeecg.modules.cable.controller.userCommon.store.bo.EcbuStoreSortBo;
import org.jeecg.modules.cable.controller.userCommon.store.bo.StoreBo;
import org.jeecg.modules.cable.controller.userCommon.store.vo.StoreVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "仓库管理--用户接口", description = "仓库管理--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "111", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuStore")
public class EcbuStoreController {

    @Resource
    EcbuStoreModel ecbuStoreModel;

    @Operation(summary = "获取仓库列表")
    @PostMapping({"/getList"})
    public Result<StoreVo> getList(@RequestBody StoreBo bo) {
        return Result.ok(ecbuStoreModel.getListAndCount(bo));
    }

    @Operation(summary = "获取仓库")
    @PostMapping({"/getObject"})
    public Result<EcbuStore> getObject(@Validated @RequestBody EcbuStoreBaseBo bo) {
        return Result.ok(ecbuStoreModel.getObject(bo));
    }


    @Operation(summary = "编辑仓库")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcbuStoreDealBo bo) {
        return Result.ok(ecbuStoreModel.deal(bo));
    }

    @Operation(summary = "仓库排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbuStoreSortBo> boList) {
        ecbuStoreModel.sort(boList);
        return Result.ok();
    }

    @Operation(summary = "删除仓库")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbuStoreBaseBo bo) {
        ecbuStoreModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "设置默认仓库")
    @PostMapping({"/dealDefault"})
    public Result<?> defaultType(@Validated @RequestBody EcbuStoreBaseBo bo) {
        ecbuStoreModel.dealDefault(bo);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbuStoreBaseBo bo) {
        return Result.ok(ecbuStoreModel.start(bo));
    }
}
