package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorStartBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.vo.ConductorVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbConductorService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcbConductorModel {
    @Resource
    EcbConductorService ecbConductorService;
    @Resource
    EcbuConductorService ecbuConductorService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    public ConductorVo getListAndCount(EcbConductorBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbConductor record = new EcbConductor();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbConductor> list = ecbConductorService.getList(record);
        long count = ecbConductorService.getCount();
        return new ConductorVo(list, count, record);
    }

    //getObject
    public EcbConductor getObject(EcbConductorStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbConductor recordEcbConductor = new EcbConductor();
        recordEcbConductor.setEcbcId(bo.getEcbcId());
        EcbConductor ecbConductor = ecbConductorService.getObject(recordEcbConductor);

        EcbuConductor record = new EcbuConductor();
        record.setEcbcId(bo.getEcbcId());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuConductor ecbuConductor = ecbuConductorService.getObject(record);
        if (ecbuConductor != null) {
            ecbConductor.setEcbuConductor(ecbuConductor);
        }

        return ecbConductor;

    }

    //load 加载用户数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbConductor record = new EcbConductor();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        log.info(CommonFunction.getGson().toJson(record));
        List<EcbConductor> list = ecbConductorService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 3, txtList);
    }

    /***===获取数据模型===***/
    //获取启用列表
    public List<EcbConductor> getListStart() {
        EcbConductor record = new EcbConductor();
        record.setStartType(true);
        return ecbConductorService.getListStart(record);
    }
}
