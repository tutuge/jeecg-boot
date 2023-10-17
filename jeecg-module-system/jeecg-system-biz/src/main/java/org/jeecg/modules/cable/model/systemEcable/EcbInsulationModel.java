package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationStartBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcbInsulationModel {
    @Resource
    EcbInsulationService ecbInsulationService;
    @Resource
    EcbuInsulationService ecbuInsulationService;
    @Resource
    EcdCollectModel ecdCollectModel;

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
