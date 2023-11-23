package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitBo;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitInsertBo;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitSortBo;
import org.jeecg.modules.cable.controller.userCommon.unit.vo.LengthUnitVo;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcbulUnitModel {
    @Resource
    EcbulUnitService ecbulUnitService;
    @Resource
    EcdCollectModel ecdCollectModel;


    public LengthUnitVo getListAndCount(EcbuUnitBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcbulUnit record = new EcbulUnit();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(sysUser.getEcCompanyId());
        List<EcbulUnit> list = ecbulUnitService.getList(record);
        Long count = ecbulUnitService.getCount(record);
        return new LengthUnitVo(list, count);
    }


    public EcbulUnit getObject(EcbuUnitBo bo) {
        return ecbulUnitService.getObjectById(bo.getEcbuluId());
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbuUnitInsertBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecbuluId = bo.getEcbuluId();
        String lengthName = bo.getLengthName();
        Integer meterNumber = bo.getMeterNumber();
        String description = bo.getDescription();

        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setLengthName(lengthName);
        EcbulUnit ecbulUnit = ecbulUnitService.getObjectPassLengthName(record);
        String msg = "";
        if (ecbulUnit != null) {
            throw new RuntimeException("名称已占用");
        }
        if (ObjectUtil.isNull(ecbuluId)) {// 插入
            Integer sortId = 1;
            ecbulUnit = ecbulUnitService.getLatestObject(record);
            if (ecbulUnit != null) {
                sortId = ecbulUnit.getSortId() + 1;
            }
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setLengthName(lengthName);
            record.setMeterNumber(meterNumber);
            record.setDescription(description);
            ecbulUnitService.insert(record);
            msg = "正常插入数据";
        } else {// 更新
            record.setEcbuluId(ecbuluId);
            record.setLengthName(lengthName);
            record.setMeterNumber(meterNumber);
            record.setDescription(description);
            ecbulUnitService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    @Transactional(rollbackFor = Exception.class)
    public void sort(List<EcbuUnitSortBo> bos) {
        for (EcbuUnitSortBo bo : bos) {
            Integer ecbuluId = bo.getEcbuluId();
            Integer sortId = bo.getSortId();
            EcbulUnit record = new EcbulUnit();
            record.setEcbuluId(ecbuluId);
            record.setSortId(sortId);
            ecbulUnitService.update(record);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcbuUnitBo bo) {
        Integer ecbuluId = bo.getEcbuluId();
        EcbulUnit ecbulUnit = ecbulUnitService.getObjectById(ecbuluId);
        Integer sortId = ecbulUnit.getSortId();
        ecbulUnitService.reduceSort(ecbuluId, sortId);
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        ecbulUnitService.delete(record);
    }


    public String start(EcbuUnitBo bo) {
        Integer ecbuluId = bo.getEcbuluId();
        EcbulUnit ecbulUnit = ecbulUnitService.getObjectById(ecbuluId);
        String msg = "";
        Boolean startType = ecbulUnit.getStartType();
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbulUnit.getEcbuluId());
        record.setStartType(startType);
        ecbulUnitService.update(record);
        return msg;
    }

    // load 加载用户包带数据为txt文档
    public void loadData(Integer ecCompanyId) {
        EcbulUnit record = new EcbulUnit();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbulUnit> list = ecbulUnitService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 10, txtList);
    }

    /***===数据模型===***/
    // insert
    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbulUnit record) {
        EcbulUnit recordEcbulUnit = new EcbulUnit();
        recordEcbulUnit.setEcCompanyId(record.getEcCompanyId());
        recordEcbulUnit.setLengthName(record.getLengthName());
        EcbulUnit ecbulUnit = ecbulUnitService.getObject(record);
        if (ecbulUnit != null) {
            ecbulUnitService.update(record);
        } else {
            ecbulUnitService.insert(record);
        }
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbulUnit record = new EcbulUnit();
        record.setEcCompanyId(ecCompanyId);
        ecbulUnitService.delete(record);
    }

    // getObjectPassEcCompanyIdAndLengthName
    public EcbulUnit getObjectPassEcCompanyIdAndLengthName(Integer ecCompanyId, String lengthName) {
        EcbulUnit record = new EcbulUnit();
        record.setEcCompanyId(ecCompanyId);
        record.setLengthName(lengthName);
        return ecbulUnitService.getObject(record);
    }
}
