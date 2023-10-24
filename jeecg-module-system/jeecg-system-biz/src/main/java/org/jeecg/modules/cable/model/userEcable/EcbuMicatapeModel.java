package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeStartBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeListBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.model.systemEcable.EcbMicatapeModel;
import org.jeecg.modules.cable.service.systemEcable.EcbMicatapeService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuMicatapeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcbuMicatapeModel {
    @Resource
    EcbuMicatapeService ecbuMicatapeService;
    @Resource
    EcbMicatapeService ecbMicatapeService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbMicatapeModel ecbMicatapeModel;
    @Resource
    EcdCollectModel ecdCollectModel;

    //deal
    public void deal(EcbuMicatapeBo bo) {

        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbmId = bo.getEcbmId();
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        if (ecbuMicatape == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuMicatapeService.insert(record);
        } else {
            record.setEcbumId(ecbuMicatape.getEcbumId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuMicatapeService.update(record);
        }
        loadData();//加截txt
    }

    //start
    public String start(EcbuMicatapeStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbmId = bo.getEcbmId();
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        boolean startType;
        String msg = "";
        if (ecbuMicatape == null) {//插入数据
            EcbMicatape recordEcbMicatape = new EcbMicatape();
            recordEcbMicatape.setEcbmId(ecbmId);
            EcbMicatape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicatape);
            record.setEcbmId(ecbmId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbMicatape.getUnitPrice());
            record.setDensity(ecbMicatape.getDensity());
            record.setDescription("");
            ecbuMicatapeService.insert(record);

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
            ecbuMicatapeService.update(record);
        }

        loadData();//加截txt
        return msg;
    }

    //getList
    public List<EcbuMicatape> getList(EcbuMicatapeListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuMicatape record = new EcbuMicatape();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuMicatapeService.getList(record);
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuMicatape record) {
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        if (ecbuMicatape == null) {
            ecbuMicatapeService.insert(record);
        } else {
            ecbuMicatapeService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbmId
    public EcbuMicatape getObjectPassEcCompanyIdAndEcbmId(int ecCompanyId, int ecbmId) {
        EcbuMicatape record = new EcbuMicatape();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbmId(ecbmId);
        return ecbuMicatapeService.getObject(record);
    }

    //getObjectPassMicatapeStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuMicatape getObjectPassMicatapeStr(int ecuId) {
        EcbuMicatape object;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuMicatape record = new EcbuMicatape();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuMicatape> list = ecbuMicatapeService.getList(record);
        object = list.get(0);
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuMicatape record = new EcbuMicatape();
        record.setEcCompanyId(ecCompanyId);
        ecbuMicatapeService.delete(record);
    }

    //getObjectPassEcbumId
    public EcbuMicatape getObjectPassEcbumId(int ecbumId) {
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbumId(ecbumId);
        return ecbuMicatapeService.getObject(record);
    }


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
