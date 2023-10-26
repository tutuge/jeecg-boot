package org.jeecg.modules.cable.model.userCommon;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitBo;
import org.jeecg.modules.cable.controller.userCommon.unit.bo.EcbuUnitInsertBo;
import org.jeecg.modules.cable.controller.userCommon.unit.vo.LengthUnitVo;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcbulUnitModel {
    @Resource
    EcbulUnitService ecbulUnitService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public LengthUnitVo getListAndCount(EcbuUnitBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbulUnit record = new EcbulUnit();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbulUnit> list = ecbulUnitService.getList(record);
        Long count = ecbulUnitService.getCount(record);
        return new LengthUnitVo(list, count);
    }

    //getObject
    public EcbulUnit getObject(EcbuUnitBo bo) {
        EcbulUnit record = new EcbulUnit();
        int ecbuluId = bo.getEcbuluId();
        record.setEcbuluId(ecbuluId);
        return ecbulUnitService.getObject(record);
    }

    //deal
    public String deal(EcbuUnitInsertBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbuluId = bo.getEcbuluId();
        String lengthName = bo.getLengthName();
        int meterNumber = bo.getMeterNumber();
        String description = bo.getDescription();

        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setLengthName(lengthName);
        EcbulUnit ecbulUnit = ecbulUnitService.getObjectPassLengthName(record);
        String msg = "";
        if (ecbulUnit != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ObjectUtil.isNull(ecbuluId)) {//插入
                int sortId = 1;
                ecbulUnit = ecbulUnitService.getLatestObject(record);
                if (ecbulUnit != null) {
                    sortId = ecbulUnit.getSortId() + 1;
                }
                record = new EcbulUnit();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setLengthName(lengthName);
                record.setMeterNumber(meterNumber);
                record.setDescription(description);
                ecbulUnitService.insert(record);

                msg = "正常插入数据";
            } else {//更新
                record = new EcbulUnit();
                record.setEcbuluId(ecbuluId);
                record.setLengthName(lengthName);
                record.setMeterNumber(meterNumber);
                record.setDescription(description);
                ecbulUnitService.update(record);

                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //sort
    public void sort(EcbuUnitBo bo) {
        int ecbuluId = bo.getEcbuluId();
        int sortId = bo.getSortId();
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        record.setSortId(sortId);
        ecbulUnitService.update(record);
    }

    //delete
    public void delete(EcbuUnitBo bo) {

        int ecbuluId = bo.getEcbuluId();
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        EcbulUnit ecbulUnit = ecbulUnitService.getObject(record);
        int sortId = ecbulUnit.getSortId();
        record = new EcbulUnit();
        record.setSortId(sortId);
        record.setEcbuluId(ecbulUnit.getEcbuluId());
        List<EcbulUnit> list = ecbulUnitService.getListGreaterThanSortId(record);
        int ecbulu_id;
        for (EcbulUnit ecbud_price : list) {
            ecbulu_id = ecbud_price.getEcbuluId();
            sortId = ecbud_price.getSortId() - 1;
            record.setEcbuluId(ecbulu_id);
            record.setSortId(sortId);
            ecbulUnitService.update(record);
        }
        record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        ecbulUnitService.delete(record);
    }

    //start
    public String start(EcbuUnitBo bo) {

        int ecbuluId = bo.getEcbuluId();
        EcbulUnit record = new EcbulUnit();
        record.setEcbuluId(ecbuluId);
        EcbulUnit ecbulUnit = ecbulUnitService.getObject(record);
        String msg = "";
        boolean startType = ecbulUnit.getStartType();
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcbulUnit();
        record.setEcbuluId(ecbulUnit.getEcbuluId());
        record.setStartType(startType);
        ecbulUnitService.update(record);
        return msg;
    }

    //load 加载用户包带数据为txt文档
    public void loadData(HttpServletRequest request) {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
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
    //insert
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

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbulUnit record = new EcbulUnit();
        record.setEcCompanyId(ecCompanyId);
        ecbulUnitService.delete(record);
    }

    //getObjectPassEcCompanyIdAndLengthName
    public EcbulUnit getObjectPassEcCompanyIdAndLengthName(int ecCompanyId, String lengthName) {
        EcbulUnit record = new EcbulUnit();
        record.setEcCompanyId(ecCompanyId);
        record.setLengthName(lengthName);
        return ecbulUnitService.getObject(record);
    }
}
