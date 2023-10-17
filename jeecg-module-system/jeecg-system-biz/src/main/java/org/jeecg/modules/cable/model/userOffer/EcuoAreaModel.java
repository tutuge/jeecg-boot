package org.jeecg.modules.cable.model.userOffer;

import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userOffer.EcuoAreaService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuoAreaModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuoAreaService ecuoAreaService;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

            int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
            EcuoArea record = new EcuoArea();
            record.setEcqulId(ecqulId);
            List<EcuoArea> list = ecuoAreaService.getList(record);
            map.put("list", list);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    //load
    public void load(int ecqulId, String areaStr) {
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        areaStr = fireArr[1];
        deal(ecqulId, areaStr);
        if (areaArr.length == 2) {
            String[] zeroArr = areaArr[1].split("\\*");
            areaStr = zeroArr[1];
            deal(ecqulId, areaStr);
        }
    }

    //deal
    public void deal(int ecqulId, String areaStr) {
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        record.setAreaStr(areaStr);
        EcuoArea ecuoArea = getObjectPassEcqulIdAndAreaStr(ecqulId, areaStr);
        if (ecuoArea == null) {
            record = new EcuoArea();
            record.setEcqulId(ecqulId);
            ecuoArea = getObjectPassEcqulId(ecqulId);
            int sortId = 1;
            if (ecuoArea != null) {
                sortId = ecuoArea.getSortId() + 1;
            }
            record.setSortId(sortId);
            record.setAreaStr(areaStr);
            ecuoAreaService.insert(record);
        }
    }

    //getObjectPassEcqulIdAndAreaStr
    public EcuoArea getObjectPassEcqulIdAndAreaStr(int ecqulId, String coreStr) {
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        record.setAreaStr(coreStr);
        return ecuoAreaService.getObject(record);
    }

    //getObjectPassEcqulId
    public EcuoArea getObjectPassEcqulId(int ecqulId) {
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        return ecuoAreaService.getObject(record);
    }
}
