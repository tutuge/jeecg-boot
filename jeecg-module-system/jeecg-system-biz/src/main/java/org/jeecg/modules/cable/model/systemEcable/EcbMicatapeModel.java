package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbMicatapeService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuMicatapeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbMicatapeModel {
    @Resource
    EcbMicatapeService ecbMicatapeService;
    @Resource
    EcbuMicatapeService ecbuMicatapeService;
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
        EcbMicatape record = new EcbMicatape();
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
        List<EcbMicatape> list = ecbMicatapeService.getList(record);
        long count = ecbMicatapeService.getCount();
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
        int ecbmId = Integer.parseInt(request.getParameter("ecbmId"));
        EcbMicatape recordEcbMicatape = new EcbMicatape();
        recordEcbMicatape.setEcbmId(ecbmId);
        EcbMicatape ecbMicatape = ecbMicatapeService.getObject(recordEcbMicatape);
        EcbuMicatape record = new EcbuMicatape();
        record.setEcbmId(ecbmId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuMicatape ecbuMicatape = ecbuMicatapeService.getObject(record);
        if (ecbuMicatape != null) {
            ecbMicatape.setEcbuMicatape(ecbuMicatape);
        }
        map.put("ecbMicatape", ecbMicatape);
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
