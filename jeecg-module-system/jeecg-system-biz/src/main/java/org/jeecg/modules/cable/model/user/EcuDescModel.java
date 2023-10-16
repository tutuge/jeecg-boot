package org.jeecg.modules.cable.model.user;

import org.jeecg.modules.cable.entity.user.EcUser;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.service.user.EcuDescService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuDescModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuDescService ecuDescService;
    @Resource
    EcUserModel ecUserModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcuDesc record = new EcuDesc();
            record.setEcuId(ecuId);
            if (request.getParameter("startType") != null) {
                boolean startType = true;
                if (!"0".equals(request.getParameter("startType"))) {
                    if ("2".equals(request.getParameter("startType"))) {
                        startType = false;
                    }
                    record.setStartType(startType);
                }
            }
            if (request.getParameter("pageNumber") != null) {
                int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
                int startNumber = (Integer.parseInt(request.getParameter("page")) - 1) * pageNumber;
                record.setStartNumber(startNumber);
                record.setPageNumber(pageNumber);
            }
            List<EcuDesc> list = ecuDescService.getList(record);
            long count = ecuDescService.getCount(record);
            map.put("list", list);
            map.put("count", count);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
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
            EcuDesc record = new EcuDesc();
            if (request.getParameter("ecudId") != null) {
                int ecudId = Integer.parseInt(request.getParameter("ecudId"));
                record.setEcudId(ecudId);
            }
            if (request.getParameter("defaultType") != null) {
                boolean defaultType = Boolean.parseBoolean(request.getParameter("defaultType"));
                record.setDefaultType(defaultType);
            }
            EcuDesc ecuDesc = ecuDescService.getObject(record);
            map.put("object", ecuDesc);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

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
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecudId = Integer.parseInt(request.getParameter("ecudId"));
            String content = request.getParameter("content");
            EcuDesc record = new EcuDesc();
            if (ecudId == 0) {//插入
                int sortId = 1;
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcuId(ecuId);
                EcuDesc ecuNotice = ecuDescService.getObject(record);
                if (ecuNotice != null) {
                    sortId = ecuNotice.getSortId() + 1;
                }
                record.setDefaultType(false);
                record.setStartType(true);
                record.setSortId(sortId);
                record.setContent(content);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                //log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuDescService.insert(record);
                status = 3;//正常新增数据
                code = "200";
                msg = "正常新增数据";
            } else {//修改
                record.setEcudId(ecudId);
                record.setContent(content);
                record.setUpdateTime(System.currentTimeMillis());
                ecuDescService.update(record);
                status = 4;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecudId = Integer.parseInt(request.getParameter("ecudId"));
            EcuDesc ecuDesc = getObjectPassEcudId(ecudId);
            boolean startType = ecuDesc.getStartType();
            if (!startType) {
                startType = true;
                status = 3;
                code = "200";
                msg = "启用成功";
            } else {
                startType = false;
                status = 4;
                code = "201";
                msg = "禁用成功";
            }
            EcuDesc record = new EcuDesc();
            record.setEcudId(ecudId);
            record.setStartType(startType);
            ecuDescService.update(record);
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecudId = Integer.parseInt(request.getParameter("ecudId"));
            int sortId = Integer.parseInt(request.getParameter("sortId"));
            EcuDesc record = new EcuDesc();
            record.setEcudId(ecudId);
            record.setSortId(sortId);
            ecuDescService.update(record);
            status = 3;
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecudId = Integer.parseInt(request.getParameter("ecudId"));
            EcuDesc record = new EcuDesc();
            record.setEcudId(ecudId);
            EcuDesc ecuDesc = ecuDescService.getObject(record);
            int sortId = ecuDesc.getSortId();
            record = new EcuDesc();
            record.setEcuId(ecuId);
            record.setSortId(sortId);
            List<EcuDesc> list = ecuDescService.getList(record);
            int ecud_id;
            for (EcuDesc desc : list) {
                ecud_id = desc.getEcudId();
                sortId = desc.getSortId() - 1;
                record.setEcudId(ecud_id);
                record.setSortId(sortId);
                ecuDescService.update(record);
            }
            record = new EcuDesc();
            record.setEcudId(ecudId);
            ecuDescService.delete(record);
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //defaultType
    public Map<String, Object> defaultType(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            int ecudId = Integer.parseInt(request.getParameter("ecudId"));
            EcuDesc record = new EcuDesc();
            record.setEcuId(ecuId);
            record.setDefaultType(false);
            ecuDescService.update(record);
            record = new EcuDesc();
            record.setEcudId(ecudId);
            record.setDefaultType(true);
            ecuDescService.update(record);
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    /***===数据模型===***/

    //getObjectPassEcunId
    public EcuDesc getObjectPassEcudId(int ecudId) {
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        return ecuDescService.getObject(record);
    }
}
