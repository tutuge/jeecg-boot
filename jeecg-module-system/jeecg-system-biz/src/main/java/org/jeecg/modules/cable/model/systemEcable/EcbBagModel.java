package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuBagService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbBagModel {
    @Resource
    EcbBagService ecbBagService;
    @Resource
    EcbuBagService ecbuBagService;
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
        EcbBag record = new EcbBag();
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
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbBag> list = ecbBagService.getList(record);
        long count = ecbBagService.getCount();
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
        int ecbbId = Integer.parseInt(request.getParameter("ecbbId"));
        EcbBag recordEcbBag = new EcbBag();
        recordEcbBag.setEcbbId(ecbbId);
        EcbBag ecbBag = ecbBagService.getObject(recordEcbBag);
        EcbuBag record = new EcbuBag();
        record.setEcbbId(ecbbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuBag ecbuBag = ecbuBagService.getObject(record);
        if (ecbuBag != null) {
            ecbBag.setEcbuBag(ecbuBag);
        }
        map.put("ecbBag", ecbBag);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //load 加载用户包带数据为txt文档
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
