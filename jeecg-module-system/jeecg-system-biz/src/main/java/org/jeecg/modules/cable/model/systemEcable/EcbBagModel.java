package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.vo.BagVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcbBagModel {
    @Resource
    EcbBagService ecbBagService;
    @Resource
    EcbuBagService ecbuBagService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public BagVo getListAndCount(EcbBagBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbBag record = new EcbBag();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbBag> list = ecbBagService.getList(record);
        long count = ecbBagService.getCount();
        return new BagVo(list, count, record);
    }

    //getObject
    public EcbBag getObject(EcbBagBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbBag recordEcbBag = new EcbBag();
        Integer ecbbId = bo.getEcbbId();
        recordEcbBag.setEcbbId(ecbbId);
        EcbBag ecbBag = ecbBagService.getObject(recordEcbBag);

        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag != null) {
            ecbBag.setEcbuBag(ecbuBag);
        }
        return ecbBag;
    }

    //load 加载用户包带数据为txt文档
    public void loadData() {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();

        EcbBag record = new EcbBag();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbBag> list = ecbBagService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 7, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbBag> getListStart() {
        EcbBag record = new EcbBag();
        record.setStartType(true);
        return ecbBagService.getListStart(record);
    }
}
