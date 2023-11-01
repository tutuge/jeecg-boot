package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleBaseBo;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleBo;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleInsertBo;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleSortBo;
import org.jeecg.modules.cable.controller.userCommon.axle.vo.EcbuAxleVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;
import org.jeecg.modules.cable.service.userCommon.EcbuAxleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcbuAxleModel {
    @Resource
    EcbuAxleService ecbuAxleService;

    // getListAndCount
    public EcbuAxleVo getListAndCount(EcbuAxleBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbuAxle record = new EcbuAxle();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuAxle> list = ecbuAxleService.getList(record);
        long count = ecbuAxleService.getCount(record);
        return new EcbuAxleVo(list, count);
    }

    // getObject
    public EcbuAxle getObject(EcbuAxleBaseBo bo) {
        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(bo.getEcbuaId());
        return ecbuAxleService.getObject(record);
    }

    // deal 提交
    public String deal(EcbuAxleInsertBo bo) {
        // 获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbuaId = bo.getEcbuaId();
        String axleName = bo.getAxleName();
        BigDecimal axleHeight = bo.getAxleHeight();
        BigDecimal circleDiameter = bo.getCircleDiameter();
        BigDecimal axleWidth = bo.getAxleWidth();
        BigDecimal axleDeep = bo.getAxleDeep();
        BigDecimal axleWeight = bo.getAxleWeight();
        BigDecimal axlePrice = bo.getAxlePrice();
        String description = bo.getDescription();


        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setAxleName(axleName);
        EcbuAxle ecbuAxle = ecbuAxleService.getObjectPassAxleName(record);
        String msg = "";
        if (ecbuAxle != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ObjectUtil.isNull(ecbuaId)) {// 插入
                Integer sortId = 1;
                ecbuAxle = ecbuAxleService.getLatestObject(record);
                if (ecbuAxle != null) {
                    sortId = ecbuAxle.getSortId() + 1;
                }
                record = new EcbuAxle();
                record.setEcCompanyId(ecUser.getEcCompanyId());
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
                ecbuAxleService.insert(record);
                msg = "正常插入数据";
            } else {// 更新
                record.setEcbuaId(ecbuaId);
                record.setAxleName(axleName);
                record.setAxleHeight(axleHeight);
                record.setCircleDiameter(circleDiameter);
                record.setAxleWidth(axleWidth);
                record.setAxleDeep(axleDeep);
                record.setAxleWeight(axleWeight);
                record.setAxlePrice(axlePrice);
                record.setDescription(description);
                ecbuAxleService.updateByPrimaryKeySelective(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }

    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbuAxleSortBo> bos) {
        for (EcbuAxleSortBo bo : bos) {
            Integer sortId = bo.getSortId();
            Integer ecbuaId = bo.getEcbuaId();
            EcbuAxle record = new EcbuAxle();
            record.setEcbuaId(ecbuaId);
            record.setSortId(sortId);
            ecbuAxleService.updateByPrimaryKeySelective(record);
        }
    }

    // delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuAxleBaseBo bo) {
        Integer ecbuaId = bo.getEcbuaId();
        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        EcbuAxle ecbuAxle = ecbuAxleService.getObject(record);
        Integer sortId = ecbuAxle.getSortId();
        record = new EcbuAxle();
        record.setSortId(sortId);
        record.setEcCompanyId(ecbuAxle.getEcCompanyId());
        List<EcbuAxle> list = ecbuAxleService.getListGreaterThanSortId(record);
        Integer ecbua_id;
        for (EcbuAxle ecbud_money : list) {
            ecbua_id = ecbud_money.getEcbuaId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbuaId(ecbua_id);
            record.setSortId(sortId);
            ecbuAxleService.updateByPrimaryKeySelective(record);
        }
        ecbuAxleService.deleteByPrimaryKey(ecbuaId);
    }

    // start
    public String start(EcbuAxleBaseBo bo) {
        Integer ecbuaId = bo.getEcbuaId();

        EcbuAxle record = new EcbuAxle();
        record.setEcbuaId(ecbuaId);
        EcbuAxle ecbuAxle = ecbuAxleService.getObject(record);
        Boolean startType = ecbuAxle.getStartType();
        String msg = "";
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbuAxle();
        record.setEcbuaId(ecbuAxle.getEcbuaId());
        record.setStartType(startType);
        ecbuAxleService.updateByPrimaryKeySelective(record);
        return msg;
    }
}
