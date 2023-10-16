package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userCommon.EcduciPositionService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class EcduciPositionModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcduciPositionService ecduciPositionService;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecduciId = Integer.parseInt(request.getParameter("ecduciId"));
            String pX = request.getParameter("pX");
            String pY = request.getParameter("pY");
            EcduciPosition ecduciPosition = getObjectPassEcduciId(ecduciId);
            EcduciPosition record = new EcduciPosition();
            record.setEcduciId(ecduciId);
            record.setPX(pX);
            record.setPY(pY);
            record.setEffectTime(System.currentTimeMillis());
            if (ecduciPosition == null) {//新增
                BigDecimal imagePercent = new BigDecimal("1");
                if (request.getParameter("imagePercent") != null) {
                    imagePercent = new BigDecimal(request.getParameter("imagePercent"));
                }
                record.setImagePercent(imagePercent);
                ecduciPositionService.insert(record);
                status = 3;//正常新增数据
                code = "200";
                msg = "正常新增数据";
            } else {
                if (request.getParameter("imagePercent") != null) {
                    BigDecimal imagePercent = new BigDecimal(request.getParameter("imagePercent"));
                    record.setImagePercent(imagePercent);
                }
                ecduciPositionService.update(record);
                status = 4;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecduciId = Integer.parseInt(request.getParameter("ecduciId"));
            EcduciPosition ecduciPosition = getObjectPassEcduciId(ecduciId);
            map.put("object", ecduciPosition);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    /***===数据模型===***/
    //getObjectPassEcduciId
    public EcduciPosition getObjectPassEcduciId(int ecduciId) {
        EcduciPosition record = new EcduciPosition();
        record.setEcduciId(ecduciId);
        return ecduciPositionService.getObject(record);
    }

    //deletePassEcduciId
    public void deletePassEcduciId(int ecduciId) {
        EcduciPosition record = new EcduciPosition();
        record.setEcduciId(ecduciId);
        ecduciPositionService.delete(record);
    }
}
