package org.jeecg.modules.cable.model.systemCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleInsertBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.bo.EcbAxleSortBo;
import org.jeecg.modules.cable.controller.systemCommon.axle.vo.AxleVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;
import org.jeecg.modules.cable.service.systemCommon.EcbAxleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbAxleModel {
    @Resource
    EcbAxleService ecbAxleService;

    // getListAndCount
    public AxleVo getListAndCount(EcbAxleBo bo) {
        // 获取当前用户id
        EcbAxle record = new EcbAxle();
        record.setStartType(bo.getStartType());
        List<EcbAxle> list = ecbAxleService.getList(record);
        long count = ecbAxleService.getCount(record);
        return new AxleVo(list, count);
    }

    // getObject
    public EcbAxle getObject(EcbAxleBaseBo bo) {
        EcbAxle record = new EcbAxle();
        record.setEcbaId(bo.getEcbaId());
        return ecbAxleService.getObject(record);
    }

    // deal 提交
    public String deal(EcbAxleInsertBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbuaId = bo.getEcbaId();
        String axleName = bo.getAxleName();
        BigDecimal axleHeight = bo.getAxleHeight();
        BigDecimal circleDiameter = bo.getCircleDiameter();
        BigDecimal axleWidth = bo.getAxleWidth();
        BigDecimal axleDeep = bo.getAxleDeep();
        BigDecimal axleWeight = bo.getAxleWeight();
        BigDecimal axlePrice = bo.getAxlePrice();
        String description = bo.getDescription();


        EcbAxle record = new EcbAxle();
        record.setEcbaId(ecbuaId);
        record.setAxleName(axleName);
        EcbAxle EcbAxle = ecbAxleService.getObjectPassAxleName(record);
        String msg = "";
        if (EcbAxle != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ObjectUtil.isNull(ecbuaId)) {// 插入
                Integer sortId = 1;
                EcbAxle = ecbAxleService.getLatestObject(record);
                if (EcbAxle != null) {
                    sortId = EcbAxle.getSortId() + 1;
                }
                record = new EcbAxle();
                record.setStartType(true);
                record.setSortId(sortId);
                record.setAxleName(axleName);
                record.setAxleHeight(axleHeight);
                record.setCircleDiameter(circleDiameter);
                record.setAxleWidth(axleWidth);
                record.setAxleDeep(axleDeep);
                record.setAxleWeight(axleWeight);
                record.setAxlePrice(axlePrice);
                record.setDescription(description);
                ecbAxleService.insert(record);
                msg = "正常插入数据";
            } else {// 更新
                record.setEcbaId(ecbuaId);
                record.setAxleName(axleName);
                record.setAxleHeight(axleHeight);
                record.setCircleDiameter(circleDiameter);
                record.setAxleWidth(axleWidth);
                record.setAxleDeep(axleDeep);
                record.setAxleWeight(axleWeight);
                record.setAxlePrice(axlePrice);
                record.setDescription(description);
                ecbAxleService.updateByPrimaryKeySelective(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbAxleSortBo> bos) {
        for (EcbAxleSortBo bo : bos) {
            Integer sortId = bo.getSortId();
            Integer ecbuaId = bo.getEcbaId();
            EcbAxle record = new EcbAxle();
            record.setEcbaId(ecbuaId);
            record.setSortId(sortId);
            ecbAxleService.updateByPrimaryKeySelective(record);
        }
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbAxleBaseBo bo) {
        Integer ecbuaId = bo.getEcbaId();
        EcbAxle record = new EcbAxle();
        record.setEcbaId(ecbuaId);
        EcbAxle EcbAxle = ecbAxleService.getObject(record);
        Integer sortId = EcbAxle.getSortId();
        record = new EcbAxle();
        record.setSortId(sortId);
        List<EcbAxle> list = ecbAxleService.getListGreaterThanSortId(record);
        Integer ecbua_id;
        for (EcbAxle ecbud_money : list) {
            ecbua_id = ecbud_money.getEcbaId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbaId(ecbua_id);
            record.setSortId(sortId);
            ecbAxleService.updateByPrimaryKeySelective(record);
        }
        ecbAxleService.deleteByPrimaryKey(ecbuaId);
    }

    // start
    public String start(EcbAxleBaseBo bo) {
        Integer ecbuaId = bo.getEcbaId();

        EcbAxle record = new EcbAxle();
        record.setEcbaId(ecbuaId);
        EcbAxle EcbAxle = ecbAxleService.getObject(record);
        Boolean startType = EcbAxle.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbAxle();
        record.setEcbaId(EcbAxle.getEcbaId());
        record.setStartType(startType);
        ecbAxleService.updateByPrimaryKeySelective(record);
        return msg;
    }
}
