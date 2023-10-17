package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingStartBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.vo.InfillingVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInfillingService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcbInfillingModel {
    @Resource
    EcbInfillingService ecbInfillingService;
    @Resource
    EcbuInfillingService ecbuInfillingService;
    @Resource
    EcdCollectModel ecdCollectModel;

    public InfillingVo getListAndCount(EcbInfillingBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbInfilling record = new EcbInfilling();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbInfilling> list = ecbInfillingService.getList(record);
        long count = ecbInfillingService.getCount();
        return new InfillingVo(list, count);
    }

    //getObject
    public EcbInfilling getObject(EcbInfillingStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbInfilling recordEcbInfilling = new EcbInfilling();
        Integer ecbinId = bo.getEcbinId();
        recordEcbInfilling.setEcbinId(ecbinId);
        EcbInfilling ecbInfilling = ecbInfillingService.getObject(recordEcbInfilling);
        EcbuInfilling record = new EcbuInfilling();
        record.setEcbinId(ecbinId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(record);
        if (ecbuInfilling != null) {
            ecbInfilling.setEcbuInfilling(ecbuInfilling);
        }
        return ecbInfilling;
    }

    //load 加载用户数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbInfilling record = new EcbInfilling();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        log.info(CommonFunction.getGson().toJson(record));
        List<EcbInfilling> list = ecbInfillingService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 6, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbInfilling> getListStart() {
        EcbInfilling record = new EcbInfilling();
        record.setStartType(true);
        return ecbInfillingService.getListStart(record);
    }
}
