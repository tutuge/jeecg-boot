//package org.jeecg.modules.cable.model.userEcable;
//
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.jeecg.common.system.vo.LoginUser;
//import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsStartBo;
//import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorBo;
//import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorListBo;
//import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorStartBo;
//import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
//import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
//import org.jeecg.modules.cable.service.systemEcable.EcbMaterialsService;
//import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//@Slf4j
//public class EcbuConductorModel {
//    @Resource
//    EcbuConductorService ecbuConductorService;
//    @Resource
//    EcbMaterialsService ecbMaterialsService;
//
//    public void deal(EcbuConductorBo bo) {
//        BigDecimal unitPrice = bo.getUnitPrice();
//        BigDecimal density = bo.getDensity();
//        BigDecimal resistivity = bo.getResistivity();
//        String description = bo.getDescription();
//        Integer ecbucId = bo.getEcbucId();
//        Integer conductorType = bo.getConductorType();
//        EcbuConductor record = new EcbuConductor();
//        if (ecbucId == null) {//插入
//            record.setStartType(false);
//            record.setName("");
//            record.setConductorType(conductorType);
//            record.setUnitPrice(unitPrice);
//            record.setDensity(density);
//            record.setResistivity(resistivity);
//            record.setDescription(description);
//            ecbuConductorService.insert(record);
//        } else {
//            record.setEcbucId(ecbucId);
//            record.setConductorType(conductorType);
//            record.setUnitPrice(unitPrice);
//            record.setDensity(density);
//            record.setResistivity(resistivity);
//            record.setDescription(description);
//            ecbuConductorService.update(record);
//        }
//        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        //loadData(sysUser.getEcCompanyId());//加截txt
//    }
//
//
//    public String start(EcbuConductorStartBo bo) {
//        EcbuConductor record = new EcbuConductor();
//        Integer ecbcId = bo.getEcbcId();
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//
//        record.setEcbcId(ecbcId);
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
//        Boolean startType;
//        String msg = "";
//        if (ecbuConductor == null) {//插入数据
//            EcbMaterials recordEcbMaterials = new EcbMaterials();
//            recordEcbMaterials.setId(ecbcId);
//            EcbMaterials ecbMaterials = ecbMaterialsService.getObject(recordEcbMaterials);
//            record.setEcbcId(ecbcId);
//            record.setEcCompanyId(sysUser.getEcCompanyId());
//            record.setStartType(true);
//            record.setName("");
//            record.setUnitPrice(ecbMaterials.getUnitPrice());
//            record.setDensity(ecbMaterials.getDensity());
//            record.setResistivity(ecbMaterials.getResistivity());
//            record.setDescription("");
//            ecbuConductorService.insert(record);
//            msg = "数据启用成功";
//        } else {
//            startType = ecbuConductor.getStartType();
//            if (!startType) {
//                startType = true;
//                msg = "数据启用成功";
//            } else {
//                startType = false;
//                msg = "数据禁用成功";
//            }
//            record.setEcbucId(ecbuConductor.getEcbucId());
//            record.setStartType(startType);
//            //System.out.println(CommonFunction.getGson().toJson(record));
//            ecbuConductorService.update(record);
//        }
//        //loadData(sysUser.getEcCompanyId());//加截txt
//        return msg;
//    }
//
//
//    public List<EcbuConductor> getList(EcbuConductorListBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        EcbuConductor record = new EcbuConductor();
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        record.setStartType(bo.getStartType());
//        return ecbuConductorService.getList(record);
//    }
//
//
//    public void deal(EcbuConductor record) {
//        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
//        if (ecbuConductor == null) {
//            ecbuConductorService.insert(record);
//        } else {
//            record.setEcbucId(ecbuConductor.getEcbcId());
//            ecbuConductorService.update(record);
//        }
//    }
//
//    public EcbuConductor getObjectPassEcbcIdAndEcCompanyId(Integer ecbcId, Integer ecCompanyId) {
//        EcbuConductor record = new EcbuConductor();
//        record.setEcbcId(ecbcId);
//        record.setEcCompanyId(ecCompanyId);
//        return ecbuConductorService.getObject(record);
//    }
//
//
//    public void deleteByEcCompanyId(Integer ecCompanyId) {
//        EcbuConductor record = new EcbuConductor();
//        record.setEcCompanyId(ecCompanyId);
//        ecbuConductorService.deleteByEcCompanyId(record);
//    }
//
//    //getObjectPassEcbucId
//    public EcbuConductor getObjectPassEcbucId(Integer ecbucId) {
//        EcbuConductor record = new EcbuConductor();
//        record.setEcbucId(ecbucId);
//        return ecbuConductorService.getObject(record);
//    }
//
//
//    public EcbMaterials getObject(EcbMaterialsStartBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        EcbMaterials recordEcbMaterials = new EcbMaterials();
//        recordEcbMaterials.setId(bo.getId());
//        EcbMaterials ecbMaterials = ecbMaterialsService.getObject(recordEcbMaterials);
//        EcbuConductor record = new EcbuConductor();
//        record.setEcbcId(bo.getId());
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
//        if (ecbuConductor != null) {
//            ecbMaterials.setEcbuConductor(ecbuConductor);
//        }
//        return ecbMaterials;
//    }
//
//
//    //public void loadData(Integer ecCompanyId) {
//    //    EcbConductor record = new EcbConductor();
//    //    record.setStartType(true);
//    //    record.setEcCompanyId(ecCompanyId);
//    //    log.info(CommonFunction.getGson().toJson(record));
//    //    List<EcbConductor> list = ecbConductorService.getList(record);
//    //    List<String> txtList = new ArrayList<>();
//    //    txtList.add(CommonFunction.getGson().toJson(list));
//    //    ecdCollectModel.deal(ecCompanyId, 3, txtList);
//    //}
//
//    /***===获取数据模型===***/
//    //获取启用列表
//    public List<EcbMaterials> getListStart() {
//        EcbMaterials record = new EcbMaterials();
//        record.setStartType(true);
//        return ecbMaterialsService.getListStart(record);
//    }
//}
