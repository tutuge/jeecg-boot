package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagDealBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagListBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagSortBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.vo.BagVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbBagMapper;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EcbBagModel {
    @Resource
    EcbBagMapper ecbBagMapper;


    public BagVo getList(EcbBagListBo request) {
        EcbBag record = new EcbBag();
        record.setStartType(request.getStartType());

        List<EcbBag> list = ecbBagMapper.getList(record);
        long count = ecbBagMapper.getSysCount(record);
        return new BagVo(list, count);
    }

    // getObject
    public EcbBag getObject(EcbBagBaseBo bo) {
        return getObjectPassEcbbId(bo.getEcbbId());
    }

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbBagDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecbbId = bo.getEcbbId();
        String abbreviation = bo.getAbbreviation();
        String fullName = bo.getFullName();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbBag record = new EcbBag();
        record.setEcbbId(ecbbId);
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        log.info("record + " + CommonFunction.getGson().toJson(record));
        EcbBag ecbBag = ecbBagMapper.getSysObject(record);
        String msg;
        if (ecbBag != null) {
            throw new RuntimeException("数据简称或全称已占用");
        } else {
            if (ObjectUtil.isNull(ecbbId)) {// 插入
                Integer sortId = 1;
                ecbBag = ecbBagMapper.getSysObject(null);
                if (ecbBag != null) {
                    sortId = ecbBag.getSortId() + 1;
                }
                record = new EcbBag();
//                    record.setEcaId(sysUser.getId());
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
                ecbBagMapper.insert(record);
                msg = "数据新增成功";
            } else {// 修改
                record.setEcbbId(ecbbId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setUnitPrice(unitPrice);
                record.setDensity(density);
                record.setDescription(description);
                record.setUpdateTime(System.currentTimeMillis());
                ecbBagMapper.updateById(record);
                msg = "数据更新成功";
            }
        }
        return msg;
    }

    // sort
    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbBagSortBo> bos) {
        for (EcbBagSortBo bo : bos) {
            Integer ecbbId = bo.getEcbbId();
            Integer sortId = bo.getSortId();
            EcbBag record = new EcbBag();
            record.setEcbbId(ecbbId);
            record.setSortId(sortId);
            ecbBagMapper.updateById(record);
        }
    }

    // start
    public String start(EcbBagBaseBo bo) {
        Integer ecbbId = bo.getEcbbId();
        EcbBag record = new EcbBag();
        record.setEcbbId(ecbbId);
        EcbBag ecbBag = ecbBagMapper.getSysObject(record);
        Boolean startType = ecbBag.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbBag();
        record.setEcbbId(ecbBag.getEcbbId());
        record.setStartType(startType);
        ecbBagMapper.updateById(record);
        return msg;
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbBagBaseBo bo) {

        Integer ecbbId = bo.getEcbbId();
        EcbBag record = new EcbBag();
        record.setEcbbId(ecbbId);
        EcbBag ecbBag = ecbBagMapper.getSysObject(record);
        Integer sortId = ecbBag.getSortId();
        record = new EcbBag();
        record.setSortId(sortId);
        List<EcbBag> list = ecbBagMapper.getSysList(record);
        Integer ecbb_id;
        for (EcbBag ecb_bag : list) {
            ecbb_id = ecb_bag.getEcbbId();
            sortId = ecb_bag.getSortId() - 1;
            record.setEcbbId(ecbb_id);
            record.setSortId(sortId);
            ecbBagMapper.updateById(record);
        }
        record = new EcbBag();
        record.setEcbbId(ecbbId);
        ecbBagMapper.deleteById(record);
    }


    /***===以下是数据模型===***/
    // getObjectPassAbbreviation
    public EcbBag getObjectPassAbbreviation(String abbreviation) {
        LambdaQueryWrapper<EcbBag> eq = Wrappers.lambdaQuery(EcbBag.class).eq(EcbBag::getAbbreviation, abbreviation);
        return ecbBagMapper.selectOne(eq);
    }

    // getObjectPassEcbbId
    public EcbBag getObjectPassEcbbId(Integer ecbbId) {
        EcbBag record = new EcbBag();
        record.setEcbbId(ecbbId);
        return ecbBagMapper.getSysObject(record);
    }


}
