package org.jeecg.modules.cable.model.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userOffer.EcuoCoreService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuoCoreModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuoCoreService ecuoCoreService;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

            int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
            EcuoCore record = new EcuoCore();
            record.setEcqulId(ecqulId);
            List<EcuoCore> list = ecuoCoreService.getList(record);
            map.put("list", list);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(int ecqulId, String areaStr) {
        String coreStr = "";
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        coreStr = fireArr[0];
        if (areaArr.length == 2) {
            String[] zeroArr = areaArr[1].split("\\*");
            coreStr += "+" + zeroArr[0];
        }
        EcuoCore ecuoCore = getObjectPassEcqulIdAndAreaStr(ecqulId, coreStr);
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        record.setCoreStr(coreStr);
        if (ecuoCore == null) {
            record = new EcuoCore();
            record.setEcqulId(ecqulId);
            ecuoCore = getObjectPassEcqulId(ecqulId);
            int sortId = 1;
            if (ecuoCore != null) {
                sortId = ecuoCore.getSortId() + 1;
            }
            record.setSortId(sortId);
            record.setCoreStr(coreStr);
            ecuoCoreService.insert(record);
        } else {
            record.setEcuocId(ecuoCore.getEcuocId());
            ecuoCoreService.update(record);
        }
    }

    //getObjectPassEcuoIdAndAreaStr
    public EcuoCore getObjectPassEcqulIdAndAreaStr(int ecqulId, String coreStr) {
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        record.setCoreStr(coreStr);
        return ecuoCoreService.getObject(record);
    }

    //getObjectPassEcqulId
    public EcuoCore getObjectPassEcqulId(int ecqulId) {
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        return ecuoCoreService.getObject(record);
    }
}
