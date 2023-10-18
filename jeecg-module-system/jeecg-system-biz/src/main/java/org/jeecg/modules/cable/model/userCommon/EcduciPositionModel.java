package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.service.userCommon.EcduciPositionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class EcduciPositionModel {
    @Resource
    EcduciPositionService ecduciPositionService;

    //deal
    public String deal(HttpServletRequest request) {

        int ecduciId = Integer.parseInt(request.getParameter("ecduciId"));
        String pX = request.getParameter("pX");
        String pY = request.getParameter("pY");
        EcduciPosition ecduciPosition = getObjectPassEcduciId(ecduciId);
        EcduciPosition record = new EcduciPosition();
        record.setEcduciId(ecduciId);
        record.setPX(pX);
        record.setPY(pY);
        record.setEffectTime(System.currentTimeMillis());
        String msg = "";
        if (ecduciPosition == null) {//新增
            BigDecimal imagePercent = new BigDecimal("1");
            if (request.getParameter("imagePercent") != null) {
                imagePercent = new BigDecimal(request.getParameter("imagePercent"));
            }
            record.setImagePercent(imagePercent);
            ecduciPositionService.insert(record);
            msg = "正常新增数据";
        } else {
            if (request.getParameter("imagePercent") != null) {
                BigDecimal imagePercent = new BigDecimal(request.getParameter("imagePercent"));
                record.setImagePercent(imagePercent);
            }
            ecduciPositionService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    //getObject
    public EcduciPosition getObject(HttpServletRequest request) {
        int ecduciId = Integer.parseInt(request.getParameter("ecduciId"));
        EcduciPosition ecduciPosition = getObjectPassEcduciId(ecduciId);
        return ecduciPosition;
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
