package org.jeecg.modules.cable.model.quality;

import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.service.quality.EcuAreaService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcuAreaModel {
    @Resource
    EcuAreaService ecuAreaService;
    @Resource
    EcUserService ecUserService;

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcuArea record = new EcuArea();
record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcuArea> list = ecuAreaService.getList(record);
        long count = ecuAreaService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcuArea recordEcuArea = new EcuArea();
        if (request.getParameter("ecuaId") != null) {
            int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
            recordEcuArea.setEcuaId(ecuaId);
        }
        map.put("object", ecuAreaService.getObject(recordEcuArea));
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        String areaStr = request.getParameter("areaStr");
        EcuArea record = new EcuArea();
        record.setEcqulId(ecqulId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setAreaStr(areaStr);
        EcuArea ecuArea = ecuAreaService.getObjectPassAreaStr(record);
        if (ecuArea != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "截面积已占用";
        } else {
            if (ecuaId == 0) {//插入
                int sortId = 1;
                ecuArea = ecuAreaService.getLatestObject(record);
                if (ecuArea != null) {
                    sortId = ecuArea.getSortId() + 1;
                }
                record = new EcuArea();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcqulId(ecqulId);
                record.setStartType(true);
                record.setSortId(sortId);
                record.setAreaStr(areaStr);
                record.setEffectTime(System.currentTimeMillis());
                System.out.println(CommonFunction.getGson().toJson(record));
                ecuAreaService.insert(record);
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {//更新
                record.setEcuaId(ecuaId);
                record.setAreaStr(areaStr);
                ecuAreaService.updateByPrimaryKeySelective(record);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcuArea record = new EcuArea();
        record.setEcuaId(ecuaId);
        record.setSortId(sortId);
        ecuAreaService.updateByPrimaryKeySelective(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
        EcuArea recordEcuArea = new EcuArea();
        recordEcuArea.setEcuaId(ecuaId);
        EcuArea ecuArea = ecuAreaService.getObject(recordEcuArea);
        boolean startType = ecuArea.getStartType();
        if (!startType) {
            startType = true;
            status = 3;
            code = "200";
            msg = "数据启用成功";
        } else {
            startType = false;
            status = 4;
            code = "201";
            msg = "数据禁用成功";
        }
        EcuArea record = new EcuArea();
        record.setEcuaId(ecuaId);
        record.setStartType(startType);
        ecuAreaService.updateByPrimaryKeySelective(record);
        CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }
}
