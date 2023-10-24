package org.jeecg.modules.cable.model.systemEcable;

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
import org.jeecg.modules.cable.mapper.dao.systemEcable.sys.EcbInfillingSysDao;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EcbInfillingModel {
    @Resource
    EcbInfillingSysDao ecbInfillingSysDao;

    //getList
    public InfillingVo getList(EcbInfillingBo bo) {
        EcbInfilling record = new EcbInfilling();
        record.setStartType(bo.getStartType());
        List<EcbInfilling> list = ecbInfillingSysDao.getList(record);
        long count = ecbInfillingSysDao.getCount(record);
        return new InfillingVo(list, count);
    }

    //getObject
    public EcbInfilling getObject(EcbInfillingBaseBo bo) {

        int ecbinId = bo.getEcbinId();
        return getObjectPassEcbinId(ecbinId);
    }

    //deal
    public String deal(EcbInfillingDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        int ecbinId = bo.getEcbinId();
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
        EcbInfilling ecbInfilling = ecbInfillingSysDao.getObject(record);
        String msg;
        if (ecbInfilling != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ecbinId == 0) {//插入
                int sortId = 1;
                ecbInfilling = ecbInfillingSysDao.getObject(null);
                if (ecbInfilling != null) {
                    sortId = ecbInfilling.getSortId() + 1;
                }
                record = new EcbInfilling();
//                record.setEcaId(ecaId);
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
                ecbInfillingSysDao.insert(record);

                msg = "数据新增成功";
            } else {//修改
                record.setEcbinId(ecbinId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                ecbInfillingSysDao.update(record);

                msg = "数据更新成功";
            }
        }

        return msg;
    }

    //sort
    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbInfillingSortBo> bos) {
        for (EcbInfillingSortBo bo : bos) {
            int ecbinId = bo.getEcbinId();
            int sortId = bo.getSortId();
            EcbInfilling record = new EcbInfilling();
            record.setEcbinId(ecbinId);
            record.setSortId(sortId);
            ecbInfillingSysDao.update(record);
        }
    }

    //start
    public String start(EcbInfillingBaseBo bo) {

        int ecbinId = bo.getEcbinId();
        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        EcbInfilling ecbInfilling = ecbInfillingSysDao.getObject(record);
        boolean startType = ecbInfilling.getStartType();
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
        ecbInfillingSysDao.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbInfillingBaseBo bo) {

        int ecbinId = bo.getEcbinId();
        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        EcbInfilling ecbInfilling = ecbInfillingSysDao.getObject(record);
        int sortId = ecbInfilling.getSortId();
        record = new EcbInfilling();
        record.setSortId(sortId);
        List<EcbInfilling> list = ecbInfillingSysDao.getList(record);
        int ecbin_id;
        for (EcbInfilling ecb_infilling : list) {
            ecbin_id = ecb_infilling.getEcbinId();
            sortId = ecb_infilling.getSortId() - 1;
            record.setEcbinId(ecbin_id);
            record.setSortId(sortId);
            ecbInfillingSysDao.update(record);
        }
        record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        ecbInfillingSysDao.delete(record);
    }

    /***===数据模型===***/

    //getObjectPassAbbreviation
    public EcbInfilling getObjectPassAbbreviation(String abbreviation) {
        EcbInfilling record = new EcbInfilling();
        record.setAbbreviation(abbreviation);
        return ecbInfillingSysDao.getObject(record);
    }

    //getObjectPassEcbinId
    public EcbInfilling getObjectPassEcbinId(int ecbinId) {
        EcbInfilling record = new EcbInfilling();
        record.setEcbinId(ecbinId);
        return ecbInfillingSysDao.getObject(record);
    }
}
