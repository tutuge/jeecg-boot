package org.jeecg.modules.cable.controller.price.input;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.price.input.bo.*;
import org.jeecg.modules.cable.controller.price.input.vo.InputListVo;
import org.jeecg.modules.cable.controller.price.input.vo.InputStructureVo;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.model.price.EcuqInputModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "报价管理页面接口--用户接口", description = "报价管理页面接口--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "1", parseValue = true)})})
@RestController
@Validated
@RequestMapping("/ecableErpPc/ecuqInput")
public class EcuqInputController {
    @Resource
    EcuqInputModel ecuqInputModel;

    @Operation(summary = "每行数据编辑提交")
    @PostMapping({"/deal"})
    public Result<EcuqInput> deal(@Validated @RequestBody InputDealBo bo) {
        return Result.ok(ecuqInputModel.deal(bo));
    }


    @PostMapping({"/getObject"})
    public Result<EcuqInput> getObject(@RequestBody InputBaseBo bo) {
        return Result.ok(ecuqInputModel.getObject(bo));
    }

    @Operation(summary = "获取报价单内每列数据")
    @PostMapping({"/getListQuoted"})
    public Result<InputListVo> getListQuoted(@Validated @RequestBody InputListBo bo) {
        return Result.ok(ecuqInputModel.getListQuoted(bo));
    }


    @Operation(summary = "删除列表信息")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody InputBaseBo bo) {
        ecuqInputModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "根据ecuqild获取")
    @PostMapping({"/getStructurePassId"})
    public Result<InputStructureVo> getStructurePassId(@RequestBody InputBaseBo bo) {
        return Result.ok(ecuqInputModel.getStructurePassId(bo));
    }

    @Operation(summary = "获取编辑结构临时数据")
    @PostMapping({"/getStructureTemporary"})
    public Result<InputStructureVo> getStructureTemporary(@RequestBody InputStructBo bo) {
        return Result.ok(ecuqInputModel.getStructureTemporary(bo));
    }

    @Operation(summary = "批量修改实际税率")
    @PostMapping({"/dealBatchBillPercent"})
    public Result<?> dealBatchBillPercent(@Validated @RequestBody InputBatchDealBo bo) {
        ecuqInputModel.dealBatchBillPercent(bo);
        return Result.ok();
    }

    @Operation(summary = "排序")
    @PostMapping({"/dealSort"})
    public Result<?> dealSort(@RequestBody InputSortBo bo) {
        ecuqInputModel.dealSort(bo);
        return Result.ok();
    }

    @Operation(summary = "备注添加")
    @PostMapping({"/dealItemDesc"})
    public Result<?> dealItemDesc(@RequestBody InputItemDescBo bo) {
        ecuqInputModel.dealItemDesc(bo);
        return Result.ok();
    }


    @Operation(summary = "利润手输更改")
    @PostMapping({"/dealProfitInput"})
    public Result<?> dealProfitInput(@RequestBody InputProfitBo bo) {
        ecuqInputModel.dealProfitInput(bo);
        return Result.ok();
    }

    @Operation(summary = "根据丝型号获取默认的质量等级")
    @PostMapping({"/getObjectPassSilkName"})
    public Result<Integer> getObjectPassSilkName(@RequestBody InputSilkNameBo bo) {
        return Result.ok(ecuqInputModel.getObjectPassSilkName(bo));
    }

    @Operation(summary = "导入报价单")
    @PostMapping({"/importData"})
    public Result<String> importData(MultipartFile file, @Validated @RequestBody InputImportBo bo) {
        ecuqInputModel.importData(file, bo);
        return Result.ok();
    }

    @Operation(summary = "修改丝名称别名")
    @PostMapping({"/dealSilkNameAs"})
    public Result<String> dealSilkNameAs(@RequestBody InputSilkNameAsBo bo) {
        ecuqInputModel.dealSilkNameAs(bo);
        return Result.ok();
    }

    @Operation(summary = "修改丝名称别名")
    @PostMapping({"/dealAreaStrAs"})
    public Result<String> dealAreaStrAs(@RequestBody InputAreaStrAsBo bo) {
        ecuqInputModel.dealAreaStrAs(bo);
        return Result.ok();
    }

    @Operation(summary = "修改丝名称是否手输")
    @PostMapping({"/dealSilkNameInput"})
    public Result<?> dealSilkNameInput(@RequestBody InputBo bo) {
        ecuqInputModel.dealSilkNameInput(bo);
        return Result.ok();
    }

    @Operation(summary = "修改截面是否手输")
    @PostMapping({"/dealAreaStrInput"})
    public Result<?> dealAreaStrInput(@RequestBody InputBo bo) {
        ecuqInputModel.dealAreaStrInput(bo);
        return Result.ok();
    }

}
