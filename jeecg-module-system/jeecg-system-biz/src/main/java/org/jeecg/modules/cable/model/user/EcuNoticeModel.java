package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.notice.vo.NoticeVo;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.service.user.EcuNoticeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcuNoticeModel {
    @Resource
    EcuNoticeService ecuNoticeService;

    //getList
    public NoticeVo getList(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
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
        return new NoticeVo(list, count);
    }

    //getObject
    public EcuNotice getObject(HttpServletRequest request) {
        EcuNotice record = new EcuNotice();
        if (request.getParameter("ecunId") != null) {
            int ecunId = Integer.parseInt(request.getParameter("ecunId"));
            record.setEcunId(ecunId);
        }
        if (request.getParameter("defaultType") != null) {
            boolean defaultType = Boolean.parseBoolean(request.getParameter("defaultType"));
            record.setDefaultType(defaultType);
        }
        return ecuNoticeService.getObject(record);
    }

    //deal
    public String deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        int ecunId = Integer.parseInt(request.getParameter("ecunId"));
        String noticeName = request.getParameter("noticeName");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        EcuNotice record = new EcuNotice();
        String msg;
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

            msg = "正常新增数据";
        } else {//修改
            record.setEcunId(ecunId);
            record.setNoticeName(noticeName);
            record.setTitle(title);
            record.setContent(content);
            record.setUpdateTime(System.currentTimeMillis());
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecuNoticeService.update(record);

            msg = "正常更新数据";
        }
        return msg;
    }

    //start
    public String start(HttpServletRequest request) {

        int ecunId = Integer.parseInt(request.getParameter("ecunId"));
        EcuNotice ecuNotice = getObjectPassEcunId(ecunId);
        boolean startType = ecuNotice.getStartType();
        String msg;
        if (!startType) {
            startType = true;

            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EcuNotice record = new EcuNotice();
        record.setEcunId(ecunId);
        record.setStartType(startType);
        ecuNoticeService.update(record);
        return msg;
    }

    //sort
    public void sort(HttpServletRequest request) {

        int ecunId = Integer.parseInt(request.getParameter("ecunId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcuNotice record = new EcuNotice();
        record.setEcunId(ecunId);
        record.setSortId(sortId);
        ecuNoticeService.update(record);
    }

    //delete
    public void delete(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
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
    }

    //defaultType
    public void defaultType(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecunId = Integer.parseInt(request.getParameter("ecunId"));
        EcuNotice record = new EcuNotice();
        Integer ecuId = ecUser.getEcuId();
        record.setEcuId(ecuId);
        record.setDefaultType(false);
        ecuNoticeService.update(record);
        record = new EcuNotice();
        record.setEcunId(ecunId);
        record.setDefaultType(true);
        ecuNoticeService.update(record);
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
