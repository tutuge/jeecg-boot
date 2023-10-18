package org.jeecg.modules.cable.model.quality;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.quality.uarea.bo.UAreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.vo.UAreaVo;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.service.quality.EcuAreaService;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EcuAreaModel {
    @Resource
    EcuAreaService ecuAreaService;
    @Resource
    EcUserService ecUserService;

    //getListAndCount
    public UAreaVo getListAndCount(UAreaBo bo) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqulId = bo.getEcqulId();
        EcuArea record = new EcuArea();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcqulId(ecqulId);
        List<EcuArea> list = ecuAreaService.getList(record);
        long count = ecuAreaService.getCount(record);
        return new UAreaVo(list, count);
    }

    //getObject
    public EcuArea getObject(HttpServletRequest request) {

        EcuArea recordEcuArea = new EcuArea();
        if (request.getParameter("ecuaId") != null) {
            int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
            recordEcuArea.setEcuaId(ecuaId);
        }
        return ecuAreaService.getObject(recordEcuArea);
    }

    //deal
    public String deal(HttpServletRequest request) {
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
        String msg;
        if (ecuArea != null) {
            throw new RuntimeException("截面积已占用");
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
                msg = "正常插入数据";
            } else {//更新
                record.setEcuaId(ecuaId);
                record.setAreaStr(areaStr);
                ecuAreaService.updateByPrimaryKeySelective(record);
                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //sort
    public void sort(HttpServletRequest request) {
        int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcuArea record = new EcuArea();
        record.setEcuaId(ecuaId);
        record.setSortId(sortId);
        ecuAreaService.updateByPrimaryKeySelective(record);
    }

    //start
    public String start(HttpServletRequest request) {

        int ecuaId = Integer.parseInt(request.getParameter("ecuaId"));
        EcuArea recordEcuArea = new EcuArea();
        recordEcuArea.setEcuaId(ecuaId);
        EcuArea ecuArea = ecuAreaService.getObject(recordEcuArea);
        boolean startType = ecuArea.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        EcuArea record = new EcuArea();
        record.setEcuaId(ecuaId);
        record.setStartType(startType);
        ecuAreaService.updateByPrimaryKeySelective(record);
        return msg;
    }
}
