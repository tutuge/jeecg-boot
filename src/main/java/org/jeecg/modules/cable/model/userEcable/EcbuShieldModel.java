//package org.jeecg.modules.cable.model.userEcable;
//
//import cn.hutool.core.lang.Pair;
//import cn.hutool.core.util.ObjUtil;
//import cn.hutool.core.util.StrUtil;
//import jakarta.annotation.Resource;
//import org.apache.shiro.SecurityUtils;
//import org.jeecg.common.system.vo.LoginUser;
//import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldBo;
//import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldStartBo;
//import org.jeecg.modules.cable.controller.systemEcable.shield.vo.ShieldVo;
//import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldBo;
//import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldListBo;
//import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldStartBo;
//import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
//import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
//import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
//import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
//import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
//import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class EcbuShieldModel {
//    @Resource
//    EcbuShieldService ecbuShieldService;
//    @Resource
//    EcbShieldService ecbShieldService;
//    //@Resource
//    //EcdCollectModel ecdCollectModel;
//
//    @Transactional(rollbackFor = Exception.class)
//    public void deal(EcbuShieldBo bo) {
//        BigDecimal unitPrice = bo.getUnitPrice();
//        BigDecimal density = bo.getDensity();
//        String description = bo.getDescription();
//        Integer ecbusId = bo.getEcbusId();
//        EcbuShield record = new EcbuShield();
//
//        if (ecbusId == null) {//插入
//            record.setStartType(false);
//            record.setName("");
//            record.setUnitPrice(unitPrice);
//            record.setDensity(density);
//            record.setDescription(description);
//            ecbuShieldService.insert(record);
//        } else {
//            record.setEcbusId(ecbusId);
//            record.setUnitPrice(unitPrice);
//            record.setDensity(density);
//            record.setDescription(description);
//            ecbuShieldService.update(record);
//        }
//    }
//
//    public String start(EcbuShieldStartBo bo) {
//        Integer ecbsId = bo.getEcbsId();
//        EcbuShield record = new EcbuShield();
//        record.setEcbsId(ecbsId);
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
//        Boolean startType;
//
//        String msg = "";
//        if (ecbuShield == null) {//插入数据
//            EcbShield recordEcbShield = new EcbShield();
//            recordEcbShield.setEcbsId(ecbsId);
//            EcbShield ecbShield = ecbShieldService.getObject(recordEcbShield);
//            record.setEcbsId(ecbsId);
//            record.setEcCompanyId(sysUser.getEcCompanyId());
//            record.setStartType(true);
//            record.setName("");
//            record.setUnitPrice(ecbShield.getUnitPrice());
//            record.setDensity(ecbShield.getDensity());
//            record.setDescription("");
//            ecbuShieldService.insert(record);
//            msg = "数据启用成功";
//        } else {
//            startType = ecbuShield.getStartType();
//            if (!startType) {
//                startType = true;
//                msg = "数据启用成功";
//            } else {
//                startType = false;
//                msg = "数据禁用成功";
//            }
//            record.setEcbusId(ecbuShield.getEcbusId());
//            record.setStartType(startType);
//            //log.info(CommonFunction.getGson().toJson(record));
//            ecbuShieldService.update(record);
//        }
//        //loadData(sysUser.getEcCompanyId());//txt文档
//        return msg;
//    }
//
//
//    public List<EcbuShield> getList(EcbuShieldListBo bo) {
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        EcbuShield record = new EcbuShield();
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        record.setStartType(bo.getStartType());
//        return ecbuShieldService.getList(record);
//    }
//
//
//    @Transactional(rollbackFor = Exception.class)
//    public void deal(EcbuShield record) {
//        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
//        if (ecbuShield == null) {
//            ecbuShieldService.insert(record);
//        } else {
//            record.setEcbusId(ecbuShield.getEcbusId());
//            ecbuShieldService.update(record);
//        }
//    }
//
//    //getObjectPassShieldStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
//    public Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassShieldStr(Integer ecCompanyId) {
//        EcbuShield record = new EcbuShield();
//        record.setStartType(true);
//        record.setEcCompanyId(ecCompanyId);
//        List<EcbuShield> list = ecbuShieldService.getList(record);
//        Map<String, Integer> abbreviationMap = new HashMap<>();
//        Map<String, Integer> fullNameMap = new HashMap<>();
//        for (EcbuShield ecbuShield : list) {
//            Integer ecbusId = ecbuShield.getEcbusId();
//            EcbShield ecbShield = ecbuShield.getEcbShield();
//            if (ObjUtil.isNotNull(ecbShield)) {
//                String abbreviation = ecbShield.getAbbreviation();
//                if (StrUtil.isNotBlank(abbreviation)) {
//                    abbreviationMap.put(abbreviation, ecbusId);
//                }
//                String fullName = ecbShield.getFullName();
//                if (StrUtil.isNotBlank(fullName)) {
//                    fullNameMap.put(fullName, ecbusId);
//                }
//            }
//        }
//        return new Pair<>(abbreviationMap, fullNameMap);
//    }
//
//
//    public void deletePassEcCompanyId(Integer ecCompanyId) {
//        EcbuShield record = new EcbuShield();
//        record.setEcCompanyId(ecCompanyId);
//        ecbuShieldService.deleteByEcCompanyId(record);
//    }
//
//    //getListAndCount
//    public ShieldVo getListAndCount(EcbShieldBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        EcbShield record = new EcbShield();
//        record.setStartType(bo.getStartType());
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        List<EcbShield> list = ecbShieldService.getList(record);
//        long count = ecbShieldService.getCount();
//        return new ShieldVo(list, count);
//    }
//
//
//    public EcbShield getObject(EcbShieldStartBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        Integer ecbsId = bo.getEcbsId();
//        EcbShield recordEcbShield = new EcbShield();
//        recordEcbShield.setEcbsId(ecbsId);
//        EcbShield ecbShield = ecbShieldService.getObject(recordEcbShield);
//        EcbuShield record = new EcbuShield();
//        record.setEcbsId(ecbsId);
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
//        if (ecbuShield != null) {
//            ecbShield.setEcbuShield(ecbuShield);
//        }
//        return ecbShield;
//    }
//
//
//    //public void loadData(Integer ecCompanyId) {
//    //    EcbShield record = new EcbShield();
//    //    record.setStartType(true);
//    //    record.setEcCompanyId(ecCompanyId);
//    //    System.out.println(CommonFunction.getGson().toJson(record));
//    //    List<EcbShield> list = ecbShieldService.getList(record);
//    //    List<String> txtList = new ArrayList<>();
//    //    txtList.add(CommonFunction.getGson().toJson(list));
//    //    ecdCollectModel.deal(ecCompanyId, 8, txtList);
//    //}
//
//
//    //getListStart
//    public List<EcbShield> getListStart() {
//        EcbShield record = new EcbShield();
//        record.setStartType(true);
//        return ecbShieldService.getListStart(record);
//    }
//
//    /**
//     * 获取系统材料id与用户材料id的对照map。用于初始化新公司的基础信息
//     *
//     * @param ecCompanyId
//     * @return
//     */
//    public Map<Integer, Integer> getMapAll(Integer ecCompanyId) {
//        EcbuShield ecbuShield = new EcbuShield();
//        ecbuShield.setEcCompanyId(ecCompanyId);
//        List<EcbuShield> list = ecbuShieldService.getList(ecbuShield);
//        Map<Integer, Integer> collect = list.stream()
//                .collect(Collectors.toMap(EcbuShield::getEcbsId, EcbuShield::getEcbusId));
//        return collect;
//    }
//}
