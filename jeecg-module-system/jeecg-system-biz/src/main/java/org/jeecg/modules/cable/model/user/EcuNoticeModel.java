package org.jeecg.modules.cable.model.user;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.service.user.EcuNoticeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcuNoticeModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuNoticeService ecuNoticeService;
    @Resource
    EcUserModel ecUserModel;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

            EcuNotice record = new EcuNotice();
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
            List<EcuNotice> list = ecuNoticeService.getList(record);
            long count = ecuNoticeService.getCount(record);
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

            EcuNotice record = new EcuNotice();
            if (request.getParameter("ecunId") != null) {
                int ecunId = Integer.parseInt(request.getParameter("ecunId"));
                record.setEcunId(ecunId);
            }
            if (request.getParameter("defaultType") != null) {
                boolean defaultType = Boolean.parseBoolean(request.getParameter("defaultType"));
                record.setDefaultType(defaultType);
            }
            EcuNotice ecuNotice = ecuNoticeService.getObject(record);
            map.put("object", ecuNotice);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecunId = Integer.parseInt(request.getParameter("ecunId"));
            String noticeName = request.getParameter("noticeName");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            EcuNotice record = new EcuNotice();
            if (ecunId == 0) {//插入
                int sortId = 1;
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setEcuId(ecuId);
                EcuNotice ecuNotice = ecuNoticeService.getObject(record);
                if (ecuNotice != null) {
                    sortId = ecuNotice.getSortId() + 1;
                }
                record.setDefaultType(false);
                record.setStartType(true);
                record.setSortId(sortId);
                record.setNoticeName(noticeName);
                record.setTitle(title);
                record.setContent(content);
                record.setAddTime(System.currentTimeMillis());
                record.setUpdateTime(System.currentTimeMillis());
                //log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuNoticeService.insert(record);
                status = 3;//正常新增数据
                code = "200";
                msg = "正常新增数据";
            } else {//修改
                record.setEcunId(ecunId);
                record.setNoticeName(noticeName);
                record.setTitle(title);
                record.setContent(content);
                record.setUpdateTime(System.currentTimeMillis());
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuNoticeService.update(record);
                status = 4;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {

            int ecunId = Integer.parseInt(request.getParameter("ecunId"));
            EcuNotice ecuNotice = getObjectPassEcunId(ecunId);
            boolean startType = ecuNotice.getStartType();
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
            EcuNotice record = new EcuNotice();
            record.setEcunId(ecunId);
            record.setStartType(startType);
            ecuNoticeService.update(record);
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {

            int ecunId = Integer.parseInt(request.getParameter("ecunId"));
            int sortId = Integer.parseInt(request.getParameter("sortId"));
            EcuNotice record = new EcuNotice();
            record.setEcunId(ecunId);
            record.setSortId(sortId);
            ecuNoticeService.update(record);
            status = 3;
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {

            int ecunId = Integer.parseInt(request.getParameter("ecunId"));
            EcuNotice record = new EcuNotice();
            record.setEcunId(ecunId);
            EcuNotice ecuNotice = ecuNoticeService.getObject(record);
            int sortId = ecuNotice.getSortId();
            record = new EcuNotice();
            record.setEcuId(ecuId);
            record.setSortId(sortId);
            List<EcuNotice> list = ecuNoticeService.getList(record);
            log.info("list + " + CommonFunction.getGson().toJson(list));
            int ecun_id;
            for (EcuNotice notice : list) {
                ecun_id = notice.getEcunId();
                sortId = notice.getSortId() - 1;
                record.setEcunId(ecun_id);
                record.setSortId(sortId);
                ecuNoticeService.update(record);
            }
            record = new EcuNotice();
            record.setEcunId(ecunId);
            ecuNoticeService.delete(record);
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    //defaultType
    public Map<String, Object> defaultType(HttpServletRequest request) {

            int ecunId = Integer.parseInt(request.getParameter("ecunId"));
            EcuNotice record = new EcuNotice();
            record.setEcuId(ecuId);
            record.setDefaultType(false);
            ecuNoticeService.update(record);
            record = new EcuNotice();
            record.setEcunId(ecunId);
            record.setDefaultType(true);
            ecuNoticeService.update(record);
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }

    /***===数据模型===***/
    //getObjectPassEcunId
    public EcuNotice getObjectPassEcunId(int ecunId) {
        EcuNotice record = new EcuNotice();
        record.setEcunId(ecunId);
        return ecuNoticeService.getObject(record);
    }

    //getObjectDefaultPassEcuId 根据用户ID获取默认项
    public EcuNotice getObjectDefaultPassEcuId(int ecuId) {
        EcuNotice record = new EcuNotice();
        record.setEcuId(ecuId);
        record.setDefaultType(true);
        return ecuNoticeService.getObject(record);
    }
}
