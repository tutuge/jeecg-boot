package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
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
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSheathMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class EcbSheathModel {
    @Resource
    EcbSheathMapper sheathMapper;
    @Resource
    private EcbuSheathService ecbuSheathService;


    public SheathVo getList(EcbSheathListBo request) {
        EcbSheath record = new EcbSheath();
        record.setStartType(request.getStartType());

        List<EcbSheath> list = sheathMapper.getSysList(record);
        long count = sheathMapper.getSysCount(record);
        return new SheathVo(list, count);
    }


    public EcbSheath getObject(EcbSheathBaseBo bo) {
        return getObjectPassEcbsId(bo.getEcbsId());
    }


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
        // log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbSheath ecbSheath = sheathMapper.getSysObject(record);
        String msg;
        if (ecbSheath != null) {
            throw new RuntimeException("数据简称或全称已占用");
        }
        if (ObjectUtil.isNull(ecbsId)) {// 插入
            Integer sortId = 1;
            ecbSheath = sheathMapper.getSysObject(null);
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
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            sheathMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbsId(ecbsId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(new Date());
            sheathMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


    public void sort(List<EcbSheathSortBo> bos) {
        for (EcbSheathSortBo bo : bos) {
            Integer ecbsId = bo.getEcbsId();
            Integer sortId = bo.getSortId();
            EcbSheath record = new EcbSheath();
            record.setEcbsId(ecbsId);
            record.setSortId(sortId);
            sheathMapper.updateById(record);
        }
    }


    public String start(EcbSheathBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        EcbSheath ecbSheath = sheathMapper.getSysObject(record);
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
        sheathMapper.updateById(record);
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbSheathBaseBo bo) {
        Integer ecbsId = bo.getEcbsId();
        EcbuSheath sheath = new EcbuSheath();
        sheath.setEcbsId(ecbsId);
        List<EcbuSheath> list1 = ecbuSheathService.getList(sheath);
        if (CollUtil.isNotEmpty(list1)) {
            throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        }
        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        EcbSheath ecbSheath = sheathMapper.getSysObject(record);
        Integer sortId = ecbSheath.getSortId();
        record = new EcbSheath();
        record.setSortId(sortId);
        List<EcbSheath> list = sheathMapper.getSysList(record);
        Integer ecbs_id;
        for (EcbSheath ecb_bag : list) {
            ecbs_id = ecb_bag.getEcbsId();
            sortId = ecb_bag.getSortId() - 1;
            record.setEcbsId(ecbs_id);
            record.setSortId(sortId);
            sheathMapper.updateById(record);
        }
        sheathMapper.deleteById(ecbsId);
    }


    // getObjectPassAbbreviation
    public EcbSheath getObjectPassAbbreviation(String abbreviation) {
        EcbSheath record = new EcbSheath();
        record.setAbbreviation(abbreviation);
        return sheathMapper.getSysObject(record);
    }

    // getObjectPassEcbsId
    public EcbSheath getObjectPassEcbsId(Integer ecbsId) {
        EcbSheath record = new EcbSheath();
        record.setEcbsId(ecbsId);
        return sheathMapper.getSysObject(record);
    }
}
