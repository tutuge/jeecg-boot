package org.jeecg.modules.cable.model.systemQuality;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelBaseBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelDealBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelListBo;
import org.jeecg.modules.cable.controller.systemQuality.level.bo.EcqLevelSortBo;
import org.jeecg.modules.cable.controller.systemQuality.level.vo.SystemLevelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcSilkService;
import org.jeecg.modules.cable.service.systemQuality.EcqLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcqLevelModel {
    @Resource
    EcqLevelService ecqLevelService;
    @Resource
    EcSilkService ecSilkService;


    public SystemLevelVo getList(EcqLevelListBo bo) {
        EcqLevel record = new EcqLevel();
        record.setStartType(bo.getStartType());
        List<EcqLevel> list = ecqLevelService.getList(record);
        long count = ecqLevelService.getCount(record);
        return new SystemLevelVo(list, count);
    }


    public EcqLevel getObject(EcqLevelBaseBo bo) {
        EcqLevel record = new EcqLevel();
        Integer ecqlId = bo.getEcqlId();
        record.setEcqlId(ecqlId);
        EcqLevel object = ecqLevelService.getObject(record);
        EcSilk silk = new EcSilk();
        silk.setEcsId(object.getEcsId());
        EcSilk ecSilk = ecSilkService.getObject(silk);
        object.setEcSilk(ecSilk);
        return object;
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcqLevelDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqlId = bo.getEcqlId();
        Integer ecsId = bo.getEcsId();
        Integer ecbcId = bo.getEcbcId();
        String name = bo.getName();
        String description = bo.getDescription();

        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqlId);
        record.setName(name);
        String msg;
        if (ObjectUtil.isNull(ecqlId)) {// 插入
            int sortId = 1;
            EcqLevel ecquLevel = ecqLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record = new EcqLevel();
            record.setEcsId(ecsId);
            record.setEcbcId(ecbcId);
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPowerId(2);// 自有库
            record.setName(name);
            record.setDefaultType(false);
            record.setDescription(description);
            ecqLevelService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcqlId(ecqlId);
            record.setEcsId(ecsId);
            record.setEcbcId(ecbcId);
            record.setName(name);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecqLevelService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public void sort(List<EcqLevelSortBo> bos) {
        for (EcqLevelSortBo bo : bos) {
            Integer ecqulId = bo.getEcqlId();
            Integer sortId = bo.getSortId();
            EcqLevel record = new EcqLevel();
            record.setEcqlId(ecqulId);
            record.setSortId(sortId);
            ecqLevelService.update(record);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcqLevelBaseBo bo) {
        Integer ecqlId = bo.getEcqlId();
        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqlId);
        EcqLevel ecquLevel = ecqLevelService.getObject(record);
        Integer sortId = ecquLevel.getSortId();
        record = new EcqLevel();
        record.setSortId(sortId);
        List<EcqLevel> list = ecqLevelService.getList(record);
        Integer ecql_id;
        for (EcqLevel ecqu_level : list) {
            ecql_id = ecqu_level.getEcqlId();
            sortId = ecqu_level.getSortId() - 1;
            record.setEcqlId(ecql_id);
            record.setSortId(sortId);
            ecqLevelService.update(record);
        }
        ecqLevelService.delete(ecqlId);
    }


    public String start(EcqLevelBaseBo bo) {
        Integer ecqlId = bo.getEcqlId();
        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqlId);
        EcqLevel ecquLevel = ecqLevelService.getObject(record);
        Boolean startType = ecquLevel.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcqLevel();
        record.setEcqlId(ecquLevel.getEcqlId());
        record.setStartType(startType);
        ecqLevelService.update(record);
        return msg;
    }


    public void defaultType(EcqLevelBaseBo bo) {
        Integer ecqlId = bo.getEcqlId();
        EcqLevel ecqLevel = getObjectPassEcqulId(ecqlId);
        //先将同型号类型的全部设为非默认
        EcqLevel record = new EcqLevel();
        record.setEcsId(ecqLevel.getEcsId());
        record.setDefaultType(false);
        ecqLevelService.update(record);
        //再将对应的主键的设置为默认
        record = new EcqLevel();
        record.setEcqlId(ecqlId);
        record.setDefaultType(true);
        ecqLevelService.update(record);
    }


    

    @Transactional(rollbackFor = Exception.class)
    public void deal(EcqLevel record) {
        EcqLevel recordEcquLevel = new EcqLevel();
        recordEcquLevel.setName(record.getName());
        EcqLevel object = ecqLevelService.getObject(recordEcquLevel);
        if (object == null) {
            int sortId = 1;
            EcqLevel ecquLevel = ecqLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record.setSortId(sortId);
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            ecqLevelService.insert(record);
        } else {
            ecqLevelService.update(record);
        }
    }

    // getObjectPassEcqulId
    public EcqLevel getObjectPassEcqulId(Integer ecqulId) {
        EcqLevel record = new EcqLevel();
        record.setEcqlId(ecqulId);
        return ecqLevelService.getObject(record);
    }
}
