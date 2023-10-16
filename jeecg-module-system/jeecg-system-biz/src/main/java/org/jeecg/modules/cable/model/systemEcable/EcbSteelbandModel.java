package org.jeecg.modules.cable.model.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.systemEcable.EcbSteelbandService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcbSteelbandModel {
    @Resource
    EcbSteelbandService ecbSteelbandService;
    @Resource
    EcbuSteelbandService ecbuSteelbandService;
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
        EcbSteelband record = new EcbSteelband();
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
        List<EcbSteelband> list = ecbSteelbandService.getList(record);
        long count = ecbSteelbandService.getCount();
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
        int ecbsbId = Integer.parseInt(request.getParameter("ecbsbId"));
        EcbSteelband recordEcbSteelband = new EcbSteelband();
        recordEcbSteelband.setEcbsbId(ecbsbId);
        EcbSteelband ecbSteelband = ecbSteelbandService.getObject(recordEcbSteelband);
        EcbuSteelband record = new EcbuSteelband();
        record.setEcbsbId(ecbsbId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcbuSteelband ecbuSteelband = ecbuSteelbandService.getObject(record);
        if (ecbuSteelband != null) {
            ecbSteelband.setEcbuSteelband(ecbuSteelband);
        }
        map.put("ecbSteelband", ecbSteelband);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //load 加载用户数据为txt文档
    public void loadData(HttpServletRequest request) {
        int ecCompanyId = 0;
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        ecCompanyId = ecUser.getEcCompanyId();
        EcbSteelband record = new EcbSteelband();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcbSteelband> list = ecbSteelbandService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 9, txtList);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcbSteelband> getListStart() {
        EcbSteelband record = new EcbSteelband();
        record.setStartType(true);
        return ecbSteelbandService.getListStart(record);
    }
}
