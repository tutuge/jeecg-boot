package org.jeecg.modules.cable.model.userEcable;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingStartBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.vo.InfillingVo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingBo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingListBo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.model.systemEcable.EcbInfillingModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInfillingService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EcbuInfillingModel {
    @Resource
    EcbuInfillingService ecbuInfillingService;
    @Resource
    EcbInfillingService ecbInfillingService;
    @Resource
    EcUserService ecUserService;

    @Resource
    EcdCollectModel ecdCollectModel;

    //deal
    public void deal(EcbuInfillingBo bo) {
        Integer ecbinId = bo.getEcbinId();
        BigDecimal unitPrice = bo.getUnitPrice();
        BigDecimal density = bo.getDensity();
        String description = bo.getDescription();

        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        EcbuInfilling record = new EcbuInfilling();
        record.setEcbinId(ecbinId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(record);
        if (ecbuInfilling == null) {//插入
            record.setStartType(false);
            record.setName("");
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInfillingService.insert(record);
        } else {
            record.setEcbuiId(ecbuInfilling.getEcbuiId());
            record.setUnitPrice(unitPrice);
            record.setDensity(density);
            record.setDescription(description);
            ecbuInfillingService.update(record);

        }
        loadData();//txt文档
    }

    //start
    public String start(EcbuInfillingStartBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecbinId = bo.getEcbinId();
        EcbuInfilling record = new EcbuInfilling();
        record.setEcbinId(ecbinId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(record);
        boolean startType;
        String msg = "";
        if (ecbuInfilling == null) {//插入数据
            EcbInfilling recordEcbInfilling = new EcbInfilling();
            recordEcbInfilling.setEcbinId(ecbinId);
            EcbInfilling ecbInfilling = ecbInfillingService.getObject(recordEcbInfilling);
            record.setEcbinId(ecbinId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setName("");
            record.setUnitPrice(ecbInfilling.getUnitPrice());
            record.setDensity(ecbInfilling.getDensity());
            record.setDescription("");
            ecbuInfillingService.insert(record);
            msg = "数据启用成功";
        } else {
            startType = ecbuInfilling.getStartType();
            if (!startType) {
                startType = true;
                msg = "数据启用成功";
            } else {
                startType = false;
                msg = "数据禁用成功";
            }
            record.setEcbuiId(ecbuInfilling.getEcbuiId());
            record.setStartType(startType);
            //System.out.println(CommonFunction.getGson().toJson(record));
            ecbuInfillingService.update(record);
        }
        loadData();//txt文档
        return msg;
    }

    //getList
    public List<EcbuInfilling> getList(EcbuInfillingListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuInfilling record = new EcbuInfilling();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setStartType(bo.getStartType());
        return ecbuInfillingService.getList(record);
    }


    /***===数据模型===***/
    //deal
    public void deal(EcbuInfilling record) {
        EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(record);
        if (ecbuInfilling == null) {
            ecbuInfillingService.insert(record);
        } else {
            ecbuInfillingService.update(record);
        }
    }

    //getObjectPassEcCompanyIdAndEcbinId
    public EcbuInfilling getObjectPassEcCompanyIdAndEcbinId(Integer ecCompanyId, Integer ecbinId) {
        EcbuInfilling record = new EcbuInfilling();
        record.setEcCompanyId(ecCompanyId);
        record.setEcbinId(ecbinId);
        return ecbuInfillingService.getObject(record);
    }

    //getObjectPassInfillingStr 通过屏蔽类型类型获取屏蔽 为计算成本提供数据
    public EcbuInfilling getObjectPassInfillingStr(Integer ecuId, String objectStr) {
        EcbuInfilling object = null;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbuInfilling record = new EcbuInfilling();
        record.setStartType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuInfilling> list = ecbuInfillingService.getList(record);
        for (EcbuInfilling ecbu_infilling : list) {
            Integer ecbinId = ecbu_infilling.getEcbinId();
            EcbInfilling recordEcbInfilling = new EcbInfilling();
            recordEcbInfilling.setEcbinId(ecbinId);
            EcbInfilling infilling = ecbInfillingService.getObject(recordEcbInfilling);
            if (infilling.getAbbreviation().equals(objectStr)) {
                object = ecbu_infilling;
            }
        }
        return object;
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(Integer ecCompanyId) {
        EcbuInfilling record = new EcbuInfilling();
        record.setEcCompanyId(ecCompanyId);
        ecbuInfillingService.delete(record);
    }

    //getObjectEcbuinId
    public EcbuInfilling getObjectAndEcbuinId(Integer ecbuinId) {
        EcbuInfilling record = new EcbuInfilling();
        record.setEcbuiId(ecbuinId);
        return ecbuInfillingService.getObject(record);
    }


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
        Integer ecCompanyId = 0;
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
