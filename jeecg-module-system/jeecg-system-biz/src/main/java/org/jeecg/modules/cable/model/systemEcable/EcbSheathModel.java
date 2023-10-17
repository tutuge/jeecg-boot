package org.jeecg.modules.cable.model.systemEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathStartBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbSheathModel {
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbSheathService ecbSheathService;
    @Resource
    EcbuSheathService ecbuSheathService;

    //getListAndCount
    public SheathVo getListAndCount(EcbSheathBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbSheath record = new EcbSheath();

        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbSheath> list = ecbSheathService.getList(record);
        long count = ecbSheathService.getCount();
        return new SheathVo(list, count);
    }

    //getObject
    public EcbSheath getObject(EcbSheathStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecbsId = bo.getEcbsId();
        EcbSheath recordEcbSheath = new EcbSheath();
        recordEcbSheath.setEcbsId(ecbsId);
        EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath != null) {
            ecbSheath.setEcbuSheath(ecbuSheath);
        }
        return ecbSheath;
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbSheath> getListStart() {
        EcbSheath record = new EcbSheath();
        record.setStartType(true);
        return ecbSheathService.getListStart(record);
    }

    // getListSilkName 获取丝型号名称 为报价页面提供数据
    public List<EcbSheath> getListSilkName(int ecuId) {
        List<EcbSheath> list;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        boolean startType = true;
        EcbSheath record = new EcbSheath();
        record.setStartType(startType);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        list = ecbSheathService.getList(record);
        for (int i = 0; i < list.size(); i++) {
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbsId(list.get(i).getEcbsId());
            recordEcbuSheath.setEcCompanyId(ecUser.getEcCompanyId());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            if (ecbuSheath != null) {
                if (list.get(i).getAbbreviation().contains("D2")) {
                    list.remove(i);
                    i--;
                } else if (list.get(i).getAbbreviation().contains("D1")) {
                    list.get(i).setAbbreviation("");
                }
            } else {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
