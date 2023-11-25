package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationListBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcbuInsulationModel {
    @Resource
    EcbuInsulationService ecbuInsulationService;
    @Resource
    EcbInsulationService ecbInsulationService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbuInsulationBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        EcbuInsulation record = new EcbuInsulation();
        Integer ecbuiId = bo.getEcbuiId();
        String msg = "";
        if (ecbuiId == null) {// 插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.insert(record);
            msg = "插入数据";
        } else {
            record.setEcbuiId(ecbuiId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.update(record);
            msg = "更新数据";
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        loadData(sysUser.getEcCompanyId());// 加截txt
        return msg;
    }


    public String start(EcbuInsulationStartBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        EcbuInsulation record = new EcbuInsulation();
        Integer ecbiId = bo.getEcbiId();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(sysUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuInsulation == null) {// 插入数据
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation ecbInsulation = ecbInsulationService.getObject(recordEcbInsulation);
            record.setEcbiId(ecbiId);
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbInsulation.getUnitPrice());
            record.setDensity(ecbInsulation.getDensity());
            record.setDescription("");
            ecbuInsulationService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuInsulation.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbuiId(ecbuInsulation.getEcbuiId());
            record.setStartType(startType);
            // System.out.println(CommonFunction.getGson().toJson(record));
            ecbuInsulationService.update(record);
        }
        loadData(sysUser.getEcCompanyId());// 加截txt
        return msg;
    }


    public List<EcbuInsulation> getList(EcbuInsulationListBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(sysUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuInsulationService.getList(record);

    }

    /***===数据模型===***/

    public void deal(EcbuInsulation record) {
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation == null) {
            ecbuInsulationService.insert(record);
        } else {
            ecbuInsulationService.update(record);
        }
    }

    // getObjectPassEcCompanyIdAndEcbiId
    public EcbuInsulation getObjectPassEcCompanyIdAndEcbiId(Integer ecCompanyId, Integer ecbiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbiId(ecbiId);
        return ecbuInsulationService.getObject(record);
    }

    // getInsulationPassInsulationStr 通过绝缘类型获取绝缘 为计算成本提供数据
    public EcbuInsulation getInsulationPassInsulationStr(Integer ecCompanyId, String insulationStr) {
        EcbuInsulation object = null;

        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        // log.info("list + " + CommonFunction.getGson().toJson(list));
        for (EcbuInsulation ecbuInsulation : list) {
            Integer ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getAbbreviation().equals(insulationStr)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }

    // deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        ecbuInsulationService.delete(record);
    }


    public EcbuInsulation getObjectPassEcbuiId(Integer ecbuiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbuiId(ecbuiId);
        return ecbuInsulationService.getObject(record);
    }

    // 通过绝缘类型获取绝缘
    public EcbuInsulation getInsulationPassFullName(Integer ecCompanyId, String fullName) {
        EcbuInsulation object = null;
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        // log.info("list + " + CommonFunction.getGson().toJson(list));
        for (EcbuInsulation ecbuInsulation : list) {
            Integer ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getFullName().equals(fullName)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }

    // load 加载用户数据为txt文档
    public void loadData(Integer ecCompanyId) {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcbInsulation> list = ecbInsulationService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 5, txtList);
    }

    /***===数据模型===***/

    public List<EcbInsulation> getListStart() {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        return ecbInsulationService.getListStart(record);
    }

    // getObjectPassAbbreviation
    public EcbInsulation getObjectPassAbbreviation(String abbreviation) {
        EcbInsulation record = new EcbInsulation();
        record.setAbbreviation(abbreviation);
        return ecbInsulationService.getObject(record);
    }
}
