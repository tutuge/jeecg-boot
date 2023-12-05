package org.jeecg.modules.cable.model.systemCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyListVo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.controller.userCommon.platformCompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.service.systemCommon.EcbPlatformCompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbPlatformCompanyModel {
    @Resource
    EcbPlatformCompanyService ecbPlatformCompanyService;


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbPlatformCompanyDealBo bo) {

        Integer ecbpId = bo.getEcbpId();
        Integer platformId = bo.getPlatformId();
        String pcName = bo.getPcName();
        BigDecimal pcPercent = bo.getPcPercent();
        String description = bo.getDescription();

        EcbPlatformCompany record = new EcbPlatformCompany();
        record.setEcbpId(ecbpId);
        record.setPcName(pcName);
        EcbPlatformCompany ecbuPcompany = ecbPlatformCompanyService.getObject(record);
        String msg;
        if (ecbuPcompany != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbpId)) {// 插入
            int sortId = 1;
            record = new EcbPlatformCompany();
            ecbuPcompany = ecbPlatformCompanyService.getObject(record);
            if (ecbuPcompany != null) {
                sortId = ecbuPcompany.getSortId() + 1;
            }
            record = new EcbPlatformCompany();
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecbPlatformCompanyService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbpId(ecbpId);
            record.setPlatformId(platformId);
            record.setPcName(pcName);
            record.setPcPercent(pcPercent);
            record.setDescription(description);
            ecbPlatformCompanyService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public EcbPlatformCompanyListVo getList(EcbPlatformCompanyListBo bo) {
        EcbPlatformCompany record = new EcbPlatformCompany();
        record.setStartType(bo.getStartType());
        List<EcbPlatformCompanyVo> list = ecbPlatformCompanyService.getList(record);
        long count = ecbPlatformCompanyService.getCount(record);
        return new EcbPlatformCompanyListVo(list, count);
    }


    public EcbPlatformCompanyVo getObject(EcbPlatformCompanyBaseBo bo) {
        Integer ecbpId = bo.getEcbpId();
        EcbPlatformCompany record = new EcbPlatformCompany();
        record.setEcbpId(ecbpId);
        return ecbPlatformCompanyService.getObject(record);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbPlatformCompanySortBo> bos) {
        for (EcbPlatformCompanySortBo bo : bos) {
            Integer ecbpId = bo.getEcbpId();
            Integer sortId = bo.getSortId();
            EcbPlatformCompany record = new EcbPlatformCompany();
            record.setEcbpId(ecbpId);
            record.setSortId(sortId);
            ecbPlatformCompanyService.update(record);
        }
    }


    public String start(EcbPlatformCompanyBaseBo bo) {

        Integer ecbpId = bo.getEcbpId();
        EcbPlatformCompany record = new EcbPlatformCompany();
        record.setEcbpId(ecbpId);
        EcbPlatformCompany ecbPlatformCompany = ecbPlatformCompanyService.getObject(record);
        Boolean startType = ecbPlatformCompany.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbPlatformCompany();
        record.setEcbpId(ecbPlatformCompany.getEcbpId());
        record.setStartType(startType);
        ecbPlatformCompanyService.update(record);

        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbPlatformCompanyBaseBo bo) {
        Integer ecbpId = bo.getEcbpId();
        EcbPlatformCompany record = new EcbPlatformCompany();
        record.setEcbpId(ecbpId);
        EcbPlatformCompany ecbPlatformCompany = ecbPlatformCompanyService.getObject(record);
        Integer sortId = ecbPlatformCompany.getSortId();
        record = new EcbPlatformCompany();
        record.setSortId(sortId);
        List<EcbPlatformCompanyVo> list = ecbPlatformCompanyService.getList(record);
        Integer ecbp_id;
        for (EcbPlatformCompany ecb_pcompany : list) {
            ecbp_id = ecb_pcompany.getEcbpId();
            sortId = ecb_pcompany.getSortId() - 1;
            record.setEcbpId(ecbp_id);
            record.setSortId(sortId);
            ecbPlatformCompanyService.update(record);
        }
        record = new EcbPlatformCompany();
        record.setEcbpId(ecbpId);
        ecbPlatformCompanyService.delete(record);
    }


    public List<EcbPlatformCompanyVo> getListStart() {
        EcbPlatformCompany record = new EcbPlatformCompany();
        record.setStartType(true);
        return ecbPlatformCompanyService.getList(record);
    }
}
