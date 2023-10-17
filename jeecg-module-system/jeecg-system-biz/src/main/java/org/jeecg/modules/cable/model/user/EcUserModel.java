package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.entity.user.EcuCode;
import org.jeecg.modules.cable.entity.user.EcuLogin;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
import org.jeecg.modules.cable.model.userEcable.EcbuConductorModel;
import org.jeecg.modules.cable.service.user.EcUserService;
import org.jeecg.modules.cable.service.user.EcuCodeService;
import org.jeecg.modules.cable.service.user.EcuLoginService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EcUserModel {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcUserService ecUserService;
    @Resource
    EcCompanyModel ecCompanyModel;
    @Resource
    EcuCodeService ecuCodeService;
    @Resource
    EcuLoginService ecuLoginService;
    @Resource
    EcbuConductorModel ecbuConductorModel;

    //getObject
    public EcUser getObject() {
        EcUser record = new EcUser();
        //获取当前用户id
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        record.setEcuId(ecuId);
        return ecUserService.getObject(record);
    }

    //getList
    public Map<String, Object> getList(HttpServletRequest request) {

        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        EcUser record = new EcUser();
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
        System.out.println(CommonFunction.getGson().toJson(record));
        List<EcUser> list = ecUserService.getList(record);
        long count = ecUserService.getCount(record);
        map.put("list", list);
        map.put("count", count);
        status = 3;//正常获取用户信息
        code = "200";
        msg = "正常获取用户信息";
        CommonFunction.getCommonMap(map, status, code, msg);

        return map;
    }

    //deal
    public Map<String, Object> deal(HttpServletRequest request) {

        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        int ecu_id = Integer.parseInt(request.getParameter("ecu_id"));
        int typeId = Integer.parseInt(request.getParameter("typeId"));
        String ecUsername = request.getParameter("ecUsername");
        String codeStr = request.getParameter("code");
        String ecPhone = request.getParameter("ecPhone");
        String ecPwd = CommonFunction.getMd5Str(CommonFunction.getMd5Str("123456"));
        EcUser record = new EcUser();
        record.setEcuId(ecu_id);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcUsername(ecUsername);
        record.setEcPhone(ecPhone);
        record.setCode(codeStr);
        EcUser ecUserUsername = ecUserService.getObjectPassEcUsername(record);
        EcUser ecUserPhone = ecUserService.getObjectPassEcPhone(record);
        EcUser ecUserCode = ecUserService.getObjectPassCode(record);
        if (ecUserUsername != null) {//用户名已占用
            status = 3;//用户名已占用
            code = "103";
            msg = "用户名已占用";
        } else if (ecUserPhone != null) {//手机号已占用
            status = 4;//用户名已占用
            code = "104";
            msg = "手机号已占用";
        } else if (ecUserCode != null) {//员工代号已占用
            status = 5;//员工代号已占用
            code = "105";
            msg = "员工代号已占用";
        } else {
            if (ecu_id == 0) {//插入
                record.setEcCompanyId(ecUser.getEcCompanyId());
                record.setTypeId(typeId);
                record.setStartType(true);
                record.setEcUsername(ecUsername);
                record.setCode(codeStr);
                record.setEcPhone(ecPhone);
                record.setEcPwd(ecPwd);
                record.setEcHeadimg("");
                record.setSex(0);
                record.setIntroduction("");
                record.setProfit(new BigDecimal("0"));
                record.setAddTime(System.currentTimeMillis());
                ecUserService.insert(record);
                status = 6;//插入数据成功
                code = "200";
                msg = "插入数据成功";
            } else {
                record.setEcuId(ecu_id);
                record.setTypeId(typeId);
                record.setEcUsername(ecUsername);
                record.setCode(codeStr);
                if (request.getParameter("profit") != null) {
                    record.setProfit(new BigDecimal(request.getParameter("profit")));
                }
                record.setEcPhone(ecPhone);
                ecUserService.update(record);
                status = 7;//更新数据成功
                code = "201";
                msg = "更新数据成功";
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);

        return map;
    }

    //dealMine
    public Map<String, Object> dealMine(HttpServletRequest request) {

        EcUser recordEcUser = new EcUser();
        recordEcUser.setEcuId(ecuId);
        EcUser ecUser = ecUserService.getObject(recordEcUser);
        String ecUsername = request.getParameter("ecUsername");
        String ecPassword = CommonFunction.getMd5Str(CommonFunction.getMd5Str(request.getParameter("ecPassword")));
        EcUser record = new EcUser();
        record.setEcuId(ecuId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcUsername(ecUsername);
        record.setEcPwd(ecPassword);
        EcUser ecUserUsername = ecUserService.getObjectPassEcUsername(record);
        if (ecUserUsername != null) {
            status = 3;//用户名已占用
            code = "103";
            msg = "用户名已占用";
        } else {
            ecUserService.update(record);
            status = 4;//正常获取用户信息
            code = "200";
            msg = "操作数据成功";
        }
        CommonFunction.getCommonMap(map, status, code, msg);

        return map;
    }

    //dealRegister
    public Map<String, Object> dealRegister(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int status = 0;
        String code = "";
        String msg = "";
        String ecPhone = request.getParameter("ecPhone");
        String codeSend = request.getParameter("code");
        String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
        String companyName = request.getParameter("companyName");
        EcUser ecUser = getObjectPassEcPhone(ecPhone);
        if (ecUser != null) {
            status = 3;//手机号已占用
            code = "103";
            msg = "手机号已占用";
        } else {
            //判断验证码是否正确
            EcuCode recordEcuCode = new EcuCode();
            recordEcuCode.setSendPhone(ecPhone);
            recordEcuCode.setCode(codeSendMd5);
            EcuCode ecuCode = ecuCodeService.getObject(recordEcuCode);
            if (ecuCode == null) {
                status = 4;//手机验证码错误
                code = "104";
                msg = "手机验证码错误";
            } else {
                //先创建公司 再创建用户
                map = ecCompanyModel.deal(request);
                log.info(CommonFunction.getGson().toJson(map));
                if ("6".equals(map.get("status").toString())) {
                    EcCompany ecCompany = ecCompanyModel.getObjectPassCompanyName(ecPhone, companyName);
                    String ecPwd = CommonFunction.getMd5Str(CommonFunction.getMd5Str("123456"));
                    EcUser record = new EcUser();
                    record.setEcCompanyId(ecCompany.getEcCompanyId());
                    record.setTypeId(0);
                    record.setStartType(true);
                    record.setEcUsername(ecPhone);
                    record.setCode("");
                    record.setEcPhone(ecPhone);
                    record.setEcPwd(ecPwd);
                    record.setEcHeadimg("");
                    record.setSex(0);
                    record.setIntroduction("");
                    record.setAddTime(System.currentTimeMillis());
                    ecUserService.insert(record);
                    ecUser = getObjectPassEcPhone(ecPhone);
                    String tokenStr = CommonFunction.getMd5Str(String.valueOf(CommonFunction.getRandom(1, 999999)));
                    EcuLogin ecuLogin = new EcuLogin();
                    ecuLogin.setEcuId(ecUser.getEcuId());//用户ID
                    ecuLogin.setClientType(1);//PC端
                    ecuLogin.setToken(tokenStr);
                    ecuLogin.setPhoneStr("");//手机信息为空
                    ecuLogin.setEffectTime(System.currentTimeMillis());
                    ecuLoginService.insert(ecuLogin);
                    EcuLogin recordEcuLogin = new EcuLogin();
                    recordEcuLogin.setEcuId(ecUser.getEcuId());
                    ecuLogin = ecuLoginService.getObject(recordEcuLogin);
                    map.put("ecUser", ecUser);
                    map.put("ecuLogin", ecuLogin);
                    status = 7;//注册成功
                    code = "200";
                    msg = "注册成功";
                }
            }
        }

    }

    //dealLoginCode 验证码登录
    public Map<String, Object> dealLoginCode(HttpServletRequest request) {

        String ecPhone = request.getParameter("ecPhone");
        String codeSend = request.getParameter("code");
        String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
        EcUser ecUser = getObjectPassEcPhone(ecPhone);
        if (ecUser == null) {
            status = 3;//手机号不存在
            code = "103";
            msg = "手机号不存在";
        } else {
            //判断验证码是否正确
            EcuCode recordEcuCode = new EcuCode();
            recordEcuCode.setSendPhone(ecPhone);
            recordEcuCode.setCode(codeSendMd5);
            EcuCode ecuCode = ecuCodeService.getObject(recordEcuCode);
            if (ecuCode == null) {
                status = 4;//手机验证码错误
                code = "104";
                msg = "手机验证码错误";
            } else {
                EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcCompanyId(ecUser.getEcCompanyId());
                if (ecbuConductor == null) {
                    status = 5;//没有初始化数据
                    code = "105";
                    msg = "没有初始化数据";
                    map.put("ecUser", ecUser);
                } else {
                    EcuLogin recordEcuLogin = new EcuLogin();
                    recordEcuLogin.setEcuId(ecUser.getEcuId());
                    EcuLogin ecuLogin = ecuLoginService.getObject(recordEcuLogin);
                    status = 6;//登录成功
                    code = "200";
                    msg = "登录成功";
                    map.put("ecUser", ecUser);
                    map.put("ecuLogin", ecuLogin);
                }
            }
        }
        CommonFunction.getCommonMap(map, status, code, msg);

        return map;
    }

    //dealProfit
    public Map<String, Object> dealProfit(HttpServletRequest request) {

        int ecu_id = Integer.parseInt(request.getParameter("ecuId"));
        BigDecimal profit = new BigDecimal(request.getParameter("profit"));
        EcUser record = new EcUser();
        record.setEcuId(ecu_id);
        record.setProfit(profit);
        ecUserService.update(record);
        status = 3;//数据操作成功
        code = "200";
        msg = "数据操作成功";
        CommonFunction.getCommonMap(map, status, code, msg);
    }
        return map;
}


    /***===数据模型===***/
//getObjectPassEcPhone
    public EcUser getObjectPassEcPhone(String ecPhone) {
        EcUser record = new EcUser();
        record.setEcPhone(ecPhone);
        return ecUserService.getObject(record);
    }

    //getObjectPassEcuId
    public EcUser getObjectPassEcuId(int ecuId) {
        EcUser record = new EcUser();
        record.setEcuId(ecuId);
        return ecUserService.getObject(record);
    }
}
