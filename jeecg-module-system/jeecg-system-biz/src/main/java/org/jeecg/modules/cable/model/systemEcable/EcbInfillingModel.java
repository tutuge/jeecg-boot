package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.entity.user.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbInfillingService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
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
public class EcbInfillingModel {
    @Resource
    EcbInfillingService ecbInfillingService;
    @Resource
    EcbuInfillingService ecbuInfillingService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcbInfilling record = new EcbInfilling();
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
        List<EcbInfilling> list = ecbInfillingService.getList(record);
        long count = ecbInfillingService.getCount();
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
        int ecbinId = Integer.parseInt(request.getParameter("ecbinId"));
        EcbInfilling recordEcbInfilling = new EcbInfilling();
        recordEcbInfilling.setEcbinId(ecbinId);
        EcbInfilling ecbInfilling = ecbInfillingService.getObject(recordEcbInfilling);
        EcbuInfilling record = new EcbuInfilling();
        record.setEcbinId(ecbinId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuInfilling ecbuInfilling = ecbuInfillingService.getObject(record);
        if (ecbuInfilling != null) {
            ecbInfilling.setEcbuInfilling(ecbuInfilling);
        }
        map.put("ecbInfilling", ecbInfilling);
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
        } else {
            ecCompanyId = Integer.parseInt(request.getParameter("ecCompanyId"));
        }

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
