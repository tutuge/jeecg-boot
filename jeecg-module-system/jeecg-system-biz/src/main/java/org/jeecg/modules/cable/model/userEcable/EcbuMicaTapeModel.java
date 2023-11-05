package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeStartBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicaTapeBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeListBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbMicaTapeService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuMicaTapeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcbuMicaTapeModel {
    @Resource
    EcbuMicaTapeService ecbuMicaTapeService;
    @Resource
    EcbMicaTapeService ecbMicatapeService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //deal
    public void deal(EcbuMicaTapeBo bo) {

        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        Integer ecbumId = bo.getEcbumId();
        EcbuMicaTape record = new EcbuMicaTape();
        if (ecbumId == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuMicaTapeService.insert(record);
        } else {
            record.setEcbumId(ecbumId);
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuMicaTapeService.update(record);
        }
        loadData();//加截txt
    }

    //start
    public String start(EcbuMicatapeStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbmId = bo.getEcbmId();
        EcbuMicaTape record = new EcbuMicaTape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicaTape ecbuMicatape = ecbuMicaTapeService.getObject(record);
        Boolean startType;
        String msg = "";
        if (ecbuMicatape == null) {//插入数据
            EcbMicaTape recordEcbMicaTape = new EcbMicaTape();
            recordEcbMicaTape.setEcbmId(ecbmId);
            EcbMicaTape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicaTape);
            record.setEcbmId(ecbmId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbMicatape.getUnitPrice());
            record.setDensity(ecbMicatape.getDensity());
            record.setDescription("");
            ecbuMicaTapeService.insert(record);

            msg = "数据启用成功";
        } else {
            startType = ecbuMicatape.getStartType();
            if (!startType) {
                startType = true;

                msg = "数据启用成功";
            } else {
                startType = false;

                msg = "数据禁用成功";
            }
            record.setEcbumId(ecbuMicatape.getEcbumId());
            record.setStartType(startType);
            ecbuMicaTapeService.update(record);
        }

        loadData();//加截txt
        return msg;
    }

    //getList
    public List<EcbuMicaTape> getList(EcbuMicatapeListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuMicaTape record = new EcbuMicaTape();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuMicaTapeService.getList(record);
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuMicaTape record) {
        EcbuMicaTape ecbuMicatape = ecbuMicaTapeService.getObject(record);
        if (ecbuMicatape == null) {
            ecbuMicaTapeService.insert(record);
        } else {
            ecbuMicaTapeService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbmId
    public EcbuMicaTape getObjectPassEcCompanyIdAndEcbmId(Integer ecCompanyId, Integer ecbmId) {
        EcbuMicaTape record = new EcbuMicaTape();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbmId(ecbmId);
        return ecbuMicaTapeService.getObject(record);
    }

    //getObjectPassMicatapeStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuMicaTape getObjectPassMicatapeStr(Integer ecuId) {
        EcbuMicaTape object;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuMicaTape record = new EcbuMicaTape();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuMicaTape> list = ecbuMicaTapeService.getList(record);
        object = list.get(0);
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuMicaTape record = new EcbuMicaTape();
        record.setEcCompanyId(ecCompanyId);
        ecbuMicaTapeService.delete(record);
    }

    //getObjectPassEcbumId
    public EcbuMicaTape getObjectPassEcbumId(Integer ecbumId) {
        EcbuMicaTape record = new EcbuMicaTape();
        record.setEcbumId(ecbumId);
        return ecbuMicaTapeService.getObject(record);
    }


    //getListAndCount
    public MicatapeVo getListAndCount(EcbMicatapeBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbMicaTape record = new EcbMicaTape();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbMicaTape> list = ecbMicatapeService.getList(record);
        long count = ecbMicatapeService.getCount();
        return new MicatapeVo(list, count);
    }

    //getObject
    public EcbMicaTape getObject(EcbMicatapeStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbMicaTape recordEcbMicaTape = new EcbMicaTape();
        Integer ecbmId = bo.getEcbmId();
        recordEcbMicaTape.setEcbmId(ecbmId);

        EcbMicaTape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicaTape);
        EcbuMicaTape record = new EcbuMicaTape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicaTape ecbuMicatape = ecbuMicaTapeService.getObject(record);
        if (ecbuMicatape != null) {
            ecbMicatape.setEcbuMicatape(ecbuMicatape);
        }
        return ecbMicatape;
    }

    //load 加载用户数据为txt文档
    public void loadData() {
        Integer ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();

        EcbMicaTape record = new EcbMicaTape();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbMicaTape> list = ecbMicatapeService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 4, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbMicaTape> getListStart() {
        EcbMicaTape record = new EcbMicaTape();
        record.setStartType(true);
        return ecbMicatapeService.getListStart(record);
    }
}
