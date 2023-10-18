package org.jeecg.modules.cable.model.quality;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.quality.level.vo.LevelVo;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EcquLevelModel {
    @Resource
    EcquLevelService ecquLevelService;
    @Resource
    EcdCollectModel ecdCollectModel;
    @Resource
    EcUserModel ecUserModel;
    @Resource
    EcSilkService ecSilkService;

    //getList
    public LevelVo getList(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcquLevel record = new EcquLevel();
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
        List<EcquLevel> list = ecquLevelService.getList(record);
        long count = ecquLevelService.getCount(record);
        return new LevelVo(list, count);

    }

    //getObject
    public EcquLevel getObject(HttpServletRequest request) {

        EcquLevel record = new EcquLevel();
        if (request.getParameter("ecqulId") != null) {
            int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
            record.setEcqulId(ecqulId);
            EcquLevel object = ecquLevelService.getObject(record);
            EcSilk recordEcSilk = new EcSilk();
            recordEcSilk.setEcsId(object.getEcsId());
            EcSilk ecSilk = ecSilkService.getObject(recordEcSilk);
            object.setEcSilk(ecSilk);
            return object;
        }
        return null;
    }

    //deal
    public String deal(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        int ecsId = Integer.parseInt(request.getParameter("ecsId"));
        int ecbucId = Integer.parseInt(request.getParameter("ecbucId"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setName(name);
        String msg;
        if (ecqulId == 0) {//插入
            int sortId = 1;
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record = new EcquLevel();
            record.setEcsId(ecsId);
            record.setEcbucId(ecbucId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setStartType(true);
            record.setSortId(sortId);
            record.setPowerId(2);//自有库
            record.setName(name);
            record.setDefaultType(false);
            record.setDescription(description);
            ecquLevelService.insert(record);

            msg = "正常插入数据";
        } else {//更新
            record.setEcqulId(ecqulId);
            record.setEcsId(ecsId);
            record.setEcbucId(ecbucId);
            record.setName(name);
            record.setDescription(description);
            log.info("record + " + CommonFunction.getGson().toJson(record));
            ecquLevelService.update(record);
            msg = "正常更新数据";
        }
        deal(ecUser.getEcCompanyId());//加载load为集成数据
        return msg;
    }

    //sort
    public void sort(HttpServletRequest request) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setSortId(sortId);
        ecquLevelService.update(record);
        deal(ecUser.getEcCompanyId());//加载load为集成数据
    }

    //delete
    public void delete(HttpServletRequest request) {

        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        int sortId = ecquLevel.getSortId();
        record = new EcquLevel();
        record.setSortId(sortId);
        record.setEcCompanyId(ecquLevel.getEcCompanyId());
        List<EcquLevel> list = ecquLevelService.getList(record);
        int ecqul_id;
        for (EcquLevel ecqu_level : list) {
            ecqul_id = ecqu_level.getEcqulId();
            sortId = ecqu_level.getSortId() - 1;
            record.setEcqulId(ecqul_id);
            record.setSortId(sortId);
            ecquLevelService.update(record);
        }
        record = new EcquLevel();
        record.setEcqulId(ecqulId);
        ecquLevelService.delete(record);

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        deal(ecUser.getEcCompanyId());//加载load为集成数据
    }

    //start
    public String start(HttpServletRequest request) {

        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        EcquLevel ecquLevel = ecquLevelService.getObject(record);
        boolean startType = ecquLevel.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcquLevel();
        record.setEcqulId(ecquLevel.getEcqulId());
        record.setStartType(startType);
        ecquLevelService.update(record);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        deal(ecUser.getEcCompanyId());//加载load为集成数据

        return msg;
    }

    //defaultType
    public void defaultType(HttpServletRequest request) {

        int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
        EcquLevel ecquLevel = getObjectPassEcqulId(ecqulId);
        EcquLevel record = new EcquLevel();
        record.setEcsId(ecquLevel.getEcsId());
        record.setDefaultType(false);
        ecquLevelService.update(record);
        record = new EcquLevel();
        record.setEcqulId(ecqulId);
        record.setDefaultType(true);
        ecquLevelService.update(record);
    }


    /***===数据模型===***/
    //deal
    public void deal(EcquLevel record) {
        EcquLevel recordEcquLevel = new EcquLevel();
        recordEcquLevel.setName(record.getName());
        recordEcquLevel.setEcCompanyId(record.getEcCompanyId());
        EcquLevel object = ecquLevelService.getObject(recordEcquLevel);
        if (object == null) {
            int sortId = 1;
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            if (ecquLevel != null) {
                sortId = ecquLevel.getSortId() + 1;
            }
            record.setSortId(sortId);
            //log.info("record + " + CommonFunction.getGson().toJson(record));
            ecquLevelService.insert(record);
        } else {
            ecquLevelService.update(record);
        }
    }

    //deal
    public void deal(int ecCompanyId) {
        EcquLevel record = new EcquLevel();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        List<EcquLevel> list = ecquLevelService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 2, txtList);
    }

    //getObjectPassEcqulId
    public EcquLevel getObjectPassEcqulId(int ecqulId) {
        EcquLevel record = new EcquLevel();
        record.setEcqulId(ecqulId);
        return ecquLevelService.getObject(record);
    }

    //getObjectPassEcsIdAndDefaultType 根据丝系列获取默认的质量等级，如果没有默认等级，随机获取一个
    public EcquLevel getObjectPassEcsIdAndDefaultType(int ecuId, int ecsId) {
        EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
        EcquLevel ecquLevel;
        EcquLevel record = new EcquLevel();
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcsId(ecsId);
        record.setDefaultType(true);
        ecquLevel = ecquLevelService.getObject(record);
        if (ecquLevel == null) {
            record = new EcquLevel();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setEcsId(ecsId);
            ecquLevel = ecquLevelService.getObject(record);
        }
        return ecquLevel;
    }

    //getObjectPassEcCompanyIdAndName 根据公司ID和质量等级名称获取数据
    public EcquLevel getObjectPassEcCompanyIdAndName(int ecCompanyId, String name) {
        EcquLevel record = new EcquLevel();
        record.setEcCompanyId(ecCompanyId);
        record.setName(name);
        return ecquLevelService.getObject(record);
    }

}
