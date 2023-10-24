package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandStartBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcbSteelbandModel {
    @Resource
    EcbSteelbandService ecbSteelbandService;
    @Resource
    EcbuSteelbandService ecbuSteelbandService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public SteelbandVo getListAndCount(EcbSteelbandBo bo) {

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbSteelBand> list = ecbSteelbandService.getList(record);
        long count = ecbSteelbandService.getCount();
        return new SteelbandVo(list, count);
    }

    //getObject
    public EcbSteelBand getObject(EcbSteelbandStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbsbId = bo.getEcbsbId();
        EcbSteelBand recordEcbSteelBand = new EcbSteelBand();
        recordEcbSteelBand.setEcbsbId(ecbsbId);
        EcbSteelBand ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelBand);
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband != null) {
            ecbSteelband.setEcbuSteelband(ecbuSteelband);
        }
        return ecbSteelband;
    }

    //load 加载用户数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbSteelBand> list = ecbSteelbandService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 9, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbSteelBand> getListStart() {
        EcbSteelBand record = new EcbSteelBand();
        record.setStartType(true);
        return ecbSteelbandService.getListStart(record);
    }
}
