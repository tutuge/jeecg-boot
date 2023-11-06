package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingDealBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingSortBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.vo.InfillingVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInfillingMapper;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbInfillingModel {
    @Resource
    EcbInfillingMapper infillingSysMapper;


    public InfillingVo getList(EcbInfillingBo bo) {
        EcbInfilling record = new EcbInfilling();
        record.setStartType(bo.getStartType());
        List<EcbInfilling> list = infillingSysMapper.getSysList(record);
        long count = infillingSysMapper.getSysCount(record);
        return new InfillingVo(list, count);
    }


    public EcbInfilling getObject(EcbInfillingBaseBo bo) {
        Integer ecbinId = bo.getEcbinId();
        return getObjectPassEcbinId(ecbinId);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbInfillingDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbinId = bo.getEcbinId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbInfilling ecbInfilling = infillingSysMapper.getSysObject(record);
        String msg;
        if (ecbInfilling != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbinId)) {// 插入
            Integer sortId = 1;
            ecbInfilling = infillingSysMapper.getSysObject(null);
            if (ecbInfilling != null) {
                sortId = ecbInfilling.getSortId() + 1;
            }
            record = new EcbInfilling();
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
            infillingSysMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbinId(ecbinId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(System.currentTimeMillis());
            infillingSysMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbInfillingSortBo> bos) {
        for (EcbInfillingSortBo bo : bos) {
            Integer ecbinId = bo.getEcbinId();
            Integer sortId = bo.getSortId();
            EcbInfilling record = new EcbInfilling();
            record.setEcbinId(ecbinId);
            record.setSortId(sortId);
            infillingSysMapper.updateById(record);
        }
    }


    public String start(EcbInfillingBaseBo bo) {

        Integer ecbinId = bo.getEcbinId();
        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        EcbInfilling ecbInfilling = infillingSysMapper.getSysObject(record);
        Boolean startType = ecbInfilling.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbInfilling();
        record.setEcbinId(ecbInfilling.getEcbinId());
        record.setStartType(startType);
        infillingSysMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbInfillingBaseBo bo) {
        Integer ecbinId = bo.getEcbinId();
        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        EcbInfilling ecbInfilling = infillingSysMapper.getSysObject(record);
        Integer sortId = ecbInfilling.getSortId();
        record = new EcbInfilling();
        record.setSortId(sortId);
        List<EcbInfilling> list = infillingSysMapper.getSysList(record);
        Integer ecbin_id;
        for (EcbInfilling ecb_infilling : list) {
            ecbin_id = ecb_infilling.getEcbinId();
            sortId = ecb_infilling.getSortId() - 1;
            record.setEcbinId(ecbin_id);
            record.setSortId(sortId);
            infillingSysMapper.updateById(record);
        }
        infillingSysMapper.deleteById(ecbinId);
    }

    /***===数据模型===***/

    // getObjectPassAbbreviation
    public EcbInfilling getObjectPassAbbreviation(String abbreviation) {
        EcbInfilling record = new EcbInfilling();
        record.setAbbreviation(abbreviation);
        return infillingSysMapper.getSysObject(record);
    }

    // getObjectPassEcbinId
    public EcbInfilling getObjectPassEcbinId(Integer ecbinId) {
        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        return infillingSysMapper.getSysObject(record);
    }
}
