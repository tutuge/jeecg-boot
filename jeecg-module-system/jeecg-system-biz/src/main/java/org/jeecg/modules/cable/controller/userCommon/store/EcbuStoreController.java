package org.jeecg.modules.cable.controller.userCommon.store;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.store.bo.StoreBo;
import org.jeecg.modules.cable.controller.userCommon.store.vo.StoreVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuStore;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@ApiSort(10111)
@Tag(name = "仓库管理")
@RestController
public class EcbuStoreController {

    @Resource
    EcbuStoreModel ecbuStoreModel;

    @Operation(summary = "获取仓库列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuStore/getList"})
    public Result<StoreVo> getList(@RequestBody StoreBo bo) {
        return Result.ok(ecbuStoreModel.getListAndCount(bo));
    }

    @Operation(summary = "获取仓库")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuStore/getObject"})
    public Result<EcbuStore> getObject(HttpServletRequest request) {
        return Result.ok(ecbuStoreModel.getObject(request));
    }


    @Operation(summary = "编辑仓库")
    //deal
    @PostMapping({"/ecableErpPc/ecbuStore/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecbuStoreModel.deal(request));
    }

    @Operation(summary = "仓库排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuStore/sort"})
    public Result<?> sort(HttpServletRequest request) {
        ecbuStoreModel.sort(request);
        return Result.ok();
    }

    @Operation(summary = "删除仓库")
    //delete
    @PostMapping({"/ecableErpPc/ecbuStore/delete"})
    public Result<?> delete(HttpServletRequest request) throws IOException {
        ecbuStoreModel.delete(request);
        return Result.ok();
    }


    @Operation(summary = "设置默认仓库")
    //dealDefault 设置默认项
    @PostMapping({"/ecableErpPc/ecbuStore/dealDefault"})
    public Result<?> defaultType(HttpServletRequest request) {
        ecbuStoreModel.dealDefault(request);
        return Result.ok();
    }


    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuStore/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecbuStoreModel.start(request));
    }
}
