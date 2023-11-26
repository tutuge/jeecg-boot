package org.jeecg.modules.cable.model.userQuality;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelBaseBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelDealBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelListBo;
import org.jeecg.modules.cable.controller.userQuality.level.bo.EcquLevelSortBo;
import org.jeecg.modules.cable.controller.userQuality.level.vo.LevelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.userEcable.EcuSilkService;
import org.jeecg.modules.cable.service.userQuality.EcquLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcquLevelModel {
    @Resource
    EcquLevelService ecquLevelService;
    @Resource
    EcdCollectModel ecdCollectModel;
    @Resource
    EcuSilkService ecuSilkService;


    public LevelVo getList(EcquLevelListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcquLevel record = new EcquLevel();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcquLevel> list = ecquLevelService.getList(record);
        long count = ecquLevelService.getCount(record);
        return new LevelVo(list, count);
    }


    public EcquLevel getObject(EcquLevelBaseBo bo) {
        EcquLevel record = new EcquLevel();
        Integer ecqulId = bo.getEcqulId();
        record.setEcqulId(ecqulId);
        EcquLevel object = ecquLevelService.getObject(record);
        EcuSilk silk = new EcuSilk();
        silk.setEcusId(object.getEcusId());
        EcuSilk ecuSilk = ecuSilkService.getObject(silk);
        object.setEcuSilk(ecuSilk);
        return object;
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcquLevelDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecqulId = bo.getEcqulId();
        Integer ecusId = bo.getEcusId();
        Integer ecbucId = bo.getEcbucId();
        String name = bo.getName();
        String description = bo.getDescription();

        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setName(name);
        String msg;
        if (ObjectUtil.isNull(ecqulId)) {// 插入
            int sortId = 1;
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record = new EcquLevel();
            record.setEcusId(ecusId);
            record.setEcbucId(ecbucId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPowerId(2);// 自有库
            record.setName(name);
            record.setDefaultType(false);
            record.setDescription(description);
            ecquLevelService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcqulId(ecqulId);
            record.setEcusId(ecusId);
            record.setEcbucId(ecbucId);
            record.setName(name);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecquLevelService.update(record);
            msg = "正常更新数据";
        }
        deal(sysUser.getEcCompanyId());
        return msg;
    }


    public void sort(List<EcquLevelSortBo> bos) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        for (EcquLevelSortBo bo : bos) {
            Integer ecqulId = bo.getEcqulId();
            Integer sortId = bo.getSortId();
            EcquLevel record = new EcquLevel();
            record.setEcqulId(ecqulId);
            record.setSortId(sortId);
            ecquLevelService.update(record);
            deal(sysUser.getEcCompanyId());// 加载load为集成数据
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(EcquLevelBaseBo bo) {

        Integer ecqulId = bo.getEcqulId();
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        Integer sortId = ecquLevel.getSortId();
        record = new EcquLevel();
        record.setSortId(sortId);
        record.setEcCompanyId(ecquLevel.getEcCompanyId());
        List<EcquLevel> list = ecquLevelService.getList(record);
        Integer ecqul_id;
        for (EcquLevel ecqu_level : list) {
            ecqul_id = ecqu_level.getEcqulId();
            sortId = ecqu_level.getSortId() - 1;
            record.setEcqulId(ecqul_id);
            record.setSortId(sortId);
            ecquLevelService.update(record);
        }
        ecquLevelService.delete(ecqulId);

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        deal(sysUser.getEcCompanyId());// 加载load为集成数据
    }


    public String start(EcquLevelBaseBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        Boolean startType = ecquLevel.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcquLevel();
        record.setEcqulId(ecquLevel.getEcqulId());
        record.setStartType(startType);
        ecquLevelService.update(record);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        deal(sysUser.getEcCompanyId());// 加载load为集成数据

        return msg;
    }


    public void defaultType(EcquLevelBaseBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcquLevel ecquLevel = getObjectPassEcqulId(ecqulId);
        //先将同型号类型的全部设为非默认
        EcquLevel record = new EcquLevel();
        record.setEcusId(ecquLevel.getEcusId());
        record.setDefaultType(false);
        ecquLevelService.update(record);
        //再将对应的主键的设置为默认
        record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setDefaultType(true);
        ecquLevelService.update(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcquLevel record) {
        EcquLevel recordEcquLevel = new EcquLevel();
        recordEcquLevel.setName(record.getName());
        recordEcquLevel.setEcCompanyId(record.getEcCompanyId());
        EcquLevel object = ecquLevelService.getObject(recordEcquLevel);
        if (object == null) {
            int sortId = 1;
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record.setSortId(sortId);
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            ecquLevelService.insert(record);
        } else {
            record.setEcqulId(object.getEcqulId());
            ecquLevelService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer ecCompanyId) {
        EcquLevel record = new EcquLevel();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcquLevel> list = ecquLevelService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 2, txtList);
    }

    // getObjectPassEcqulId
    public EcquLevel getObjectPassEcqulId(Integer ecqulId) {
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        return ecquLevelService.getObject(record);
    }

    // getObjectPassEcsIdAndDefaultType 根据丝系列获取默认的质量等级，如果没有默认等级，随机获取一个
    public EcquLevel getObjectPassEcsIdAndDefaultType(Integer ecCompanyId, Integer ecsId) {
        EcquLevel record = new EcquLevel();
        record.setEcCompanyId(ecCompanyId);
        record.setEcusId(ecsId);
        record.setDefaultType(true);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        if (ecquLevel == null) {
            record = new EcquLevel();
            record.setEcCompanyId(ecCompanyId);
            record.setEcusId(ecsId);
            ecquLevel = ecquLevelService.getObject(record);
        }
        return ecquLevel;
    }

    // getObjectPassEcCompanyIdAndName 根据公司ID和质量等级名称获取数据
    public EcquLevel getObjectPassEcCompanyIdAndName(Integer ecCompanyId, String name) {
        EcquLevel record = new EcquLevel();
        record.setEcCompanyId(ecCompanyId);
        record.setName(name);
        return ecquLevelService.getObject(record);
    }

}
