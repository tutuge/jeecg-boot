package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInsulationService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcbInsulationModel {
    @Resource
    EcbInsulationService ecbInsulationService;
    @Resource
    EcbuInsulationService ecbuInsulationService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbInsulation record = new EcbInsulation();
        if (request.getParameter("startType") != null) {
            boolean startType = true;
            if (!"0".equals(request.getParameter("startType"))) {
                if ("2".equals(request.getParameter("startType"))) {
                    startType = false;
                }
                record.setStartType(startType);
            }
        }
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbInsulation> list = ecbInsulationService.getList(record);
        long count = ecbInsulationService.getCount();
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取列表
        code = "200";
        msg = "正常获取列表";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecbiId = Integer.parseInt(request.getParameter("ecbiId"));
        EcbInsulation recordEcbInsulation = new EcbInsulation();
        recordEcbInsulation.setEcbiId(ecbiId);
        EcbInsulation ecbInsulation = ecbInsulationService.getObject(recordEcbInsulation);
        EcbuInsulation record = new EcbuInsulation();
        record.setEcbiId(ecbiId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInsulation ecbuInsulation = ecbuInsulationService.getObject(record);
        if (ecbuInsulation != null) {
            ecbInsulation.setEcbuInsulation(ecbuInsulation);
        }
        map.put("ecbInsulation", ecbInsulation);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //load 加载用户数据为txt文档
    public void loadData(HttpServletRequest request) {
        int ecCompanyId = 0;
        if (request.getParameter("ecuId") != null) {
            int ecuId = Integer.parseInt(request.getParameter("ecuId"));
            EcUser recordEcUser = new EcUser();
            recordEcUser.setEcuId(ecuId);
            EcUser ecUser = ecUserService.getObject(recordEcUser);
            ecCompanyId = ecUser.getEcCompanyId();
        } else if (request.getParameter("ecCompanyId") != null) {
            ecCompanyId = Integer.parseInt(request.getParameter("ecCompanyId"));
        }
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        //log.info(CommonFunction.getGson().toJson(record));
        List<EcbInsulation> list = ecbInsulationService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 5, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbInsulation> getListStart() {
        EcbInsulation record = new EcbInsulation();
        record.setStartType(true);
        return ecbInsulationService.getListStart(record);
    }

    //getObjectPassAbbreviation
    public EcbInsulation getObjectPassAbbreviation(String abbreviation) {
        EcbInsulation record = new EcbInsulation();
        record.setAbbreviation(abbreviation);
        return ecbInsulationService.getObject(record);

    }
}
