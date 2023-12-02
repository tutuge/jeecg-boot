package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandStartBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandListBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelBand;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EcbuSteelBandModel {
    @Resource
    EcbuSteelbandService ecbuSteelbandService;
    @Resource
    EcbSteelbandService ecbSteelbandService;
    //@Resource
    //EcdCollectModel ecdCollectModel;


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbuSteelBandBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        Integer ecbusId = bo.getEcbusId();
        EcbuSteelBand record = new EcbuSteelBand();
        if (ecbusId == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.insert(record);
        } else {
            record.setEcbusId(ecbusId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.update(record);
        }
        //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //loadData(sysUser.getEcCompanyId());//txt文档
    }


    public String start(EcbuSteelBandStartBo bo) {
        Integer ecbsbId = bo.getEcbsbId();
        EcbuSteelBand record = new EcbuSteelBand();
        record.setEcbsbId(ecbsbId);
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuSteelBand ecbuSteelband = ecbuSteelbandService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuSteelband == null) {//插入数据
            EcbSteelBand recordEcbSteelBand = new EcbSteelBand();
            recordEcbSteelBand.setEcbsbId(ecbsbId);
            EcbSteelBand ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelBand);
            record.setEcbsbId(ecbsbId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSteelband.getUnitPrice());
            record.setDensity(ecbSteelband.getDensity());
            record.setDescription("");
            ecbuSteelbandService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuSteelband.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuSteelband.getEcbusId());
            record.setStartType(startType);
            ecbuSteelbandService.update(record);
        }
        //loadData(sysUser.getEcCompanyId());//txt文档
        return msg;
    }


    public List<EcbuSteelBand> getList(EcbuSteelBandListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcbuSteelBand record = new EcbuSteelBand();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuSteelbandService.getList(record);
    }


    public void deal(EcbuSteelBand record) {
        EcbuSteelBand ecbuSteelBand = ecbuSteelbandService.getObject(record);
        if (ecbuSteelBand == null) {
            ecbuSteelbandService.insert(record);
        } else {
            record.setEcbusId(ecbuSteelBand.getEcbusId());
            ecbuSteelbandService.update(record);
        }
    }

    //通过钢带类型类型获取钢带 为计算成本提供数据
    public EcbuSteelBand getObjectPassSteelbandStr(Integer ecCompanyId, String objectStr) {
        EcbuSteelBand object = null;
        EcbuSteelBand record = new EcbuSteelBand();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuSteelBand> list = ecbuSteelbandService.getList(record);
        for (EcbuSteelBand ecbuSteelband : list) {
            Integer ecbsbId = ecbuSteelband.getEcbsbId();
            EcbSteelBand recordEcbSteelBand = new EcbSteelBand();
            recordEcbSteelBand.setEcbsbId(ecbsbId);
            EcbSteelBand steelband = ecbSteelbandService.getObject(recordEcbSteelBand);
            if (steelband.getAbbreviation().equals(objectStr)) {
                object = ecbuSteelband;
            }
        }
        return object;
    }


    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuSteelBand record = new EcbuSteelBand();
        record.setEcCompanyId(ecCompanyId);
        ecbuSteelbandService.deleteByEcCompanyId(record);
    }


    public EcbuSteelBand getObjectPassEcbusbId(Integer ecbusbId) {
        EcbuSteelBand record = new EcbuSteelBand();
        record.setEcbusId(ecbusbId);
        return ecbuSteelbandService.getObject(record);
    }

    //getListAndCount
    public SteelbandVo getListAndCount(EcbSteelbandBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcbSteelBand> list = ecbSteelbandService.getList(record);
        long count = ecbSteelbandService.getCount();
        return new SteelbandVo(list, count);
    }


    public EcbSteelBand getObject(EcbSteelbandStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbsbId = bo.getEcbsbId();
        EcbSteelBand recordEcbSteelBand = new EcbSteelBand();
        recordEcbSteelBand.setEcbsbId(ecbsbId);
        EcbSteelBand ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelBand);
        EcbuSteelBand record = new EcbuSteelBand();
        record.setEcbsbId(ecbsbId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuSteelBand ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband != null) {
            ecbSteelband.setEcbuSteelband(ecbuSteelband);
        }
        return ecbSteelband;
    }


    //public void loadData(Integer ecCompanyId) {
    //    EcbSteelBand record = new EcbSteelBand();
    //    record.setStartType(true);
    //    record.setEcCompanyId(ecCompanyId);
    //    System.out.println(CommonFunction.getGson().toJson(record));
    //    List<EcbSteelBand> list = ecbSteelbandService.getList(record);
    //    List<String> txtList = new ArrayList<>();
    //    txtList.add(CommonFunction.getGson().toJson(list));
    //    ecdCollectModel.deal(ecCompanyId, 9, txtList);
    //}


    //getListStart
    public List<EcbSteelBand> getListStart() {
        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(true);
        return ecbSteelbandService.getListStart(record);
    }

    /**
     * 获取系统材料id与用户材料id的对照map。用于初始化新公司的基础信息
     *
     * @param ecCompanyId
     * @return
     */
    public Map<Integer, Integer> getMapAll(Integer ecCompanyId) {
        EcbuSteelBand ecbuSteelband = new EcbuSteelBand();
        ecbuSteelband.setEcCompanyId(ecCompanyId);
        List<EcbuSteelBand> list = ecbuSteelbandService.getList(ecbuSteelband);
        Map<Integer, Integer> collect = list.stream()
                .collect(Collectors.toMap(EcbuSteelBand::getEcbsbId, EcbuSteelBand::getEcbusId));
        return collect;
    }
}
