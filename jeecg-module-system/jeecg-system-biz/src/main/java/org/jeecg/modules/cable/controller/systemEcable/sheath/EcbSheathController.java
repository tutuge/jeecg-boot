package org.jeecg.modules.cable.controller.systemEcable.sheath;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathDealBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathListBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathSortBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.model.systemEcable.EcbSheathModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =481)
@Tag(name = "护套--系统接口")
@RestController
public class EcbSheathController {
    @Resource
    EcbSheathModel ecbSheathModel;

    @PostMapping({"/ecableAdminPc/ecbSheath/getList"})
    public Result<SheathVo> getList(@RequestBody EcbSheathListBo bo) {
        return Result.ok(ecbSheathModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSheath/getObject"})
    public Result<EcbSheath> getObject(@RequestBody EcbSheathBaseBo bo) {
        return Result.ok(ecbSheathModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSheath/deal"})
    public Result<String> deal(@RequestBody EcbSheathDealBo bo) {
        return Result.ok(ecbSheathModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSheath/sort"})
    public Result<?> sort(@RequestBody List<EcbSheathSortBo> bos) {
        ecbSheathModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbSheath/start"})
    public Result<String> start(@RequestBody EcbSheathBaseBo bo) {
        return Result.ok(ecbSheathModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSheath/delete"})
    public Result<?> delete(@RequestBody EcbSheathBaseBo bo) {
        ecbSheathModel.delete(bo);
        return Result.ok();
    }


}
