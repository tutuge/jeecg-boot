package org.jeecg.modules.cable.model.userEcable;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationListBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcbuInsulationModel {
    @Resource
    EcbuInsulationService ecbuInsulationService;
    @Resource
    EcbInsulationService ecbInsulationService;
    //@Resource
    //EcdCollectModel ecdCollectModel;

    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbuInsulationBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        EcbuInsulation record = new EcbuInsulation();
        Integer ecbuiId = bo.getEcbuiId();
        String msg = "";
        if (ecbuiId == null) {// 插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.insert(record);
            msg = "插入数据";
        } else {
            record.setEcbuiId(ecbuiId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.update(record);
            msg = "更新数据";
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //loadData(sysUser.getEcCompanyId());// 加截txt
        return msg;
    }


    public String start(EcbuInsulationStartBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuInsulation record = new EcbuInsulation();
        Integer ecbiId = bo.getEcbiId();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuInsulation == null) {// 插入数据
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation ecbInsulation = ecbInsulationService.getObject(recordEcbInsulation);
            record.setEcbiId(ecbiId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbInsulation.getUnitPrice());
            record.setDensity(ecbInsulation.getDensity());
            record.setDescription("");
            ecbuInsulationService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuInsulation.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbuiId(ecbuInsulation.getEcbuiId());
            record.setStartType(startType);
            // System.out.println(CommonFunction.getGson().toJson(record));
            ecbuInsulationService.update(record);
        }
        //loadData(sysUser.getEcCompanyId());// 加截txt
        return msg;
    }


    public List<EcbuInsulation> getList(EcbuInsulationListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuInsulationService.getList(record);
    }


    public void deal(EcbuInsulation record) {
        //根据公司ID与系统材料的ID查询
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation == null) {
            ecbuInsulationService.insert(record);
        } else {
            record.setEcbuiId(ecbuInsulation.getEcbuiId());
            ecbuInsulationService.update(record);
        }
    }

    //  通过绝缘类型获取绝缘 为计算成本提供数据
    //前面是简称 后面是全称的
    public Pair<Map<String, Integer>, Map<String, Integer>> getInsulationPassInsulationStr(Integer ecCompanyId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        Map<String, Integer> abbreviationMap = new HashMap<>();
        Map<String, Integer> fullNameMap = new HashMap<>();
        for (EcbuInsulation ecbuInsulation : list) {
            Integer ecbuiId = ecbuInsulation.getEcbuiId();
            EcbInsulation ecbInsulation = ecbuInsulation.getEcbInsulation();
            if (ObjUtil.isNotNull(ecbInsulation)) {
                String abbreviation = ecbInsulation.getAbbreviation();
                if (StrUtil.isNotBlank(abbreviation)) {
                    abbreviationMap.put(abbreviation, ecbuiId);
                }
                String fullName = ecbInsulation.getFullName();
                if (StrUtil.isNotBlank(fullName)) {
                    fullNameMap.put(fullName, ecbuiId);
                }
            }
        }
        return new Pair<>(abbreviationMap, fullNameMap);
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        ecbuInsulationService.deleteByEcCompanyId(record);
    }


    public EcbuInsulation getObjectPassEcbuiId(Integer ecbuiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbuiId(ecbuiId);
        return ecbuInsulationService.getObject(record);
    }

    // 通过绝缘类型获取绝缘
    public EcbuInsulation getInsulationPassFullName(Integer ecCompanyId, String fullName) {
        EcbuInsulation object = null;
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        for (EcbuInsulation ecbuInsulation : list) {
            Integer ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getFullName().equals(fullName)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }

    //// load 加载用户数据为txt文档
    //public void loadData(Integer ecCompanyId) {
    //    EcbInsulation record = new EcbInsulation();
    //    record.setStartType(true);
    //    record.setEcCompanyId(ecCompanyId);
    //    List<EcbInsulation> list = ecbInsulationService.getList(record);
    //    List<String> txtList = new ArrayList<>();
    //    txtList.add(CommonFunction.getGson().toJson(list));
    //    ecdCollectModel.deal(ecCompanyId, 5, txtList);
    //}


    public List<EcbInsulation> getListStart() {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        return ecbInsulationService.getListStart(record);
    }

    // getObjectPassAbbreviation
    public EcbInsulation getObjectPassAbbreviation(String abbreviation) {
        EcbInsulation record = new EcbInsulation();
        record.setAbbreviation(abbreviation);
        return ecbInsulationService.getObject(record);
    }

    /**
     * 获取绝缘的系统id与用户id的对照map
     *
     * @param ecCompanyId
     * @return
     */
    public Map<Integer, Integer> getMapAll(Integer ecCompanyId) {
        EcbuInsulation insulation = new EcbuInsulation();
        insulation.setEcCompanyId(ecCompanyId);
        List<EcbuInsulation> list = ecbuInsulationService.getList(insulation);
        Map<Integer, Integer> collect = list.stream().collect(Collectors.toMap(EcbuInsulation::getEcbiId, EcbuInsulation::getEcbuiId));
        return collect;
    }
}
