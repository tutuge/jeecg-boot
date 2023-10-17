package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeStartBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbMicatapeService;
import org.jeecg.modules.cable.service.userEcable.EcbuMicatapeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcbMicatapeModel {
    @Resource
    EcbMicatapeService ecbMicatapeService;
    @Resource
    EcbuMicatapeService ecbuMicatapeService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public MicatapeVo getListAndCount(EcbMicatapeBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbMicatape record = new EcbMicatape();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbMicatape> list = ecbMicatapeService.getList(record);
        long count = ecbMicatapeService.getCount();

        return new MicatapeVo(list, count);
    }

    //getObject
    public EcbMicatape getObject(EcbMicatapeStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbMicatape recordEcbMicatape = new EcbMicatape();
        Integer ecbmId = bo.getEcbmId();
        recordEcbMicatape.setEcbmId(ecbmId);

        EcbMicatape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicatape);
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        if (ecbuMicatape != null) {
            ecbMicatape.setEcbuMicatape(ecbuMicatape);
        }
        return ecbMicatape;
    }

    //load 加载用户数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();

        EcbMicatape record = new EcbMicatape();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbMicatape> list = ecbMicatapeService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 4, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbMicatape> getListStart() {
        EcbMicatape record = new EcbMicatape();
        record.setStartType(true);
        return ecbMicatapeService.getListStart(record);
    }
}
