package org.jeecg.modules.cable.controller.systemCommon.pcompany;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.model.systemCommon.EcbPcompanyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@Tag(name = "平台--系统接口", description = "平台",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2020", parseValue = true)})})
public class EcbPcompanyController {
    @Resource
    EcbPcompanyModel ecbPcompanyModel;

    // deal
    @PostMapping({"/ecableAdminPc/ecbPcompany/deal"})
    public Result<String> deal(@RequestBody EcbPcompanyDealBo bo) {
        return Result.ok(ecbPcompanyModel.deal(bo));
    }

    // getList
    @PostMapping({"/ecableAdminPc/ecbPcompany/getList"})
    public Result<EcbPcompanyVo> getList(@RequestBody EcbPcompanyListBo bo) {
        return Result.ok(ecbPcompanyModel.getList(bo));
    }

    // getObject
    @PostMapping({"/ecableAdminPc/ecbPcompany/getObject"})
    public Result<EcbPcompany> getObject(@RequestBody EcbPcompanyBaseBo bo) {
        return Result.ok(ecbPcompanyModel.getObject(bo));
    }

    // sort 排序
    @PostMapping({"/ecableAdminPc/ecbPcompany/sort"})
    public Result<?> sort(@RequestBody List<EcbPcompanySortBo> bos) {
        ecbPcompanyModel.sort(bos);
        return Result.ok();
    }

    // start 启用、禁用
    @PostMapping({"/ecableAdminPc/ecbPcompany/start"})
    public Result<String> start(@RequestBody EcbPcompanyBaseBo bo) {
        return Result.ok(ecbPcompanyModel.start(bo));
    }

    // delete 删除
    @PostMapping({"/ecableAdminPc/ecbPcompany/delete"})
    public Result<?> delete(@RequestBody EcbPcompanyBaseBo bo) {
        ecbPcompanyModel.delete(bo);
        return Result.ok();
    }
}
