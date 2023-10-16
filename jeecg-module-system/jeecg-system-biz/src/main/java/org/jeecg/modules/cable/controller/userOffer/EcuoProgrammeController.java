package org.jeecg.modules.cable.controller.userOffer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.userOffer.EcuoProgrammeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "方案")
@RestController
@Slf4j
public class EcuoProgrammeController {
    @Resource
    EcuoProgrammeModel ecuoProgrammeModel;

    @Operation(summary = "编辑提交方案")
    @PostMapping({"/ecableErpPc/ecuoProgramme/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuoProgrammeModel.deal(request);
    }


    @Operation(summary = "方案列表")
    @PostMapping({"/ecableErpPc/ecuoProgramme/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuoProgrammeModel.getList(request);
    }


    @Operation(summary = "方案详情")
    @PostMapping({"/ecableErpPc/ecuoProgramme/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuoProgrammeModel.getObject(request);
    }


    @Operation(summary = "方案排序")
    @PostMapping({"/ecableErpPc/ecuoProgramme/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecuoProgrammeModel.sort(request);
    }


    @Operation(summary = "方案删除")
    @PostMapping({"/ecableErpPc/ecuoProgramme/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuoProgrammeModel.delete(request);
    }
}
