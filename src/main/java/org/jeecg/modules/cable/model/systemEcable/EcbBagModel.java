package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbBagMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EcbBagModel {
    @Resource
    EcbBagMapper ecbBagMapper;
    @Resource
    private EcbuBagService ecbuBagService;


    public BagVo getList(EcbBagListBo bo) {
        EcbBag record = new EcbBag();
        record.setStartType(bo.getStartType());
        List<EcbBag> list = ecbBagMapper.getList(record);
        long count = ecbBagMapper.getSysCount(record);
        return new BagVo(list, count);
    }


    public EcbBag getObject(EcbBagBaseBo bo) {
        return getObjectPassEcbbId(bo.getEcbbId());
    }


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
        }
        if (ObjectUtil.isNull(ecbbId)) {// 插入
            Integer sortId = 1;
            ecbBag = ecbBagMapper.getSysObject(null);
            if (ecbBag != null) {
                sortId = ecbBag.getSortId() + 1;
            }
            record = new EcbBag();
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
            ecbBagMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbbId(ecbbId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(new Date());
            ecbBagMapper.updateById(record);
            msg = "数据更新成功";
        }
        return msg;
    }


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


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbBagBaseBo bo) {
        Integer ecbbId = bo.getEcbbId();
        EcbuBag ecbuBag = new EcbuBag();
        ecbuBag.setEcbbId(ecbbId);
        List<EcbuBag> list = ecbuBagService.getList(ecbuBag);
        if (CollUtil.isNotEmpty(list)) {
            throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        }
        EcbBag record = new EcbBag();
        record.setEcbbId(ecbbId);
        EcbBag ecbBag = ecbBagMapper.getSysObject(record);
        Integer sortId = ecbBag.getSortId();
        ecbBagMapper.reduceSort(sortId);
        ecbBagMapper.deleteById(ecbbId);
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


    public Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassBagStr() {
        EcbBag record = new EcbBag();
        record.setStartType(true);
        List<EcbBag> list = ecbBagMapper.getListStart(record);
        Map<String, Integer> abbreviationMap = new HashMap<>();
        Map<String, Integer> fullNameMap = new HashMap<>();
        for (EcbBag ecbBag : list) {
            Integer ecbbId = ecbBag.getEcbbId();
            if (ObjUtil.isNotNull(ecbBag)) {
                String abbreviation = ecbBag.getAbbreviation();
                if (StrUtil.isNotBlank(abbreviation)) {
                    abbreviationMap.put(abbreviation, ecbbId);
                }
                String fullName = ecbBag.getFullName();
                if (StrUtil.isNotBlank(fullName)) {
                    fullNameMap.put(fullName, ecbbId);
                }
            }
        }
        return new Pair<>(abbreviationMap, fullNameMap);
    }
}
