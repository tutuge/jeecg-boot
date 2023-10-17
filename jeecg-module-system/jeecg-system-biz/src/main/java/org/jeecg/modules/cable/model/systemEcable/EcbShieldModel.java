package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldStartBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.vo.ShieldVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuShieldService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcbShieldModel {
    @Resource
    EcbShieldService ecbShieldService;
    @Resource
    EcbuShieldService ecbuShieldService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public ShieldVo getListAndCount(EcbShieldBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbShield record = new EcbShield();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbShield> list = ecbShieldService.getList(record);
        long count = ecbShieldService.getCount();
        return new ShieldVo(list, count);
    }

    //getObject
    public EcbShield getObject(EcbShieldStartBo bo) {

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbsId = bo.getEcbsId();
        EcbShield recordEcbShield = new EcbShield();
        recordEcbShield.setEcbsId(ecbsId);
        EcbShield ecbShield = ecbShieldService.getObject(recordEcbShield);
        EcbuShield record = new EcbuShield();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuShield ecbuShield = ecbuShieldService.getObject(record);
        if (ecbuShield != null) {
            ecbShield.setEcbuShield(ecbuShield);
        }
        return ecbShield;
    }

    //load 加载用户数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbShield record = new EcbShield();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbShield> list = ecbShieldService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 8, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbShield> getListStart() {
        EcbShield record = new EcbShield();
        record.setStartType(true);
        return ecbShieldService.getListStart(record);
    }
}
