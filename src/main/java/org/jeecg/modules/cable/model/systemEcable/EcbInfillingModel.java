package org.jeecg.modules.cable.model.systemEcable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInfillingMapper;
import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcbInfillingModel {
    @Resource
    EcbInfillingMapper infillingSysMapper;
    @Resource
    private EcbuInfillingService ecbuInfillingService;


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
            record.setAddTime(new Date());
            record.setUpdateTime(new Date());
            infillingSysMapper.insert(record);
            msg = "数据新增成功";
        } else {// 修改
            record.setEcbinId(ecbinId);
            record.setAbbreviation(abbreviation);
            record.setFullName(fullName);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            record.setUpdateTime(new Date());
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
        EcbuInfilling infilling = new EcbuInfilling();
        infilling.setEcbinId(ecbinId);
        List<EcbuInfilling> list1 = ecbuInfillingService.getList(infilling);
        if (CollUtil.isNotEmpty(list1)) {
            throw new RuntimeException("此记录已被用户记录关联使用，无法删除！");
        }
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

    public Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassInfillingStr() {
        EcbInfilling record = new EcbInfilling();
        record.setStartType(true);
        List<EcbInfilling> list = infillingSysMapper.getListStart(record);
        Map<String, Integer> abbreviationMap = new HashMap<>();
        Map<String, Integer> fullNameMap = new HashMap<>();
        for (EcbInfilling ecbInfilling : list) {
            Integer ecbinId = ecbInfilling.getEcbinId();
            if (ObjUtil.isNotNull(ecbInfilling)) {
                String abbreviation = ecbInfilling.getAbbreviation();
                if (StrUtil.isNotBlank(abbreviation)) {
                    abbreviationMap.put(abbreviation, ecbinId);
                }
                String fullName = ecbInfilling.getFullName();
                if (StrUtil.isNotBlank(fullName)) {
                    fullNameMap.put(fullName, ecbinId);
                }
            }
        }
        return new Pair<>(abbreviationMap, fullNameMap);
    }
}
