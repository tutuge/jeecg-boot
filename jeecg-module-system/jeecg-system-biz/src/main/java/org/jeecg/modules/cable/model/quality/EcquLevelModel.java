package org.jeecg.modules.cable.model.quality;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.price.EcSilkService;
import org.jeecg.modules.cable.service.quality.EcquLevelService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcquLevelModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcquLevelService ecquLevelService;
    @Resource
    EcdCollectModel ecdCollectModel;
    @Resource
    EcUserModel ecUserModel;
    @Resource
    EcSilkService ecSilkService;

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
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

            EcquLevel record = new EcquLevel();
            if (request.getParameter("ecqulId") != null) {
                int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
                record.setEcqulId(ecqulId);
                EcquLevel object = ecquLevelService.getObject(record);
                EcSilk recordEcSilk = new EcSilk();
                recordEcSilk.setEcsId(object.getEcsId());
                EcSilk ecSilk = ecSilkService.getObject(recordEcSilk);
                object.setEcSilk(ecSilk);
                map.put("object", object);
            }
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
            int ecsId = Integer.parseInt(request.getParameter("ecsId"));
            int ecbucId = Integer.parseInt(request.getParameter("ecbucId"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            EcquLevel record = new EcquLevel();
            record.setEcqulId(ecqulId);
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setName(name);
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
                status = 4;//正常插入数据
                code = "200";
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
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {

            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
            int sortId = Integer.parseInt(request.getParameter("sortId"));
            EcquLevel record = new EcquLevel();
            record.setEcqulId(ecqulId);
            record.setSortId(sortId);
            ecquLevelService.update(record);
            deal(ecUser.getEcCompanyId());//加载load为集成数据
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {

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
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            deal(ecUser.getEcCompanyId());//加载load为集成数据
            status = 3;//数据操作成功
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {

            int ecqulId = Integer.parseInt(request.getParameter("ecqulId"));
            EcquLevel record = new EcquLevel();
            record.setEcqulId(ecqulId);
            EcquLevel ecquLevel = ecquLevelService.getObject(record);
            boolean startType = ecquLevel.getStartType();
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
            record = new EcquLevel();
            record.setEcqulId(ecquLevel.getEcqulId());
            record.setStartType(startType);
            ecquLevelService.update(record);
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            deal(ecUser.getEcCompanyId());//加载load为集成数据
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //defaultType
    public Map<String, Object> defaultType(HttpServletRequest request) {

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
            status = 3;
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
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
