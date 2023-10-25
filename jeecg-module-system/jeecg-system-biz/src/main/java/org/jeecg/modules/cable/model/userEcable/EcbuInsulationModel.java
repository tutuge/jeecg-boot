package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationStartBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
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


    //deal
    public String deal(EcbuInsulationBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        EcbuInsulation record = new EcbuInsulation();
        record.setEcbiId(bo.getEcbiId());
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        String msg = "";
        if (ecbuInsulation == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.insert(record);
            msg = "插入数据";
        } else {
            record.setEcbuiId(ecbuInsulation.getEcbuiId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInsulationService.update(record);
            msg = "更新数据";
        }
        loadData();//加截txt
        return msg;
    }

    //start
    public String start(EcbuInsulationStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuInsulation record = new EcbuInsulation();
        Integer ecbiId = bo.getEcbiId();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        boolean startType;
        String msg = "";
        if (ecbuInsulation == null) {//插入数据
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation ecbInsulation = ecbInsulationService.getObject(recordEcbInsulation);
            record.setEcbiId(ecbiId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
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
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuInsulationService.update(record);
        }
        loadData();//加截txt
        return msg;
    }

    //getList
    public List<EcbuInsulation> getList(EcbuInsulationListBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuInsulationService.getList(record);

    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuInsulation record) {
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation == null) {
            ecbuInsulationService.insert(record);
        } else {
            ecbuInsulationService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbiId
    public EcbuInsulation getObjectPassEcCompanyIdAndEcbiId(int ecCompanyId, int ecbiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbiId(ecbiId);
        return ecbuInsulationService.getObject(record);
    }

    //getInsulationPassInsulationStr 通过绝缘类型获取绝缘 为计算成本提供数据
    public EcbuInsulation getInsulationPassInsulationStr(int ecuId, String insulationStr) {
        EcbuInsulation object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        //log.info("list + " + CommonFunction.getGson().toJson(list));
        for (EcbuInsulation ecbuInsulation : list) {
            int ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getAbbreviation().equals(insulationStr)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcCompanyId(ecCompanyId);
        ecbuInsulationService.delete(record);
    }

    //getObjectPassEcbuiId
    public EcbuInsulation getObjectPassEcbuiId(int ecbuiId) {
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbuiId(ecbuiId);
        return ecbuInsulationService.getObject(record);
    }

    //getInsulationPassFullName 通过绝缘类型获取绝缘
    public EcbuInsulation getInsulationPassFullName(int ecuId, String fullName) {
        EcbuInsulation object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuInsulation record = new EcbuInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuInsulation> list = ecbuInsulationService.getList(record);
        //log.info("list + " + CommonFunction.getGson().toJson(list));
        for (EcbuInsulation ecbuInsulation : list) {
            int ecbiId = ecbuInsulation.getEcbiId();
            EcbInsulation recordEcbInsulation = new EcbInsulation();
            recordEcbInsulation.setEcbiId(ecbiId);
            EcbInsulation insulation = ecbInsulationService.getObject(recordEcbInsulation);
            if (insulation.getFullName().equals(fullName)) {
                object = ecbuInsulation;
            }
        }
        return object;
    }

    //getListAndCount
    public InsulationVo getListAndCount(EcbInsulationBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbInsulation record = new EcbInsulation();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbInsulation> list = ecbInsulationService.getList(record);
        long count = ecbInsulationService.getCount();

        return new InsulationVo(list, count);
    }

    //getObject
    public EcbInsulation getObject(EcbInsulationStartBo bo) {

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbInsulation recordEcbInsulation = new EcbInsulation();
        Integer ecbiId = bo.getEcbiId();
        recordEcbInsulation.setEcbiId(ecbiId);
        EcbInsulation ecbInsulation = ecbInsulationService.getObject(recordEcbInsulation);
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation != null) {
            ecbInsulation.setEcbuInsulation(ecbuInsulation);
        }
        return ecbInsulation;
    }

    //load 加载用户数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        //log.info(CommonFunction.getGson().toJson(record));
        List<EcbInsulation> list = ecbInsulationService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 5, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbInsulation> getListStart() {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        return ecbInsulationService.getListStart(record);
    }

    //getObjectPassAbbreviation
    public EcbInsulation getObjectPassAbbreviation(String abbreviation) {
        EcbInsulation record = new EcbInsulation();
        record.setAbbreviation(abbreviation);
        return ecbInsulationService.getObject(record);
    }
}
