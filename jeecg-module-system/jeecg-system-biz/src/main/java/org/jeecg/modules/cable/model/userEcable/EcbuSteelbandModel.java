package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandStartBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandListBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcbuSteelbandModel {
    @Resource
    EcbuSteelbandService ecbuSteelbandService;
    @Resource
    EcbSteelbandService ecbSteelbandService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;


    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbuSteelBandBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        Integer ecbusId = bo.getEcbusId();
        EcbuSteelband record = new EcbuSteelband();
        if (ecbusId == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.insert(record);
        } else {
            record.setEcbusId(ecbusId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuSteelbandService.update(record);
        }
        loadData();//txt文档
    }


    public String start(EcbuSteelBandStartBo bo) {
        Integer ecbsbId = bo.getEcbsbId();
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuSteelband == null) {//插入数据
            EcbSteelBand recordEcbSteelBand = new EcbSteelBand();
            recordEcbSteelBand.setEcbsbId(ecbsbId);
            EcbSteelBand ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelBand);
            record.setEcbsbId(ecbsbId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbSteelband.getUnitPrice());
            record.setDensity(ecbSteelband.getDensity());
            record.setDescription("");
            ecbuSteelbandService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuSteelband.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbusId(ecbuSteelband.getEcbusId());
            record.setStartType(startType);
            ecbuSteelbandService.update(record);
        }
        loadData();//txt文档
        return msg;
    }


    public List<EcbuSteelband> getList(EcbuSteelBandListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuSteelbandService.getList(record);
    }

    /***===数据模型===***/

    public void deal(EcbuSteelband record) {
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband == null) {
            ecbuSteelbandService.insert(record);
        } else {
            ecbuSteelbandService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbsbId
    public EcbuSteelband getObjectPassEcCompanyIdAndEcbsbId(Integer ecCompanyId, Integer ecbsbId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbsbId(ecbsbId);
        return ecbuSteelbandService.getObject(record);
    }

    //getObjectPassSteelbandStr 通过钢带类型类型获取钢带 为计算成本提供数据
    public EcbuSteelband getObjectPassSteelbandStr(Integer ecuId, String objectStr) {
        EcbuSteelband object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuSteelband record = new EcbuSteelband();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuSteelband> list = ecbuSteelbandService.getList(record);
        for (EcbuSteelband ecbuSteelband : list) {
            Integer ecbsbId = ecbuSteelband.getEcbsbId();
            EcbSteelBand recordEcbSteelBand = new EcbSteelBand();
            recordEcbSteelBand.setEcbsbId(ecbsbId);
            EcbSteelBand steelband = ecbSteelbandService.getObject(recordEcbSteelBand);
            if (steelband.getAbbreviation().equals(objectStr)) {
                object = ecbuSteelband;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcCompanyId(ecCompanyId);
        ecbuSteelbandService.delete(record);
    }

    //getObjectPassEcbusbId
    public EcbuSteelband getObjectPassEcbusbId(Integer ecbusbId) {
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbusId(ecbusbId);
        return ecbuSteelbandService.getObject(record);
    }

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
        Integer ecCompanyId = 0;
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
