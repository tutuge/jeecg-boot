package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.vo.BagVo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagBo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagListBo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcbuBagModel {
    @Resource
    EcbuBagService ecbuBagService;
    @Resource
    EcbBagService ecbBagService;
    @Resource
    EcdCollectModel ecdCollectModel;

    public void deal(EcbuBagBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        Integer ecbubId = bo.getEcbubId();
        EcbuBag record = new EcbuBag();

        if (ecbubId == null) {// 插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuBagService.insert(record);
        } else {
            record.setEcbubId(ecbubId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuBagService.update(record);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        loadData(sysUser.getEcCompanyId());// txt文档
    }

    public String start(EcbuBagStartBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbbId = bo.getEcbbId();
        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuBag == null) {// 插入数据
            EcbBag recordEcbBag = new EcbBag();
            recordEcbBag.setEcbbId(ecbbId);
            EcbBag ecbBag = ecbBagService.getObject(recordEcbBag);
            record.setEcbbId(ecbbId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbBag.getUnitPrice());
            record.setDensity(ecbBag.getDensity());
            record.setDescription("");
            ecbuBagService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuBag.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbubId(ecbuBag.getEcbubId());
            record.setStartType(startType);
            // System.out.println(CommonFunction.getGson().toJson(record));
            ecbuBagService.update(record);
        }
        loadData(sysUser.getEcCompanyId());// txt文档
        return msg;
    }

    public List<EcbuBag> getList(EcbuBagListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuBagService.getList(record);
    }

    public void deal(EcbuBag record) {
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag == null) {
            ecbuBagService.insert(record);
        } else {
            ecbuBag.setEcbubId(ecbuBag.getEcbubId());
            ecbuBagService.update(record);
        }
    }

    // getObjectPassBagStr 通过包带类型类型获取包带 为计算成本提供数据
    public EcbuBag getObjectPassBagStr(Integer ecCompanyId, String objectStr) {
        EcbuBag object = null;
        EcbuBag record = new EcbuBag();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuBag> list = ecbuBagService.getList(record);
        for (EcbuBag ecbuBag : list) {
            Integer ecbbId = ecbuBag.getEcbbId();
            EcbBag recordEcbBag = new EcbBag();
            recordEcbBag.setEcbbId(ecbbId);
            EcbBag bag = ecbBagService.getObject(recordEcbBag);
            if (bag.getAbbreviation().equals(objectStr)) {
                object = ecbuBag;
            }
        }
        return object;
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecCompanyId);
        ecbuBagService.deleteByEcCompanyId(record);
    }

    // getObjectPassEcbubId
    public EcbuBag getObjectPassEcbubId(Integer ecbubId) {
        EcbuBag record = new EcbuBag();
        record.setEcbubId(ecbubId);
        return ecbuBagService.getObject(record);
    }


    public BagVo getListAndCount(EcbBagBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbBag record = new EcbBag();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcbBag> list = ecbBagService.getList(record);
        long count = ecbBagService.getCount();
        return new BagVo(list, count, record);
    }


    public EcbBag getObject(EcbBagBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbBag recordEcbBag = new EcbBag();
        Integer ecbbId = bo.getEcbbId();
        recordEcbBag.setEcbbId(ecbbId);
        EcbBag ecbBag = ecbBagService.getObject(recordEcbBag);

        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag != null) {
            ecbBag.setEcbuBag(ecbuBag);
        }
        return ecbBag;
    }

    // load 加载用户包带数据为txt文档
    public void loadData(Integer ecCompanyId) {
        EcbBag record = new EcbBag();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbBag> list = ecbBagService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 7, txtList);
    }


    public List<EcbBag> getListStart() {
        EcbBag record = new EcbBag();
        record.setStartType(true);
        return ecbBagService.getListStart(record);
    }

    /**
     * 获取系统材料id与用户材料id的对照map。用于初始化新公司的基础信息
     *
     * @param ecCompanyId
     * @return
     */
    public Map<Integer, Integer> getMapAll(Integer ecCompanyId) {
        EcbuBag ecbuBag = new EcbuBag();
        ecbuBag.setEcCompanyId(ecCompanyId);
        List<EcbuBag> list = ecbuBagService.getList(ecbuBag);
        Map<Integer, Integer> collect = list.stream().collect(Collectors.toMap(EcbuBag::getEcbbId, EcbuBag::getEcbubId));
        return collect;
    }
}
