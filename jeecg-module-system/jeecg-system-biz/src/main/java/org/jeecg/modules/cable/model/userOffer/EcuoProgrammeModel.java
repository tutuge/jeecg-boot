package org.jeecg.modules.cable.model.userOffer;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.userOffer.EcuoProgrammeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuoProgrammeModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcUserModel ecUserModel;
    @Resource
    EcuoProgrammeService ecuoProgrammeService;

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
            String programmeName = request.getParameter("programmeName");
            String coreStr = request.getParameter("coreStr");
            String areaStr = request.getParameter("areaStr");
            BigDecimal addPercent = new BigDecimal(request.getParameter("addPercent"));
            EcuoProgramme record = new EcuoProgramme();
            if (ecuopId == 0) {//插入
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setProgrammeName(programmeName);
                EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
                if (ecuoProgramme != null) {
                    status = 3;//方案名称已占用
                    code = "103";
                    msg = "方案名称已占用";
                } else {
                    int sortId = 1;
                    record = new EcuoProgramme();
                    record.setEcCompanyId(ecUser.getEcCompanyId());
                    ecuoProgramme = ecuoProgrammeService.getObject(record);
                    if (ecuoProgramme != null) {
                        sortId = ecuoProgramme.getSortId() + 1;
                    }
                    record.setSortId(sortId);
                    record.setProgrammeName(programmeName);
                    record.setCoreStr(coreStr);
                    record.setAreaStr(areaStr);
                    record.setAddPercent(addPercent);
                    ecuoProgrammeService.insert(record);
                    status = 4;//正常新增数据
                    code = "200";
                    msg = "正常新增数据";
                }
            } else {
                record.setEcuopId(ecuopId);
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setProgrammeName(programmeName);
                EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
                if (ecuoProgramme != null) {
                    status = 3;//方案名称已占用
                    code = "103";
                    msg = "方案名称已占用";
                } else {
                    record.setCoreStr(coreStr);
                    record.setAreaStr(areaStr);
                    record.setAddPercent(addPercent);
                    ecuoProgrammeService.update(record);
                    status = 5;//正常更新数据
                    code = "201";
                    msg = "正常更新数据";
                }
            }
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            EcuoProgramme record = new EcuoProgramme();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            List<EcuoProgramme> list = ecuoProgrammeService.getList(record);
            map.put("list", list);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {

            int ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
            EcuoProgramme record = new EcuoProgramme();
            record.setEcuopId(ecuopId);
            EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
            map.put("object", ecuoProgramme);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {

            int ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
            int sortId = Integer.parseInt(request.getParameter("sortId"));
            EcuoProgramme record = new EcuoProgramme();
            record.setEcuopId(ecuopId);
            record.setSortId(sortId);
            ecuoProgrammeService.update(record);
            status = 3;//正常操作数据
            code = "200";
            msg = "正常操作数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {

            int ecuopId = Integer.parseInt(request.getParameter("ecuopId"));
            EcuoProgramme record = new EcuoProgramme();
            record.setEcuopId(ecuopId);
            EcuoProgramme ecuoProgramme = ecuoProgrammeService.getObject(record);
            int sortId = ecuoProgramme.getSortId();
            record = new EcuoProgramme();
            record.setEcCompanyId(ecuoProgramme.getEcCompanyId());
            record.setSortId(sortId);
            List<EcuoProgramme> list = ecuoProgrammeService.getList(record);
            int ecuop_id;
            for (EcuoProgramme programme : list) {
                ecuop_id = programme.getEcuopId();
                sortId = programme.getSortId() - 1;
                record.setEcuopId(ecuop_id);
                record.setSortId(sortId);
                ecuoProgrammeService.update(record);
            }
            record = new EcuoProgramme();
            record.setEcuopId(ecuopId);
            ecuoProgrammeService.delete(record);
            status = 4;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    public EcuoProgramme getObjectPassEcuopId(int ecuopId) {
        EcuoProgramme record = new EcuoProgramme();
        record.setEcuopId(ecuopId);
        return ecuoProgrammeService.getObject(record);
    }
}
