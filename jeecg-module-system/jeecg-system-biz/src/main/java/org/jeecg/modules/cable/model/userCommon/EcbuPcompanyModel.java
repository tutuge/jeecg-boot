package org.jeecg.modules.cable.model.userCommon;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcbuPcompanyService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcbuPcompanyModel {
    @Resource
    EcbuPcompanyService ecbuPcompanyService;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcdCollectModel ecdCollectModel;

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcbuPcompany record = new EcbuPcompany();
record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcbuPcompany> list = ecbuPcompanyService.getList(record);
        long count = ecbuPcompanyService.getCount(record);

    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {

        EcbuPcompany record = new EcbuPcompany();
        if (request.getParameter("ecbupId") != null) {
            int ecbupId = Integer.parseInt(request.getParameter("ecbupId"));
            record.setEcbupId(ecbupId);
        }
        map.put("object", ecbuPcompanyService.getObject(record));

    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecbupId = Integer.parseInt(request.getParameter("ecbupId"));
        int platformId = Integer.parseInt(request.getParameter("platformId"));
        String pcName = request.getParameter("pcName");
        BigDecimal pcPercent = new BigDecimal(request.getParameter("pcPercent"));
        String description = request.getParameter("description");
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setPcName(pcName);
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObjectPassPcName(record);
        if (ecbuPcompany != null) {
            status = 3;//名称已占用
            code = "103";
            msg = "名称已占用";
        } else {
            if (ecbupId == 0) {//插入
                int sortId = 1;
                ecbuPcompany = ecbuPcompanyService.getLatestObject(record);
                if (ecbuPcompany != null) {
                    sortId = ecbuPcompany.getSortId() + 1;
                }
                record = new EcbuPcompany();
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setPlatformId(platformId);
                record.setPcName(pcName);
                record.setPcPercent(pcPercent);
                record.setDescription(description);
                System.out.println(CommonFunction.getGson().toJson(record));
                ecbuPcompanyService.insert(record);
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {//更新
                record.setEcbupId(ecbupId);
                record.setPlatformId(platformId);
                record.setPcName(pcName);
                record.setPcPercent(pcPercent);
                record.setDescription(description);
                ecbuPcompanyService.update(record);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData();
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {

        int ecbupId = Integer.parseInt(request.getParameter("ecbupId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        record.setSortId(sortId);
        ecbuPcompanyService.update(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData();
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {

        int ecbupId = Integer.parseInt(request.getParameter("ecbupId"));
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(record);
        int sortId = ecbuPcompany.getSortId();
        record = new EcbuPcompany();
        record.setSortId(sortId);
        record.setEcCompanyId(ecbuPcompany.getEcCompanyId());
        List<EcbuPcompany> list = ecbuPcompanyService.getListGreaterThanSortId(record);
        int ecbup_id;
        for (EcbuPcompany ecbud_money : list) {
            ecbup_id = ecbud_money.getEcbupId();
            sortId = ecbud_money.getSortId() - 1;
            record.setEcbupId(ecbup_id);
            record.setSortId(sortId);
            ecbuPcompanyService.update(record);
        }
        record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        ecbuPcompanyService.delete(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData();
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {

        int ecbupId = Integer.parseInt(request.getParameter("ecbupId"));
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(record);
        boolean startType = ecbuPcompany.getStartType();
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
        record = new EcbuPcompany();
        record.setEcbupId(ecbuPcompany.getEcbupId());
        record.setStartType(startType);
        ecbuPcompanyService.update(record);
        CommonFunction.getCommonMap(map, status, code, msg);
        loadData();
        return map;
    }

    //load 加载用户包带数据为txt文档
    public void loadData() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        int ecCompanyId = ecUser.getEcCompanyId();
        EcbuPcompany record = new EcbuPcompany();
        record.setStartType(true);
        record.setEcCompanyId(ecCompanyId);
        log.info(CommonFunction.getGson().toJson(record));
        List<EcbuPcompany> list = ecbuPcompanyService.getList(record);
        List<String> txtList = new ArrayList<>();
        txtList.add(CommonFunction.getGson().toJson(list));
        ecdCollectModel.deal(ecCompanyId, 11, txtList);
    }

    /***===数据模型===***/
    //deal
    public void deal(EcbuPcompany record) {
        EcbuPcompany recordEcbuPcompany = new EcbuPcompany();
        recordEcbuPcompany.setEcCompanyId(record.getEcCompanyId());
        recordEcbuPcompany.setPlatformId(record.getPlatformId());
        recordEcbuPcompany.setPcName(record.getPcName());
        EcbuPcompany ecbuPcompany = ecbuPcompanyService.getObject(record);
        if (ecbuPcompany != null) {
            record.setEcbupId(ecbuPcompany.getEcbupId());
            ecbuPcompanyService.update(record);
        } else {
            ecbuPcompanyService.insert(record);
        }
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcbuPcompany record = new EcbuPcompany();
        record.setEcCompanyId(ecCompanyId);
        ecbuPcompanyService.delete(record);
    }

    //getObjectPassEcbupId
    public EcbuPcompany getObjectPassEcbupId(int ecbupId) {
        EcbuPcompany record = new EcbuPcompany();
        record.setEcbupId(ecbupId);
        return ecbuPcompanyService.getObject(record);
    }
}
