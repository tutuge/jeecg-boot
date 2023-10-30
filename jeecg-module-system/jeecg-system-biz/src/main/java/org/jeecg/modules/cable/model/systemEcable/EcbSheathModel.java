package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathDealBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathListBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathSortBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.mapper.dao.systemEcable.sys.EcbSheathSysDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbSheathModel {
    @Resource
    EcbSheathSysDao sheathSysDao;

    //getList
    public SheathVo getList(EcbSheathListBo request) {
        EcbSheath record = new EcbSheath();
        record.setStartType(request.getStartType());

        List<EcbSheath> list = sheathSysDao.getList(record);
        long count = sheathSysDao.getCount(record);
        return new SheathVo(list, count);
    }

    //getObject
    public EcbSheath getObject(EcbSheathBaseBo bo) {
        return getObjectPassEcbsId(bo.getEcbsId());
    }

       // deal
       @Transactional(rollbackFor = Exception.class)
       public String deal(EcbSheathDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbsId = bo.getEcbsId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        //log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbSheath ecbSheath = sheathSysDao.getObject(record);
        String msg;
        if (ecbSheath != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ObjectUtil.isNull(ecbsId)) {//插入
                Integer sortId = 1;
                ecbSheath = sheathSysDao.getObject(null);
                if (ecbSheath != null) {
                    sortId = ecbSheath.getSortId() + 1;
                }
                record = new EcbSheath();
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
                sheathSysDao.insert(record);

                msg = "数据新增成功";
            } else {//修改
                record.setEcbsId(ecbsId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                sheathSysDao.update(record);

                msg = "数据更新成功";
            }
        }
        return msg;
    }

    //sort
    public void sort(List<EcbSheathSortBo> bos) {
        for (EcbSheathSortBo bo : bos) {
            Integer ecbsId = bo.getEcbsId();
            Integer sortId = bo.getSortId();
            EcbSheath record = new EcbSheath();
            record.setEcbsId(ecbsId);
            record.setSortId(sortId);
            sheathSysDao.update(record);
        }
    }

    //start
    public String start(EcbSheathBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        EcbSheath ecbSheath = sheathSysDao.getObject(record);
        Boolean startType = ecbSheath.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbSheath();
        record.setEcbsId(ecbSheath.getEcbsId());
        record.setStartType(startType);
        sheathSysDao.update(record);
        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbSheathBaseBo bo) {

        Integer ecbsId = bo.getEcbsId();
        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        EcbSheath ecbSheath = sheathSysDao.getObject(record);
        Integer sortId = ecbSheath.getSortId();
        record = new EcbSheath();
        record.setSortId(sortId);
        List<EcbSheath> list = sheathSysDao.getList(record);
        Integer ecbs_id;
        for (EcbSheath ecb_bag : list) {
            ecbs_id = ecb_bag.getEcbsId();
            sortId = ecb_bag.getSortId() - 1;
            record.setEcbsId(ecbs_id);
            record.setSortId(sortId);
            sheathSysDao.update(record);
        }
        record = new EcbSheath();
        record.setEcbsId(ecbsId);
        sheathSysDao.delete(record);
    }

    /***===数据模型===***/
    //getObjectPassAbbreviation
    public EcbSheath getObjectPassAbbreviation(String abbreviation) {
        EcbSheath record = new EcbSheath();
        record.setAbbreviation(abbreviation);
        return sheathSysDao.getObject(record);
    }

    //getObjectPassEcbsId
    public EcbSheath getObjectPassEcbsId(Integer ecbsId) {
        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        return sheathSysDao.getObject(record);
    }
}
