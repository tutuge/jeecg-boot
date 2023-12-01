package org.jeecg.modules.cable.controller.user.notice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.notice.bo.*;
import org.jeecg.modules.cable.controller.user.notice.vo.NoticeVo;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "报价说明", description = "报价说明",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "989", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecuNotice")
public class EcuNoticeController {
    @Resource
    EcuNoticeModel ecuNoticeModel;

    @Operation(summary = "备注管理编辑时回显")
    @PostMapping({"/getObject"})
    public Result<EcuNotice> getObject(@RequestBody EcuNoticeBo bo) {
        return Result.ok(ecuNoticeModel.getObject(bo));
    }


    @Operation(summary = "获取备注管理列表")
    @PostMapping({"/getList"})
    public Result<NoticeVo> getList(@RequestBody EcuNoticePageBo bo) {
        return Result.ok(ecuNoticeModel.getList(bo));
    }


    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcuNoticeDealBo bo) {
        return Result.ok(ecuNoticeModel.deal(bo));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcuNoticeStartBo bo) {
        return Result.ok(ecuNoticeModel.start(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody EcuNoticeSortBo bo) {
        ecuNoticeModel.sort(bo);
        return Result.ok();
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcuNoticeStartBo bo) {
        ecuNoticeModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "设置默认")
    //设置默认
    @PostMapping({"/defaultType"})
    public Result<?> defaultType(@RequestBody EcuNoticeStartBo bo) {
        ecuNoticeModel.defaultType(bo);
        return Result.ok();
    }
}
