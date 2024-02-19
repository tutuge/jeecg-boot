//package org.jeecg.modules.cable.controller.systemEcable.insulation;
//
//import io.swagger.v3.oas.annotations.extensions.Extension;
//import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBaseBo;
//import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationDealBo;
//import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationListBo;
//import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationSortBo;
//import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
//import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
//import org.jeecg.modules.cable.model.systemEcable.EcbInsulationModel;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Tag(name = "绝缘--系统接口", description = "绝缘--系统接口",
//        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "421", parseValue = true)})})
//@RestController
//@RequestMapping("/ecableAdminPc/ecbInsulation")
//public class EcbInsulationController {
//    @Resource
//    EcbInsulationModel ecbInsulationModel;
//
//    @PostMapping({"/getList"})
//    public Result<InsulationVo> getList(@RequestBody EcbInsulationListBo bo) {
//        return Result.ok(ecbInsulationModel.getList(bo));
//    }
//
//    @PostMapping({"/getObject"})
//    public Result<EcbInsulation> getObject(@Validated @RequestBody EcbInsulationBaseBo bo) {
//        return Result.ok(ecbInsulationModel.getObject(bo));
//    }
//
//    @PostMapping({"/deal"})
//    public Result<String> deal(@Validated @RequestBody EcbInsulationDealBo bo) {
//        return Result.ok(ecbInsulationModel.deal(bo));
//    }
//
//    @PostMapping({"/sort"})
//    public Result<?> sort(@RequestBody List<EcbInsulationSortBo> bos) {
//        ecbInsulationModel.sort(bos);
//        return Result.ok();
//    }
//
//    @PostMapping({"/start"})
//    public Result<String> start(@Validated @RequestBody EcbInsulationBaseBo bo) {
//        return Result.ok(ecbInsulationModel.start(bo));
//    }
//
//    @PostMapping({"/delete"})
//    public Result<?> delete(@Validated @RequestBody EcbInsulationBaseBo bo) {
//        ecbInsulationModel.delete(bo);
//        return Result.ok();
//    }
//
//}
