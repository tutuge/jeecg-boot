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

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
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
        Map<String, Object> map = new HashMap<>();
        record.setEcCompanyId(ecUser.getEcCompanyId());

        List<EcUser> list = ecUserService.getList(record);
        long count = ecUserService.getCount(record);
        map.put("list", list);
        map.put("count", count);

        return map;
    }

    //deal
    public String deal(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

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
        String msg;
        if (ecUserUsername != null) {//用户名已占用
            throw new RuntimeException("用户名已占用");
        } else if (ecUserPhone != null) {//手机号已占用
            throw new RuntimeException("手机号已占用");
        } else if (ecUserCode != null) {//员工代号已占用
            throw new RuntimeException("员工代号已占用");
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
                msg = "更新数据成功";
            }
        }
        return msg;
    }

    //dealMine
    public void dealMine(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        String ecUsername = request.getParameter("ecUsername");
        String ecPassword = CommonFunction.getMd5Str(CommonFunction.getMd5Str(request.getParameter("ecPassword")));
        EcUser record = new EcUser();
        record.setEcuId(ecuId);
        record.setEcCompanyId(ecUser.getEcCompanyId());
        record.setEcUsername(ecUsername);
        record.setEcPwd(ecPassword);
        EcUser ecUserUsername = ecUserService.getObjectPassEcUsername(record);
        if (ecUserUsername != null) {
            throw new RuntimeException("用户名已占用");
        } else {
            ecUserService.update(record);
        }
    }

    //dealRegister
    public String dealRegister(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String msg = "";
        String ecPhone = request.getParameter("ecPhone");
        String codeSend = request.getParameter("code");
        String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
        String companyName = request.getParameter("companyName");
        EcUser ecUser = getObjectPassEcPhone(ecPhone);
        if (ecUser != null) {
            throw new RuntimeException("手机号已占用");
        } else {
            //判断验证码是否正确
            EcuCode recordEcuCode = new EcuCode();
            recordEcuCode.setSendPhone(ecPhone);
            recordEcuCode.setCode(codeSendMd5);
            EcuCode ecuCode = ecuCodeService.getObject(recordEcuCode);
            if (ecuCode == null) {
                throw new RuntimeException("手机验证码错误");
            } else {
                //先创建公司 再创建用户
                ecCompanyModel.deal(request);
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
                    msg = "注册成功";
                }
            }
        }
        return msg;
    }

    //dealLoginCode 验证码登录
    public Map<String, Object> dealLoginCode(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        String ecPhone = request.getParameter("ecPhone");
        String codeSend = request.getParameter("code");
        String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
        EcUser ecUser = getObjectPassEcPhone(ecPhone);
        if (ecUser == null) {
            throw new RuntimeException("手机号不存在");
        } else {
            //判断验证码是否正确
            EcuCode recordEcuCode = new EcuCode();
            recordEcuCode.setSendPhone(ecPhone);
            recordEcuCode.setCode(codeSendMd5);
            EcuCode ecuCode = ecuCodeService.getObject(recordEcuCode);
            if (ecuCode == null) {
                throw new RuntimeException("手机验证码错误");
            } else {
                EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcCompanyId(ecUser.getEcCompanyId());
                if (ecbuConductor == null) {
                    throw new RuntimeException("没有初始化数据");
                } else {
                    EcuLogin recordEcuLogin = new EcuLogin();
                    recordEcuLogin.setEcuId(ecUser.getEcuId());
                    EcuLogin ecuLogin = ecuLoginService.getObject(recordEcuLogin);
                    map.put("ecUser", ecUser);
                    map.put("ecuLogin", ecuLogin);
                }
            }
        }

        return map;
    }

    //dealProfit
    public void dealProfit(HttpServletRequest request) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        BigDecimal profit = new BigDecimal(request.getParameter("profit"));
        EcUser record = new EcUser();
        record.setEcuId(ecuId);
        record.setProfit(profit);
        ecUserService.update(record);

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
