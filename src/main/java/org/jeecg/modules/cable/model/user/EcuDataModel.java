package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataBaseBo;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataDealBo;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataListBo;
import org.jeecg.modules.cable.controller.user.data.bo.EcuDataObjectBo;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.jeecg.modules.cable.service.user.EcuDataService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.jeecg.common.enums.UserTypeEnum.ADMIN;

@Service
@Slf4j
public class EcuDataModel {

    @Resource
    EcuDataService ecuDataService;


    public Map<String, Object> getList(EcuDataListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcuData record = new EcuData();
        if (!Objects.equals(sysUser.getUserType(), ADMIN.getUserType())) {
            record.setEcuId(ecuId);
        } else {
            record.setEcCompanyId(sysUser.getEcCompanyId());
        }
        record.setStartType(bo.getStartType());
        //Integer pageNumber = bo.getPageSize();
        //Integer startNumber = (bo.getPageNo() - 1) * pageNumber;
        //record.setStartNumber(startNumber);
        //record.setPageNumber(pageNumber);

        List<EcuData> list = ecuDataService.getList(record);
        //long count = ecuDataService.getCount(record);
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", 0L);
        return map;
    }


    public EcuData getObject(EcuDataObjectBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        EcuData record = new EcuData();
        record.setEcuId(ecuId);
        record.setEcudId(bo.getEcudId());
        record.setStartType(bo.getStartType());
        log.info("record + " + CommonFunction.getGson().toJson(record));
        return ecuDataService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcuDataDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecuId = sysUser.getUserId();
        //EcuData ecuData = getObjectPassEcuId(ecuId);
        EcuData record = new EcuData();
        Integer ecbusId = bo.getEcbusId();
        Integer ecusmId = bo.getEcusmId();
        Integer ecudId = bo.getEcudId();
        record.setEcuId(ecuId);
        record.setEcusmId(ecusmId);
        record.setEcbusId(ecbusId);
        String msg = "";
        if (ecudId == null) {// 插入
            record.setEcCompanyId(sysUser.getEcCompanyId());
            record.setEcuId(ecuId);
            record.setStartType(true);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecuDataService.insert(record);
            msg = "正常新增数据";
        } else {// 修改
            record.setEcudId(ecudId);
            ecuDataService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public String start(EcuDataBaseBo request) {
        Integer ecudId = request.getEcudId();
        EcuData ecuData = getObjectPassEcudId(ecudId);
        Boolean startType = ecuData.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EcuData record = new EcuData();
        record.setEcudId(ecudId);
        record.setStartType(startType);
        ecuDataService.update(record);
        return msg;
    }


    // getObjectPassEcuId
    public EcuData getObjectPassEcuId(Integer ecuId) {
        EcuData record = new EcuData();
        record.setEcuId(ecuId);
        return ecuDataService.getObject(record);
    }

    // getObjectPassEcudId
    public EcuData getObjectPassEcudId(Integer ecudId) {
        EcuData record = new EcuData();
        record.setEcudId(ecudId);
        return ecuDataService.getObject(record);
    }
}
