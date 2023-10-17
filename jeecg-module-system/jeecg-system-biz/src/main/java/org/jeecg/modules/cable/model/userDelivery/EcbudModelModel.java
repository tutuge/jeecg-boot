package org.jeecg.modules.cable.model.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EcbudModelModel {
    @Resource
    EcbudModelService ecbudModelService;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
        int startWeight1 = 0;
        if (!"".equals(request.getParameter("startWeight1"))) {
            startWeight1 = Integer.parseInt(request.getParameter("startWeight1"));
        }
        int endWeight1 = 0;
        if (!"".equals(request.getParameter("endWeight1"))) {
            endWeight1 = Integer.parseInt(request.getParameter("endWeight1"));
        }
        int startWeight2 = 0;
        if (!"".equals(request.getParameter("startWeight2"))) {
            startWeight2 = Integer.parseInt(request.getParameter("startWeight2"));
        }
        int endWeight2 = 0;
        if (!"".equals(request.getParameter("endWeight2"))) {
            endWeight2 = Integer.parseInt(request.getParameter("endWeight2"));
        }
        int startWeight3 = 0;
        if (!"".equals(request.getParameter("startWeight3"))) {
            startWeight3 = Integer.parseInt(request.getParameter("startWeight3"));
        }
        int endWeight3 = 0;
        if (!"".equals(request.getParameter("endWeight3"))) {
            endWeight3 = Integer.parseInt(request.getParameter("endWeight3"));
        }
        //System.out.println("h16");
        int startWeight4 = 0;
        if (!"".equals(request.getParameter("startWeight4"))) {
            startWeight4 = Integer.parseInt(request.getParameter("startWeight4"));
        }
        int endWeight4 = 0;
        if (!"".equals(request.getParameter("endWeight4"))) {
            endWeight4 = Integer.parseInt(request.getParameter("endWeight4"));
        }
        int startWeight5 = 0;
        if (!"".equals(request.getParameter("startWeight5"))) {
            startWeight5 = Integer.parseInt(request.getParameter("startWeight5"));
        }
        int endWeight5 = 0;
        if (!"".equals(request.getParameter("endWeight5"))) {
            endWeight5 = Integer.parseInt(request.getParameter("endWeight5"));
        }
        EcbudModel record = new EcbudModel();
        record.setEcbudId(ecbudId);
        EcbudModel ecbudModel = ecbudModelService.getObject(record);
        record.setStartWeight1(startWeight1);
        record.setEndWeight1(endWeight1);
        record.setStartWeight2(startWeight2);
        record.setEndWeight2(endWeight2);
        record.setStartWeight3(startWeight3);
        record.setEndWeight3(endWeight3);
        record.setStartWeight4(startWeight4);
        record.setEndWeight4(endWeight4);
        record.setStartWeight5(startWeight5);
        record.setEndWeight5(endWeight5);
        if (ecbudModel == null) {
            ecbudModelService.insert(record);
            status = 3;//正常插入数据
            code = "200";
            msg = "正常插入数据";
        } else {
            ecbudModelService.update(record);
            status = 4;//正常更新数据
            code = "201";
            msg = "正常更新数据";
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcbudModel record = new EcbudModel();
        if (request.getParameter("ecbudId") != null) {
            int ecbudId = Integer.parseInt(request.getParameter("ecbudId"));
            record.setEcbudId(ecbudId);
        }
        map.put("object", ecbudModelService.getObject(record));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===物流模型===***/
    //deal
    public void deal(EcbudModel record) {
        EcbudModel recordEcbudModel = new EcbudModel();
        recordEcbudModel.setEcbudId(record.getEcbudId());
        EcbudModel ecbudModel = ecbudModelService.getObject(record);
        if (ecbudModel != null) {
            ecbudModelService.update(record);
        } else {
            ecbudModelService.insert(record);
        }
    }

    //delete
    public void deletePassEcbudId(int ecbudId) {
        EcbudModel record = new EcbudModel();
        record.setEcbudId(ecbudId);
        ecbudModelService.delete(record);
    }
}
