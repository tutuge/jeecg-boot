package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeDealBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeListBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeSortBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMicatapeMapper;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EcbMicatapeModel {
    @Resource
    EcbMicatapeMapper micatapeSysMapper;


    // getList
    public MicatapeVo getList(EcbMicatapeListBo bo) {
        EcbMicatape record = new EcbMicatape();
        record.setStartType(bo.getStartType());
        List<EcbMicatape> list = micatapeSysMapper.getSysList(record);
        long count = micatapeSysMapper.getSysCount(record);
        return new MicatapeVo(list, count);
    }

    // getObject
    public EcbMicatape getObject(EcbMicatapeBaseBo bo) {
        Integer ecbmId = bo.getEcbmId();
        return getObjectPassEcbmId(ecbmId);
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbMicatapeDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbmId = bo.getEcbmId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbMicatape ecbMicatape = micatapeSysMapper.getObject(record);

        String msg;
        if (ecbMicatape != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbmId)) {// 插入
            Integer sortId = 1;
            ecbMicatape = micatapeSysMapper.getObject(null);
            if (ecbMicatape != null) {
                sortId = ecbMicatape.getSortId() + 1;
            }
            record = new EcbMicatape();
            record.setEcaId(sysUser.getUserId());
            record.setEcaName(sysUser.getUsername());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setAddTime(System.currentTimeMillis());
            record.setUpdateTime(System.currentTimeMillis());
            micatapeSysMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbmId(ecbmId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(System.currentTimeMillis());
            micatapeSysMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }

    // sort
    public void sort(List<EcbMicatapeSortBo> bos) {
        for (EcbMicatapeSortBo bo : bos) {
            Integer ecbmId = bo.getEcbmId();
            Integer sortId = bo.getSortId();
            EcbMicatape record = new EcbMicatape();
            record.setEcbmId(ecbmId);
            record.setSortId(sortId);
            micatapeSysMapper.updateById(record);
        }
    }

    // start
    public String start(EcbMicatapeBaseBo bo) {
        Integer ecbmId = bo.getEcbmId();
        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        EcbMicatape ecbMicatape = micatapeSysMapper.getObject(record);
        Boolean startType = ecbMicatape.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;

            msg = "数据禁用成功";
        }
        record = new EcbMicatape();
        record.setEcbmId(ecbMicatape.getEcbmId());
        record.setStartType(startType);
        micatapeSysMapper.updateById(record);
        return msg;
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMicatapeBaseBo bo) {

        Integer ecbmId = bo.getEcbmId();
        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        EcbMicatape ecbMicatape = micatapeSysMapper.getObject(record);
        Integer sortId = ecbMicatape.getSortId();
        record = new EcbMicatape();
        record.setSortId(sortId);
        List<EcbMicatape> list = micatapeSysMapper.getSysList(record);
        Integer ecbm_id;
        for (EcbMicatape ecb_micatape : list) {
            ecbm_id = ecb_micatape.getEcbmId();
            sortId = ecb_micatape.getSortId() - 1;
            record.setEcbmId(ecbm_id);
            record.setSortId(sortId);
            micatapeSysMapper.updateById(record);
        }
        micatapeSysMapper.deleteById(ecbmId);
    }

    /***===数据模型===***/
    // getObjectPassAbbreviation
    public EcbMicatape getObjectPassAbbreviation(String abbreviation) {
        EcbMicatape record = new EcbMicatape();
        record.setAbbreviation(abbreviation);
        return micatapeSysMapper.getObject(record);
    }

    // getObjectPassEcbmId
    public EcbMicatape getObjectPassEcbmId(Integer ecbmId) {
        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        return micatapeSysMapper.getObject(record);
    }
}
