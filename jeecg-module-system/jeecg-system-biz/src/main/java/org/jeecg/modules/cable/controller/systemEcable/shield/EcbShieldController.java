package org.jeecg.modules.cable.controller.systemEcable.shield;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathDealBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldListBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldSortBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.vo.ShieldVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@ApiSupport(order =431)
@Tag(name = "屏蔽--系统接口")
@RestController
public class EcbShieldController {
    @Resource
    EcbShieldModel ecbShieldModel;

    @PostMapping({"/ecableAdminPc/ecbShield/getList"})
    public Result<ShieldVo> getList(@RequestBody EcbShieldListBo bo) {
        return Result.ok(ecbShieldModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbShield/getObject"})
    public Result<EcbShield> getObject(@RequestBody EcbShieldBaseBo bo) {
        return Result.ok(ecbShieldModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbShield/deal"})
    public Result<String> deal(@RequestBody EcbSheathDealBo bo) {
        return Result.ok(ecbShieldModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbShield/sort"})
    public Result<?> sort(@RequestBody List<EcbShieldSortBo> bos) {
        ecbShieldModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbShield/start"})
    public Result<String> start(@RequestBody EcbShieldBaseBo bo) {
        return Result.ok(ecbShieldModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbShield/delete"})
    public Result<?> delete(@RequestBody EcbShieldBaseBo bo) {
         ecbShieldModel.delete(bo);
        return Result.ok();
    }
}
