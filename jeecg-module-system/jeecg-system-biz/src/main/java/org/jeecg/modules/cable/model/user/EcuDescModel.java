package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescDealBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescPageBo;
import org.jeecg.modules.cable.controller.user.udesc.bo.EcuDescSortBo;
import org.jeecg.modules.cable.controller.user.udesc.vo.UDescVo;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.service.user.EcuDescService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EcuDescModel {
    @Resource
    EcuDescService ecuDescService;

    //getList
    public UDescVo getList(EcuDescPageBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        EcuDesc record = new EcuDesc();
        record.setEcuId(ecuId);

        record.setStartType(bo.getStartType());
        if (bo.getPageNumber() != null) {
            int pageNumber = bo.getPageNumber();
            int startNumber = (bo.getPage() - 1) * pageNumber;
            record.setStartNumber(startNumber);
            record.setPageNumber(pageNumber);
        }
        List<EcuDesc> list = ecuDescService.getList(record);
        long count = ecuDescService.getCount(record);
        return new UDescVo(list, count);
    }

    //getObject
    public EcuDesc getObject(EcuDescBo bo) {
        EcuDesc record = new EcuDesc();
        if (bo.getEcudId() != null) {
            int ecudId = bo.getEcudId();
            record.setEcudId(ecudId);
        }
        if (bo.getDefaultType() != null) {
            boolean defaultType = bo.getDefaultType();
            record.setDefaultType(defaultType);
        }
        return ecuDescService.getObject(record);
    }

    //deal
    public String deal(EcuDescDealBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();

        Integer ecudId = bo.getEcudId();
        String content = bo.getContent();
        EcuDesc record = new EcuDesc();
        String msg;
        if (ObjectUtil.isNull(ecudId)) {//插入
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
            msg = "正常新增数据";
        } else {//修改
            record.setEcudId(ecudId);
            record.setContent(content);
            record.setUpdateTime(System.currentTimeMillis());
            ecuDescService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    //start
    public String start(EcuDescBo bo) {

        int ecudId = bo.getEcudId();
        EcuDesc ecuDesc = getObjectPassEcudId(ecudId);
        String msg;
        boolean startType = ecuDesc.getStartType();
        if (!startType) {
            startType = true;
            msg = "启用成功";
        } else {
            startType = false;
            msg = "禁用成功";
        }
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        record.setStartType(startType);
        ecuDescService.update(record);
        return msg;
    }

    //sort
    public void sort(EcuDescSortBo bo) {
        int ecudId = bo.getEcudId();
        int sortId = bo.getSortId();
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        record.setSortId(sortId);
        ecuDescService.update(record);

    }

    //delete
    public void delete(EcuDescBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        int ecudId = bo.getEcudId();
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
    }

    //defaultType
    public void defaultType(EcuDescBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        int ecudId = bo.getEcudId();
        EcuDesc record = new EcuDesc();
        record.setEcuId(ecuId);
        record.setDefaultType(false);
        ecuDescService.update(record);

        record = new EcuDesc();
        record.setEcudId(ecudId);
        record.setDefaultType(true);
        ecuDescService.update(record);
    }

    /***===数据模型===***/

//getObjectPassEcunId
    public EcuDesc getObjectPassEcudId(int ecudId) {
        EcuDesc record = new EcuDesc();
        record.setEcudId(ecudId);
        return ecuDescService.getObject(record);
    }
}
