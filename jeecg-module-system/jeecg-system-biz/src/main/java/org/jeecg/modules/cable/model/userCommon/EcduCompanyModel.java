package org.jeecg.modules.cable.model.userCommon;

import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.userCommon.EcduCompanyService;
import org.jeecg.modules.cable.service.userCommon.EctImagesService;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcduCompanyModel {
    @Resource
    EcduCompanyService ecduCompanyService;
    @Resource
    EcUserService ecUserService;//用户
    @Resource
    EctImagesService ectImagesService;
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcUserModel ecUserModel;

    //getListAndCount
    public Map<String, Object> getListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcduCompany record = new EcduCompany();
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
        List<EcduCompany> list = ecduCompanyService.getList(record);
        for (EcduCompany ecduCompany : list) {
            if (!"".equals(ecduCompany.getLogoImg())) {
                ecduCompany.setLogoImg("http://101.42.164.66:8001/home/" + ecduCompany.getLogoImg());
            }
            if (!"".equals(ecduCompany.getSealImg())) {
                ecduCompany.setSealImg("http://101.42.164.66:8001/home/" + ecduCompany.getSealImg());
            }
        }
        long count = ecduCompanyService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        EcduCompany record = new EcduCompany();
        if (request.getParameter("ecducId") != null) {
            int ecducId = Integer.parseInt(request.getParameter("ecducId"));
            record.setEcducId(ecducId);
        }
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        if (!"".equals(ecduCompany.getLogoImg())) {
            ecduCompany.setLogoImg("http://101.42.164.66:8001/home/" + ecduCompany.getLogoImg());
        }
        if (!"".equals(ecduCompany.getSealImg())) {
            ecduCompany.setSealImg("http://101.42.164.66:8001/home/" + ecduCompany.getSealImg());
        }
        map.put("object", ecduCompany);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //getObject
    public Map<String, Object> getObjectDefault(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcduCompany record = new EcduCompany();
        record.setDefaultType(true);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        if (!"".equals(ecduCompany.getLogoImg())) {
            ecduCompany.setLogoImg("http://101.42.164.66:8001/home/" + ecduCompany.getLogoImg());
        }
        if (!"".equals(ecduCompany.getSealImg())) {
            ecduCompany.setSealImg("http://101.42.164.66:8001/home/" + ecduCompany.getSealImg());
        }
        map.put("object", ecduCompany);
        status = 3;//正常获取数据
        code = "200";
        msg = "正常获取数据";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecducId = Integer.parseInt(request.getParameter("ecducId"));
        String abbreviation = request.getParameter("abbreviation");//简称
        String fullName = request.getParameter("fullName");//全称
        int billPercentType = Integer.parseInt(request.getParameter("billPercentType"));
        String description = request.getParameter("description");
        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setAbbreviation(abbreviation);
        record.setFullName(fullName);
        EcduCompany ecduCompany = ecduCompanyService.getObjectPassAbbreviationAndFullName(record);
        if (ecduCompany != null) {
            status = 3;//简称或者全称已占用
            code = "103";
            msg = "简称或者全称已占用";
        } else {
            EctImages ectImages;
            EctImages recordImages = new EctImages();
            String logoImg = "";
            String sealImg = "";
            long targetTime = System.currentTimeMillis() - 10 * 60 * 1000L;//取10分钟以内的图片
            recordImages.setTypeId(1);
            recordImages.setEcuId(ecuId);
            recordImages.setAddTime(targetTime);
            ectImages = ectImagesService.getObject(recordImages);
            if (ecducId == 0) {//插入
                if (ectImages != null) {
                    logoImg = ectImages.getImageUrl();
                }
                recordImages.setTypeId(2);
                ectImages = ectImagesService.getObject(recordImages);
                if (ectImages != null) {
                    sealImg = ectImages.getImageUrl();
                }
                int sortId = 1;
                ecduCompany = ecduCompanyService.getLatestObject(record);
                if (ecduCompany != null) {
                    sortId = ecduCompany.getSortId() + 1;
                }
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setStartType(true);
                record.setSortId(sortId);
                record.setDefaultType(false);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setLogoImg(logoImg);
                record.setSealImg(sealImg);
                record.setBillPercentType(billPercentType);
                record.setDescription(description);
                //System.out.println(CommonFunction.getGson().toJson(record));
                ecduCompanyService.insert(record);
                status = 4;//正常插入数据
                code = "200";
                msg = "正常插入数据";
            } else {
                if (ectImages != null) {
                    logoImg = ectImages.getImageUrl();
                    record.setLogoImg(logoImg);
                }
                recordImages.setTypeId(2);
                ectImages = ectImagesService.getObject(recordImages);
                log.info("ectImages + " + CommonFunction.getGson().toJson(ectImages));
                if (ectImages != null) {
                    sealImg = ectImages.getImageUrl();
                    log.info("sealImg + " + sealImg);
                    record.setSealImg(sealImg);
                }
                record.setEcducId(ecducId);
                record.setAbbreviation(abbreviation);
                record.setFullName(fullName);
                record.setBillPercentType(billPercentType);
                record.setDescription(description);
                //System.out.println(CommonFunction.getGson().toJson(record));
                ecduCompanyService.update(record);
                status = 5;//正常更新数据
                code = "201";
                msg = "正常更新数据";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //sort
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecducId = Integer.parseInt(request.getParameter("ecducId"));
        int sortId = Integer.parseInt(request.getParameter("sortId"));
        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        record.setSortId(sortId);
        ecduCompanyService.update(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //delete
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecducId = Integer.parseInt(request.getParameter("ecducId"));
        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        int sortId = ecduCompany.getSortId();
        record = new EcduCompany();
        record.setSortId(sortId);
        record.setEcCompanyId(ecduCompany.getEcCompanyId());
        List<EcduCompany> list = ecduCompanyService.getListGreaterThanSortId(record);
        int ecduc_id;
        for (EcduCompany ecbuCompany : list) {
            ecduc_id = ecbuCompany.getEcducId();
            sortId = ecbuCompany.getSortId() - 1;
            record.setEcducId(ecduc_id);
            record.setSortId(sortId);
            ecduCompanyService.update(record);
        }
        record = new EcduCompany();
        record.setEcducId(ecducId);
        ecduCompanyService.delete(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //start
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status;
        String code;
        String msg;
        int ecducId = Integer.parseInt(request.getParameter("ecducId"));
        EcduCompany record = new EcduCompany();
        record.setEcducId(ecducId);
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        boolean startType = ecduCompany.getStartType();
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
        record = new EcduCompany();
        record.setEcducId(ecduCompany.getEcducId());
        record.setStartType(startType);
        ecduCompanyService.update(record);
        CommonFunction.getCommonMap(map, status, code, msg);
        return map;
    }

    //dealDefault
    public Map<String, Object> dealDefault(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            EcUser ecUser = ecUserModel.getObjectPassEcuId(ecuId);
            int ecducId = Integer.parseInt(request.getParameter("ecducId"));
            EcduCompany record = new EcduCompany();
            record.setEcCompanyId(ecUser.getEcCompanyId());
            record.setDefaultType(false);
            ecduCompanyService.update(record);
            record.setEcducId(ecducId);
            record.setDefaultType(true);
            ecduCompanyService.update(record);
            status = 3;
            code = "200";
            msg = "数据操作成功";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    /***===数据模型===***/
    //deal
    public void deal(EcduCompany record) {
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(record.getEcCompanyId());
        recordEcduCompany.setAbbreviation(record.getAbbreviation());
        recordEcduCompany.setFullName(record.getFullName());
        EcduCompany ecduCompany = ecduCompanyService.getObject(record);
        if (ecduCompany != null) {
            ecduCompanyService.update(record);
        } else {
            ecduCompanyService.insert(record);
        }
    }

    //deletePassEcCompanyId
    public void deletePassEcCompanyId(int ecCompanyId) {
        EcduCompany recordEcduCompany = new EcduCompany();
        recordEcduCompany.setEcCompanyId(ecCompanyId);
        ecduCompanyService.delete(recordEcduCompany);
    }
}
