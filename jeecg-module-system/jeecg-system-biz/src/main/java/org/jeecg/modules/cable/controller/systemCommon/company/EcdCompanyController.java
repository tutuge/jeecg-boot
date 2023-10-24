package org.jeecg.modules.cable.controller.systemCommon.company;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.company.vo.EcdCompanyListVo;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.model.systemCommon.EcdCompanyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =2030)
@RestController
@Slf4j
public class EcdCompanyController {
    @Resource
    EcdCompanyModel ecdCompanyModel;

    //deal
    @PostMapping({"/ecableAdminPc/ecdCompany/deal"})
    public Result<String> deal(@RequestBody EcdCompanyDealBo bo) {
        return Result.ok(ecdCompanyModel.deal(bo));
    }

    //getList
    @PostMapping({"/ecableAdminPc/ecdCompany/getList"})
    public Result<EcdCompanyListVo> getList(@RequestBody EcdCompanyListBo bo) {
        return Result.ok(ecdCompanyModel.getList(bo));
    }

    //getObject
    @PostMapping({"/ecableAdminPc/ecdCompany/getObject"})
    public Result<EcdCompany> getObject(@RequestBody EcdCompanyBaseBo bo) {
        return Result.ok(ecdCompanyModel.getObject(bo));
    }

    //sort 排序
    @PostMapping({"/ecableAdminPc/ecdCompany/sort"})
    public Result<?> sort(@RequestBody List<EcdCompanySortBo> bos) {
        ecdCompanyModel.sort(bos);
        return Result.ok();
    }

    //start 启用、禁用
    @PostMapping({"/ecableAdminPc/ecdCompany/start"})
    public Result<String> start(@RequestBody EcdCompanyBaseBo bo) {
        return Result.ok(ecdCompanyModel.start(bo));
    }

    //delete 删除
    @PostMapping({"/ecableAdminPc/ecdCompany/delete"})
    public Result<?> delete(@RequestBody EcdCompanyBaseBo bo) {
        ecdCompanyModel.delete(bo);
        return Result.ok();
    }
}
