package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
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
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMicaTapeMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuMicaTapeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EcbMicaTapeModel {
    @Resource
    EcbMicaTapeMapper ecbMicaTapeMapper;
    @Resource
    private EcbuMicaTapeService ecbuMicaTapeService;


    public MicatapeVo getList(EcbMicatapeListBo bo) {
        EcbMicaTape record = new EcbMicaTape();
        record.setStartType(bo.getStartType());
        List<EcbMicaTape> list = ecbMicaTapeMapper.getSysList(record);
        long count = ecbMicaTapeMapper.getSysCount(record);
        return new MicatapeVo(list, count);
    }


    public EcbMicaTape getObject(EcbMicatapeBaseBo bo) {
        Integer ecbmId = bo.getEcbmId();
        return getObjectPassEcbmId(ecbmId);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbMicatapeDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbmId = bo.getEcbmId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbMicaTape record = new EcbMicaTape();
        record.setEcbmId(ecbmId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbMicaTape ecbMicatape = ecbMicaTapeMapper.getObject(record);

        String msg;
        if (ecbMicatape != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbmId)) {// 插入
            Integer sortId = 1;
            ecbMicatape = ecbMicaTapeMapper.getObject(null);
            if (ecbMicatape != null) {
                sortId = ecbMicatape.getSortId() + 1;
            }
            record = new EcbMicaTape();
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
            ecbMicaTapeMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbmId(ecbmId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(System.currentTimeMillis());
            ecbMicaTapeMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    public void sort(List<EcbMicatapeSortBo> bos) {
        for (EcbMicatapeSortBo bo : bos) {
            Integer ecbmId = bo.getEcbmId();
            Integer sortId = bo.getSortId();
            EcbMicaTape record = new EcbMicaTape();
            record.setEcbmId(ecbmId);
            record.setSortId(sortId);
            ecbMicaTapeMapper.updateById(record);
        }
    }


    public String start(EcbMicatapeBaseBo bo) {
        Integer ecbmId = bo.getEcbmId();
        EcbMicaTape record = new EcbMicaTape();
        record.setEcbmId(ecbmId);
        EcbMicaTape ecbMicatape = ecbMicaTapeMapper.getObject(record);
        Boolean startType = ecbMicatape.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbMicaTape();
        record.setEcbmId(ecbMicatape.getEcbmId());
        record.setStartType(startType);
        ecbMicaTapeMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMicatapeBaseBo bo) {
        Integer ecbmId = bo.getEcbmId();
        EcbuMicaTape ecbuMicaTape = new EcbuMicaTape();
        ecbuMicaTape.setEcbmId(ecbmId);
        List<EcbuMicaTape> list1 = ecbuMicaTapeService.getList(ecbuMicaTape);
        if (CollUtil.isNotEmpty(list1)) {
            throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        }
        EcbMicaTape record = new EcbMicaTape();
        record.setEcbmId(ecbmId);
        EcbMicaTape ecbMicatape = ecbMicaTapeMapper.getObject(record);
        Integer sortId = ecbMicatape.getSortId();
        record = new EcbMicaTape();
        record.setSortId(sortId);
        List<EcbMicaTape> list = ecbMicaTapeMapper.getSysList(record);
        Integer ecbm_id;
        for (EcbMicaTape tape : list) {
            ecbm_id = tape.getEcbmId();
            sortId = tape.getSortId() - 1;
            record.setEcbmId(ecbm_id);
            record.setSortId(sortId);
            ecbMicaTapeMapper.updateById(record);
        }
        ecbMicaTapeMapper.deleteById(ecbmId);
    }


    // getObjectPassAbbreviation
    public EcbMicaTape getObjectPassAbbreviation(String abbreviation) {
        EcbMicaTape record = new EcbMicaTape();
        record.setAbbreviation(abbreviation);
        return ecbMicaTapeMapper.getObject(record);
    }

    // getObjectPassEcbmId
    public EcbMicaTape getObjectPassEcbmId(Integer ecbmId) {
        EcbMicaTape record = new EcbMicaTape();
        record.setEcbmId(ecbmId);
        return ecbMicaTapeMapper.getObject(record);
    }
}
