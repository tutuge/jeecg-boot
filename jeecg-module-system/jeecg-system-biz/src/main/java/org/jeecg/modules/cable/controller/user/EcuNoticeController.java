package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "备注管理")
@RestController
public class EcuNoticeController {
    @Resource
    EcuNoticeModel ecuNoticeModel;

    @Operation(summary = "备注管理编辑时回显")
    @PostMapping({"/ecableErpPc/ecuNotice/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuNoticeModel.getObject(request);
    }


    @Operation(summary = "获取备注管理列表")
    @PostMapping({"/ecableErpPc/ecuNotice/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuNoticeModel.getList(request);
    }


    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecuNotice/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuNoticeModel.deal(request);
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecuNotice/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuNoticeModel.start(request);
    }


    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecuNotice/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecuNoticeModel.sort(request);
    }


    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecuNotice/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuNoticeModel.delete(request);
    }


    @Operation(summary = "设置默认")
    //设置默认
    @PostMapping({"/ecableErpPc/ecuNotice/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecuNoticeModel.defaultType(request);
    }
}
