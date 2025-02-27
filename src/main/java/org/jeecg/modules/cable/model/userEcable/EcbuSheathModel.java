package org.jeecg.modules.cable.model.userEcable;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathStartBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathListBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EcbuSheathModel {
    @Resource
    EcbuSheathService ecbuSheathService;
    @Resource
    EcbSheathService ecbSheathService;


    public void deal(EcbuSheathBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        Integer ecbusId = bo.getEcbusId();
        EcbuSheath record = new EcbuSheath();
        if (ecbusId == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSheathService.insert(record);
        } else {
            record.setEcbusId(ecbusId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSheathService.updateById(record);
        }
    }


    public String start(EcbuSheathStartBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuSheath == null) {//插入数据
            EcbSheath recordEcbSheath = new EcbSheath();
            recordEcbSheath.setEcbsId(ecbsId);
            EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
            record.setEcbsId(ecbsId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSheath.getUnitPrice());
            record.setDensity(ecbSheath.getDensity());
            record.setDescription("");
            ecbuSheathService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuSheath.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuSheath.getEcbusId());
            record.setStartType(startType);
            ecbuSheathService.updateById(record);
        }
        return msg;
    }


    public List<EcbuSheath> getList(EcbuSheathListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuSheathService.getList(record);
    }

    public void deal(EcbuSheath record) {
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath == null) {
            ecbuSheathService.insert(record);
        } else {
            record.setEcbusId(ecbuSheath.getEcbusId());
            ecbuSheathService.updateById(record);
        }
    }


    //getObjectPassSheathStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassSheathStr(Integer ecCompanyId) {
        EcbuSheath record = new EcbuSheath();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuSheath> list = ecbuSheathService.getList(record);
        Map<String, Integer> abbreviationMap = new HashMap<>();
        Map<String, Integer> fullNameMap = new HashMap<>();
        for (EcbuSheath ecbuSheath : list) {
            Integer ecbusId = ecbuSheath.getEcbusId();
            EcbSheath ecbSheath = ecbuSheath.getEcbSheath();
            if (ObjUtil.isNotNull(ecbSheath)) {
                String abbreviation = ecbSheath.getAbbreviation();
                if (StrUtil.isNotBlank(abbreviation)) {
                    abbreviationMap.put(abbreviation, ecbusId);
                }
                String fullName = ecbSheath.getFullName();
                if (StrUtil.isNotBlank(fullName)) {
                    fullNameMap.put(fullName, ecbusId);
                }
            }
        }
        return new Pair<>(abbreviationMap, fullNameMap);
    }

    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuSheath record = new EcbuSheath();
        record.setEcCompanyId(ecCompanyId);
        ecbuSheathService.deleteByCompanyId(record);
    }

    //getObjectPassEcbusid
    public EcbuSheath getObjectPassEcbusid(Integer ecbusid) {
        return ecbuSheathService.getObjectById(ecbusid);
    }

    //getListAndCount
    public SheathVo getListAndCount(EcbSheathBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcbSheath record = new EcbSheath();

        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcbSheath> list = ecbSheathService.getList(record);
        long count = ecbSheathService.getCount();
        return new SheathVo(list, count);
    }


    public EcbSheath getObject(EcbSheathStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbsId = bo.getEcbsId();
        EcbSheath recordEcbSheath = new EcbSheath();
        recordEcbSheath.setEcbsId(ecbsId);
        EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath != null) {
            ecbSheath.setEcbuSheath(ecbuSheath);
        }
        return ecbSheath;
    }


    //getListStart
    public List<EcbSheath> getListStart() {
        EcbSheath record = new EcbSheath();
        record.setStartType(true);
        return ecbSheathService.getListStart(record);
    }

    // getListSilkName 获取丝型号名称 为报价页面提供数据
    public List<EcbSheath> getListSilkName(Integer ecCompanyId) {
        List<EcbSheath> list;
        Boolean startType = true;
        EcbSheath record = new EcbSheath();
        record.setStartType(startType);
        record.setEcCompanyId(ecCompanyId);
        list = ecbSheathService.getList(record);
        for (Integer i = 0; i < list.size(); i++) {
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbsId(list.get(i).getEcbsId());
            recordEcbuSheath.setEcCompanyId(ecCompanyId);
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            if (ecbuSheath != null) {
                if (list.get(i).getAbbreviation().contains("D2")) {
                    list.remove(i);
                    i--;
                } else if (list.get(i).getAbbreviation().contains("D1")) {
                    list.get(i).setAbbreviation("");
                }
            } else {
                list.remove(i);
                i--;
            }
        }
        return list;
    }


    /**
     * 获取系统材料id与用户材料id的对照map。用于初始化新公司的基础信息
     *
     * @param ecCompanyId
     * @return
     */
    public Map<Integer, Integer> getMapAll(Integer ecCompanyId) {
        EcbuSheath ecbuSheath = new EcbuSheath();
        ecbuSheath.setEcCompanyId(ecCompanyId);
        List<EcbuSheath> list = ecbuSheathService.getList(ecbuSheath);
        Map<Integer, Integer> collect = list.stream()
                .collect(Collectors.toMap(EcbuSheath::getEcbsId, EcbuSheath::getEcbusId));
        return collect;
    }
}
