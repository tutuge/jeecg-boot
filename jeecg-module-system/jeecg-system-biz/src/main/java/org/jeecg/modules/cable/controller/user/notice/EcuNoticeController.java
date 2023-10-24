package org.jeecg.modules.cable.controller.user.notice;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.user.notice.bo.EcuNoticeBo;
import org.jeecg.modules.cable.controller.user.notice.bo.EcuNoticePageBo;
import org.jeecg.modules.cable.controller.user.notice.bo.EcuNoticeSortBo;
import org.jeecg.modules.cable.controller.user.notice.bo.EcuNoticeStartBo;
import org.jeecg.modules.cable.controller.user.notice.vo.NoticeVo;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@ApiSupport(order =570)
@Tag(name = "报价说明")
@RestController
public class EcuNoticeController {
    @Resource
    EcuNoticeModel ecuNoticeModel;

    @Operation(summary = "备注管理编辑时回显")
    @PostMapping({"/ecableErpPc/ecuNotice/getObject"})
    public Result<EcuNotice> getObject(@RequestBody EcuNoticeBo bo) {
        return Result.ok(ecuNoticeModel.getObject(bo));
    }


    @Operation(summary = "获取备注管理列表")
    @PostMapping({"/ecableErpPc/ecuNotice/getList"})
    public Result<NoticeVo> getList(@RequestBody EcuNoticePageBo bo) {
        return Result.ok(ecuNoticeModel.getList(bo));
    }


    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecuNotice/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecuNoticeModel.deal(request));
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecuNotice/start"})
    public Result<String> start(@RequestBody EcuNoticeStartBo bo) {
        return Result.ok(ecuNoticeModel.start(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecuNotice/sort"})
    public Result<?> sort(@RequestBody EcuNoticeSortBo bo) {
        ecuNoticeModel.sort(bo);
        return Result.ok();
    }


    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecuNotice/delete"})
    public Result<?> delete(@RequestBody EcuNoticeStartBo bo) {
        ecuNoticeModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "设置默认")
    //设置默认
    @PostMapping({"/ecableErpPc/ecuNotice/defaultType"})
    public Result<?> defaultType(@RequestBody EcuNoticeStartBo bo) {
        ecuNoticeModel.defaultType(bo);
        return Result.ok();
    }
}
