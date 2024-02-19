//package org.jeecg.modules.cable.model.userEcable;
//
//import cn.hutool.core.lang.Pair;
//import cn.hutool.core.util.ObjUtil;
//import cn.hutool.core.util.StrUtil;
//import jakarta.annotation.Resource;
//import org.apache.shiro.SecurityUtils;
//import org.jeecg.common.system.vo.LoginUser;
//import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBo;
//import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeStartBo;
//import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
//import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicaTapeBo;
//import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeListBo;
//import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeStartBo;
//import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
//import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
//import org.jeecg.modules.cable.service.systemEcable.EcbMicaTapeService;
//import org.jeecg.modules.cable.service.userEcable.EcbuMicaTapeService;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class EcbuMicaTapeModel {
//    @Resource
//    EcbuMicaTapeService ecbuMicaTapeService;
//    @Resource
//    EcbMicaTapeService ecbMicatapeService;
//    //@Resource
//    //EcdCollectModel ecdCollectModel;
//
//
//    public void deal(EcbuMicaTapeBo bo) {
//        BigDecimal unitPrice = bo.getUnitPrice();
//        BigDecimal density = bo.getDensity();
//        String description = bo.getDescription();
//        Integer ecbumId = bo.getEcbumId();
//        EcbuMicaTape record = new EcbuMicaTape();
//        if (ecbumId == null) {//插入
//            record.setStartType(false);
//            record.setName("");
//            record.setUnitPrice(unitPrice);
//            record.setDensity(density);
//            record.setDescription(description);
//            ecbuMicaTapeService.insert(record);
//        } else {
//            record.setEcbumId(ecbumId);
//            record.setUnitPrice(unitPrice);
//            record.setDensity(density);
//            record.setDescription(description);
//            ecbuMicaTapeService.update(record);
//        }
//        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        //loadData(sysUser.getEcCompanyId());//加截txt
//    }
//
//
//    public String start(EcbuMicatapeStartBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        Integer ecbmId = bo.getEcbmId();
//        EcbuMicaTape record = new EcbuMicaTape();
//        record.setEcbmId(ecbmId);
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        EcbuMicaTape ecbuMicaTape = ecbuMicaTapeService.getObject(record);
//        Boolean startType;
//        String msg = "";
//        if (ecbuMicaTape == null) {//插入数据
//            EcbMicaTape recordEcbMicaTape = new EcbMicaTape();
//            recordEcbMicaTape.setEcbmId(ecbmId);
//            EcbMicaTape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicaTape);
//            record.setEcbmId(ecbmId);
//            record.setEcCompanyId(sysUser.getEcCompanyId());
//            record.setStartType(true);
//            record.setName("");
//            record.setUnitPrice(ecbMicatape.getUnitPrice());
//            record.setDensity(ecbMicatape.getDensity());
//            record.setDescription("");
//            ecbuMicaTapeService.insert(record);
//            msg = "数据启用成功";
//        } else {
//            startType = ecbuMicaTape.getStartType();
//            if (!startType) {
//                startType = true;
//                msg = "数据启用成功";
//            } else {
//                startType = false;
//                msg = "数据禁用成功";
//            }
//            record.setEcbumId(ecbuMicaTape.getEcbumId());
//            record.setStartType(startType);
//            ecbuMicaTapeService.update(record);
//        }
//        //loadData(sysUser.getEcCompanyId());//加截txt
//        return msg;
//    }
//
//
//    public List<EcbuMicaTape> getList(EcbuMicatapeListBo bo) {
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//        EcbuMicaTape record = new EcbuMicaTape();
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        record.setStartType(bo.getStartType());
//        return ecbuMicaTapeService.getList(record);
//    }
//
//
//    public void deal(EcbuMicaTape record) {
//        EcbuMicaTape ecbuMicaTape = ecbuMicaTapeService.getObject(record);
//        if (ecbuMicaTape == null) {
//            ecbuMicaTapeService.insert(record);
//        } else {
//            record.setEcbumId(ecbuMicaTape.getEcbumId());
//            ecbuMicaTapeService.update(record);
//        }
//    }
//
//
//    // 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
//    public Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassMicaTape(Integer ecCompanyId) {
//        EcbuMicaTape record = new EcbuMicaTape();
//        record.setStartType(true);
//        record.setEcCompanyId(ecCompanyId);
//        List<EcbuMicaTape> list = ecbuMicaTapeService.getList(record);
//        Map<String, Integer> abbreviationMap = new HashMap<>();
//        Map<String, Integer> fullNameMap = new HashMap<>();
//        for (EcbuMicaTape ecbuMicaTape : list) {
//            Integer ecbusId = ecbuMicaTape.getEcbumId();
//            EcbMicaTape ecbMicaTape = ecbuMicaTape.getEcbMicatape();
//            if (ObjUtil.isNotNull(ecbMicaTape)) {
//                String abbreviation = ecbMicaTape.getAbbreviation();
//                if (StrUtil.isNotBlank(abbreviation)) {
//                    abbreviationMap.put(abbreviation, ecbusId);
//                }
//                String fullName = ecbMicaTape.getFullName();
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
//        EcbuMicaTape record = new EcbuMicaTape();
//        record.setEcCompanyId(ecCompanyId);
//        ecbuMicaTapeService.delete(record);
//    }
//
//    //getObjectPassEcbumId
//    public EcbuMicaTape getObjectPassEcbumId(Integer ecbumId) {
//        EcbuMicaTape record = new EcbuMicaTape();
//        record.setEcbumId(ecbumId);
//        return ecbuMicaTapeService.getObject(record);
//    }
//
//
//    //getListAndCount
//    public MicatapeVo getListAndCount(EcbMicatapeBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//
//        EcbMicaTape record = new EcbMicaTape();
//        record.setStartType(bo.getStartType());
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        List<EcbMicaTape> list = ecbMicatapeService.getList(record);
//        long count = ecbMicatapeService.getCount();
//        return new MicatapeVo(list, count);
//    }
//
//
//    public EcbMicaTape getObject(EcbMicatapeStartBo bo) {
//        //获取当前用户id
//        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//
//
//        EcbMicaTape recordEcbMicaTape = new EcbMicaTape();
//        Integer ecbmId = bo.getEcbmId();
//        recordEcbMicaTape.setEcbmId(ecbmId);
//
//        EcbMicaTape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicaTape);
//        EcbuMicaTape record = new EcbuMicaTape();
//        record.setEcbmId(ecbmId);
//        record.setEcCompanyId(sysUser.getEcCompanyId());
//        EcbuMicaTape ecbuMicatape = ecbuMicaTapeService.getObject(record);
//        if (ecbuMicatape != null) {
//            ecbMicatape.setEcbuMicatape(ecbuMicatape);
//        }
//        return ecbMicatape;
//    }
//
//
//    //public void loadData(Integer ecCompanyId) {
//    //    EcbMicaTape record = new EcbMicaTape();
//    //    record.setStartType(true);
//    //    record.setEcCompanyId(ecCompanyId);
//    //    System.out.println(CommonFunction.getGson().toJson(record));
//    //    List<EcbMicaTape> list = ecbMicatapeService.getList(record);
//    //    List<String> txtList = new ArrayList<>();
//    //    txtList.add(CommonFunction.getGson().toJson(list));
//    //    ecdCollectModel.deal(ecCompanyId, 4, txtList);
//    //}
//
//
//    //getListStart
//    public List<EcbMicaTape> getListStart() {
//        EcbMicaTape record = new EcbMicaTape();
//        record.setStartType(true);
//        return ecbMicatapeService.getListStart(record);
//    }
//
//
//    /**
//     * 获取系统材料id与用户材料id的对照map。用于初始化新公司的基础信息
//     *
//     * @param ecCompanyId
//     * @return
//     */
//    public Map<Integer, Integer> getMapAll(Integer ecCompanyId) {
//        EcbuMicaTape ecbuMicaTape = new EcbuMicaTape();
//        ecbuMicaTape.setEcCompanyId(ecCompanyId);
//        List<EcbuMicaTape> list = ecbuMicaTapeService.getList(ecbuMicaTape);
//        Map<Integer, Integer> collect = list.stream()
//                .collect(Collectors.toMap(EcbuMicaTape::getEcbmId, EcbuMicaTape::getEcbumId));
//        return collect;
//    }
//}
