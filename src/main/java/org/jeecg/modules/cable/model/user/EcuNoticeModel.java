package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.notice.bo.*;
import org.jeecg.modules.cable.controller.user.notice.vo.NoticeVo;
import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.jeecg.modules.cable.service.user.EcuNoticeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcuNoticeModel {
    @Resource
    EcuNoticeService ecuNoticeService;


    public NoticeVo getList(EcuNoticePageBo bo) {
LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
Integer ecuId = sysUser.getUserId();
        EcuNotice record = new EcuNotice();
        record.setEcuId(ecuId);
        BeanUtils.copyProperties(bo, record);

        record.setStartType(bo.getStartType());

        if (bo.getPageNum() != null) {
            Integer pageNumber = bo.getPageSize();
            Integer startNumber = (bo.getPageNum() - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        List<EcuNotice> list = ecuNoticeService.getList(record);
        long count = ecuNoticeService.getCount(record);
        return new NoticeVo(list, count);
    }


    public EcuNotice getObject(EcuNoticeBo bo) {
        EcuNotice record = new EcuNotice();
        Integer ecunId1 = bo.getEcunId();
        if (ecunId1 != null) {
            Integer ecunId = ecunId1;
            record.setEcunId(ecunId);
        }
        Boolean defaultType1 = bo.getDefaultType();
        if (defaultType1 != null) {
            Boolean defaultType = defaultType1;
            record.setDefaultType(defaultType);
        }
        return ecuNoticeService.getObject(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcuNoticeDealBo bo) {
LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
Integer ecuId = sysUser.getUserId();

        Integer ecunId = bo.getEcunId();
        String noticeName = bo.getNoticeName();
        String title = bo.getTitle();
        String content = bo.getContent();

        EcuNotice record = new EcuNotice();
        String msg;
        if (ObjectUtil.isNull(ecunId)) {// 插入
            Integer sortId = 1;
            record.setEcCompanyId(sysUser.getEcCompanyId());
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
            // log.info("record + " + CommonFunction.getGson().toJson(record));
            ecuNoticeService.insert(record);

            msg = "正常新增数据";
        } else {// 修改
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


    public String start(EcuNoticeStartBo bo) {
        Integer ecunId = bo.getEcunId();
        EcuNotice ecuNotice = getObjectPassEcunId(ecunId);
        Boolean startType = ecuNotice.getStartType();
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


    public void sort(EcuNoticeSortBo bo) {
        Integer ecunId = bo.getEcunId();
        Integer sortId = bo.getSortId();
        EcuNotice record = new EcuNotice();
        record.setEcunId(ecunId);
        record.setSortId(sortId);
        ecuNoticeService.update(record);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(EcuNoticeStartBo bo) {
LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
Integer ecuId = sysUser.getUserId();

        Integer ecunId = bo.getEcunId();
        EcuNotice record = new EcuNotice();
        record.setEcunId(ecunId);
        EcuNotice ecuNotice = ecuNoticeService.getObject(record);
        Integer sortId = ecuNotice.getSortId();
        record = new EcuNotice();
        record.setEcuId(ecuId);
        record.setSortId(sortId);
        List<EcuNotice> list = ecuNoticeService.getList(record);
        log.info("list + " + CommonFunction.getGson().toJson(list));
        Integer ecun_id;
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


    public void defaultType(EcuNoticeStartBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer ecunId = bo.getEcunId();
        // 先将根据用户查询的设置为非默认
        EcuNotice record = new EcuNotice();
        Integer ecuId = sysUser.getUserId();
        record.setEcuId(ecuId);
        record.setDefaultType(false);
        ecuNoticeService.update(record);
        // 再将对应的主键的设置为默认
        record = new EcuNotice();
        record.setEcunId(ecunId);
        record.setDefaultType(true);
        ecuNoticeService.update(record);
    }

    
// getObjectPassEcunId
    public EcuNotice getObjectPassEcunId(Integer ecunId) {
        EcuNotice record = new EcuNotice();
        record.setEcunId(ecunId);
        return ecuNoticeService.getObject(record);
    }

    // getObjectDefaultPassEcuId 根据用户ID获取默认项
    public EcuNotice getObjectDefaultPassEcuId(Integer ecuId) {
        EcuNotice record = new EcuNotice();
        record.setEcuId(ecuId);
        record.setDefaultType(true);
        return ecuNoticeService.getObject(record);
    }
}
