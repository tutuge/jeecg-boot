package org.jeecg.modules.cable.model.systemEcable;

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
import org.jeecg.modules.cable.mapper.dao.systemEcable.sys.EcbMicatapeSysDao;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EcbMicatapeModel {
    @Resource
    EcbMicatapeSysDao micatapeSysDao;


    //getList
    public MicatapeVo getList(EcbMicatapeListBo bo) {
        EcbMicatape record = new EcbMicatape();
        record.setStartType(bo.getStartType());
        List<EcbMicatape> list = micatapeSysDao.getList(record);
        long count = micatapeSysDao.getCount(record);
        return new MicatapeVo(list, count);
    }

    //getObject
    public EcbMicatape getObject(EcbMicatapeBaseBo bo) {
        int ecbmId = bo.getEcbmId();
        return getObjectPassEcbmId(ecbmId);
    }

    //deal
    public String deal(EcbMicatapeDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        int ecbmId = bo.getEcbmId();
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
        EcbMicatape ecbMicatape = micatapeSysDao.getObject(record);

        String msg;
        if (ecbMicatape != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ecbmId == 0) {//插入
                int sortId = 1;
                ecbMicatape = micatapeSysDao.getObject(null);
                if (ecbMicatape != null) {
                    sortId = ecbMicatape.getSortId() + 1;
                }
                record = new EcbMicatape();
//                    record.setEcaId(ecaId);
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
                micatapeSysDao.insert(record);
                msg = "数据新增成功";
            } else {//修改
                record.setEcbmId(ecbmId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                micatapeSysDao.update(record);
                msg = "数据更新成功";
            }
        }
        return msg;
    }

    //sort
    public void sort(List<EcbMicatapeSortBo> bos) {
        for (EcbMicatapeSortBo bo : bos) {
            int ecbmId = bo.getEcbmId();
            int sortId = bo.getSortId();
            EcbMicatape record = new EcbMicatape();
            record.setEcbmId(ecbmId);
            record.setSortId(sortId);
            micatapeSysDao.update(record);
        }
    }

    //start
    public String start(EcbMicatapeBaseBo bo) {
        int ecbmId = bo.getEcbmId();
        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        EcbMicatape ecbMicatape = micatapeSysDao.getObject(record);
        boolean startType = ecbMicatape.getStartType();
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
        micatapeSysDao.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbMicatapeBaseBo bo) {

        int ecbmId = bo.getEcbmId();
        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        EcbMicatape ecbMicatape = micatapeSysDao.getObject(record);
        int sortId = ecbMicatape.getSortId();
        record = new EcbMicatape();
        record.setSortId(sortId);
        List<EcbMicatape> list = micatapeSysDao.getList(record);
        int ecbm_id;
        for (EcbMicatape ecb_micatape : list) {
            ecbm_id = ecb_micatape.getEcbmId();
            sortId = ecb_micatape.getSortId() - 1;
            record.setEcbmId(ecbm_id);
            record.setSortId(sortId);
            micatapeSysDao.update(record);
        }
        record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        micatapeSysDao.delete(record);
    }

    /***===数据模型===***/
    //getObjectPassAbbreviation
    public EcbMicatape getObjectPassAbbreviation(String abbreviation) {
        EcbMicatape record = new EcbMicatape();
        record.setAbbreviation(abbreviation);
        return micatapeSysDao.getObject(record);
    }

    //getObjectPassEcbmId
    public EcbMicatape getObjectPassEcbmId(int ecbmId) {
        EcbMicatape record = new EcbMicatape();
        record.setEcbmId(ecbmId);
        return micatapeSysDao.getObject(record);
    }
}
