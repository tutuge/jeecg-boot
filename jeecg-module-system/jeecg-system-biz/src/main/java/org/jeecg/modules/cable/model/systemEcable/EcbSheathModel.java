package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSheathService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbSheathModel {
    @Resource
    EcUserService ecUserService;
    @Resource
    EcbSheathService ecbSheathService;
    @Resource
    EcbuSheathService ecbuSheathService;

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
        EcbSheath record = new EcbSheath();
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
        List<EcbSheath> list = ecbSheathService.getList(record);
        long count = ecbSheathService.getCount();
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取列表
        code = "200";
        msg = "正常获取列表";
        CommonFunction.getCommonMap(map, status, code, msg);}
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
        int ecbsId = Integer.parseInt(request.getParameter("ecbsId"));
        EcbSheath recordEcbSheath = new EcbSheath();
        recordEcbSheath.setEcbsId(ecbsId);
        EcbSheath ecbSheath = ecbSheathService.getObject(recordEcbSheath);
        EcbuSheath record = new EcbuSheath();
        record.setEcbsId(ecbsId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSheath ecbuSheath = ecbuSheathService.getObject(record);
        if (ecbuSheath != null) {
            ecbSheath.setEcbuSheath(ecbuSheath);
        }
        map.put("object", ecbSheath);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbSheath> getListStart() {
        EcbSheath record = new EcbSheath();
        record.setStartType(true);
        return ecbSheathService.getListStart(record);
    }

    // getListSilkName 获取丝型号名称 为报价页面提供数据
    public List<EcbSheath> getListSilkName(int ecuId) {
        List<EcbSheath> list;
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        boolean startType = true;
        EcbSheath record = new EcbSheath();
        record.setStartType(startType);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        list = ecbSheathService.getList(record);
        for (int i = 0; i < list.size(); i++) {
            EcbuSheath recordEcbuSheath = new EcbuSheath();
            recordEcbuSheath.setEcbsId(list.get(i).getEcbsId());
            recordEcbuSheath.setEcCompanyId(ecUser.getEcCompanyId());
            EcbuSheath ecbuSheath = ecbuSheathService.getObject(recordEcbuSheath);
            if (ecbuSheath != null) {
                if (list.get(i).getAbbreviation().contains("D2")) {
                    list.remove(i);
                    i--;
                } else if (list.get(i).getAbbreviation().contains("D1")) {
                    list.get(i).setAbbreviation("");
                }
            } else {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
