package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.vo.BagVo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagBo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagListBo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.model.systemEcable.EcbBagModel;
import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EcbuBagModel {
    @Resource
    EcbuBagService ecbuBagService;
    @Resource
    EcbBagService ecbBagService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbBagModel ecbBagModel;

    @Resource
    EcdCollectModel ecdCollectModel;

    //deal
    public void deal(EcbuBagBo bo) {
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbbId = bo.getEcbbId();
        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuBagService.insert(record);
        } else {
            record.setEcbubId(ecbuBag.getEcbubId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuBagService.update(record);
        }
        loadData();//txt文档
    }

    //start
    public String start(EcbuBagStartBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbbId = bo.getEcbbId();
        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        boolean startType;
        String msg = "";
        if (ecbuBag == null) {//插入数据
            EcbBag recordEcbBag = new EcbBag();
            recordEcbBag.setEcbbId(ecbbId);
            EcbBag ecbBag = ecbBagService.getObject(recordEcbBag);
            record.setEcbbId(ecbbId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbBag.getUnitPrice());
            record.setDensity(ecbBag.getDensity());
            record.setDescription("");
            ecbuBagService.insert(record);

            msg = "数据启用成功";
        } else {
            startType = ecbuBag.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbubId(ecbuBag.getEcbubId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuBagService.update(record);
        }
        loadData();//txt文档
        return msg;
    }

    //getList
    public List<EcbuBag> getList(EcbuBagListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuBagService.getList(record);
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuBag record) {
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag == null) {
            ecbuBagService.insert(record);
        } else {
            ecbuBagService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbbId
    public EcbuBag getObjectPassEcCompanyIdAndEcbbId(int ecCompanyId, int ecbbId) {
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbbId(ecbbId);
        return ecbuBagService.getObject(record);
    }

    //getObjectPassBagStr 通过包带类型类型获取包带 为计算成本提供数据
    public EcbuBag getObjectPassBagStr(int ecuId, String objectStr) {
        EcbuBag object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuBag record = new EcbuBag();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuBag> list = ecbuBagService.getList(record);
        for (EcbuBag ecbu_bag : list) {
            int ecbbId = ecbu_bag.getEcbbId();
            EcbBag recordEcbBag = new EcbBag();
            recordEcbBag.setEcbbId(ecbbId);
            EcbBag bag = ecbBagService.getObject(recordEcbBag);
            if (bag.getAbbreviation().equals(objectStr)) {
                object = ecbu_bag;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuBag record = new EcbuBag();
        record.setEcCompanyId(ecCompanyId);
        ecbuBagService.delete(record);
    }

    //getObjectPassEcbubId
    public EcbuBag getObjectPassEcbubId(int ecbubId) {
        EcbuBag record = new EcbuBag();
        record.setEcbubId(ecbubId);
        return ecbuBagService.getObject(record);
    }


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
