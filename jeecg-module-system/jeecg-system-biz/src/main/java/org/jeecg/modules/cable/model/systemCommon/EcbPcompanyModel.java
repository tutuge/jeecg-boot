package org.jeecg.modules.cable.model.systemCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.jeecg.modules.cable.service.systemCommon.EcbPcompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbPcompanyModel {
    @Resource
    EcbPcompanyService ecbPcompanyService;

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbPcompanyDealBo bo) {

        Integer ecbpId = bo.getEcbpId();
        Integer platformId = bo.getPlatformId();
        String pcName = bo.getPcName();
        BigDecimal pcPercent = bo.getPcPercent();
        String description = bo.getDescription();

        EcbPcompany record = new EcbPcompany();
        record.setEcbpId(ecbpId);
        record.setPcName(pcName);
        EcbPcompany ecbuPcompany = ecbPcompanyService.getObject(record);
        String msg;
        if (ecbuPcompany != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbpId)) {// 插入
            Integer sortId = 1;
            record = new EcbPcompany();
            ecbuPcompany = ecbPcompanyService.getObject(record);
            if (ecbuPcompany != null) {
                sortId = ecbuPcompany.getSortId() + 1;
            }
            record = new EcbPcompany();
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecbPcompanyService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbpId(ecbpId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            ecbPcompanyService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    // getList
    public EcbPcompanyVo getList(EcbPcompanyListBo bo) {
        EcbPcompany record = new EcbPcompany();
        record.setStartType(bo.getStartType());
        List<EcbPcompany> list = ecbPcompanyService.getList(record);
        long count = ecbPcompanyService.getCount(record);
        return new EcbPcompanyVo(list, count);
    }

    // getObject
    public EcbPcompany getObject(EcbPcompanyBaseBo bo) {
        Integer ecbpId = bo.getEcbpId();
        EcbPcompany record = new EcbPcompany();
        record.setEcbpId(ecbpId);
        return ecbPcompanyService.getObject(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbPcompanySortBo> bos) {
        for (EcbPcompanySortBo bo : bos) {
            Integer ecbpId = bo.getEcbpId();
            Integer sortId = bo.getSortId();
            EcbPcompany record = new EcbPcompany();
            record.setEcbpId(ecbpId);
            record.setSortId(sortId);
            ecbPcompanyService.update(record);
        }
    }

    // start
    public String start(EcbPcompanyBaseBo bo) {

        Integer ecbpId = bo.getEcbpId();
        EcbPcompany record = new EcbPcompany();
        record.setEcbpId(ecbpId);
        EcbPcompany ecbPcompany = ecbPcompanyService.getObject(record);
        Boolean startType = ecbPcompany.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbPcompany();
        record.setEcbpId(ecbPcompany.getEcbpId());
        record.setStartType(startType);
        ecbPcompanyService.update(record);

        return msg;
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbPcompanyBaseBo bo) {
        Integer ecbpId = bo.getEcbpId();
        EcbPcompany record = new EcbPcompany();
        record.setEcbpId(ecbpId);
        EcbPcompany ecbPcompany = ecbPcompanyService.getObject(record);
        Integer sortId = ecbPcompany.getSortId();
        record = new EcbPcompany();
        record.setSortId(sortId);
        List<EcbPcompany> list = ecbPcompanyService.getList(record);
        Integer ecbp_id;
        for (EcbPcompany ecb_pcompany : list) {
            ecbp_id = ecb_pcompany.getEcbpId();
            sortId = ecb_pcompany.getSortId() - 1;
            record.setEcbpId(ecbp_id);
            record.setSortId(sortId);
            ecbPcompanyService.update(record);
        }
        record = new EcbPcompany();
        record.setEcbpId(ecbpId);
        ecbPcompanyService.delete(record);

    }

    /***===数据模型===***/
    // getListStart
    public List<EcbPcompany> getListStart() {
        EcbPcompany record = new EcbPcompany();
        record.setStartType(true);
        return ecbPcompanyService.getList(record);
    }
}
