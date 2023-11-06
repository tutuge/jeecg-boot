package org.jeecg.modules.cable.controller.systemEcable.sheath;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "护套--系统接口", description = "护套--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "481", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbSheath")
public class EcbSheathController {
    @Resource
    EcbSheathModel ecbSheathModel;

    @PostMapping({"/getList"})
    public Result<SheathVo> getList(@RequestBody EcbSheathListBo bo) {
        return Result.ok(ecbSheathModel.getList(bo));
    }

    @PostMapping({"/getObject"})
    public Result<EcbSheath> getObject(@RequestBody EcbSheathBaseBo bo) {
        return Result.ok(ecbSheathModel.getObject(bo));
    }

    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbSheathDealBo bo) {
        return Result.ok(ecbSheathModel.deal(bo));
    }

    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbSheathSortBo> bos) {
        ecbSheathModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbSheathBaseBo bo) {
        return Result.ok(ecbSheathModel.start(bo));
    }

    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbSheathBaseBo bo) {
        ecbSheathModel.delete(bo);
        return Result.ok();
    }


}
