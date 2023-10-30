package org.jeecg.modules.cable.model.user;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.user.bo.*;
import org.jeecg.modules.cable.controller.user.user.vo.EcuUserRegisterVo;
import org.jeecg.modules.cable.controller.user.user.vo.UserVo;
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
import org.springframework.transaction.annotation.Transactional;

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
    public UserVo getList(EcuUserListBo bo) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        EcUser record = new EcUser();
        record.setStartType(bo.getStartType());
        record.setEcCompanyId(ecUser.getEcCompanyId());
        List<EcUser> list = ecUserService.getList(record);
        long count = ecUserService.getCount(record);
        return new UserVo(list, count);
    }

       // deal 
@Transactional(rollbackFor = Exception.class)  
          public String deal(EcuUserDealBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();

        Integer ecu_id = bo.getEcuId();
        Integer typeId = bo.getTypeId();
        String ecUsername = bo.getEcUsername();
        String codeStr = bo.getCode();
        String ecPhone = bo.getEcPhone();

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
            if (ObjectUtil.isNull(ecu_id)) {//插入
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
                if (bo.getProfit() != null) {
                    record.setProfit(bo.getProfit());
                }
                record.setEcPhone(ecPhone);
                ecUserService.update(record);
                msg = "更新数据成功";
            }
        }
        return msg;
    }

    //dealMine
    public void dealMine(EcuUserDealMineBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        String ecUsername = bo.getEcUsername();
        String ecPassword = CommonFunction.getMd5Str(CommonFunction.getMd5Str(bo.getEcPassword()));
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
    @Transactional(rollbackFor = Exception.class)
    public EcuUserRegisterVo dealRegister(EcuUserRegisterBo bo) {
        String ecPhone = bo.getEcPhone();
        String companyName = bo.getCompanyName();
        EcUser ecUser = getObjectPassEcPhone(ecPhone);
        if (ecUser != null) {
            throw new RuntimeException("手机号已占用");
        }
        //判断验证码是否正确
        EcuCode recordEcuCode = new EcuCode();
        recordEcuCode.setSendPhone(ecPhone);
        String codeSend = bo.getCode();
        String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
        recordEcuCode.setCode(codeSendMd5);
        EcuCode ecuCode = ecuCodeService.getObject(recordEcuCode);
        if (ecuCode == null) {
            throw new RuntimeException("手机验证码错误");
        }
        //先创建公司
        ecCompanyModel.deal(bo);
        EcCompany ecCompany = ecCompanyModel.getObjectPassCompanyName(ecPhone, companyName);
        String ecPwd = CommonFunction.getMd5Str(CommonFunction.getMd5Str("123456"));
        // 再创建用户
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
        return new EcuUserRegisterVo(ecUser, ecuLogin);
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
    public void dealProfit(EcuUserProfitBo bo) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        EcUser ecUser = sysUser.getEcUser();
        Integer ecuId = ecUser.getEcuId();
        BigDecimal profit = bo.getProfit();
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
    public EcUser getObjectPassEcuId(Integer ecuId) {
        EcUser record = new EcUser();
        record.setEcuId(ecuId);
        return ecUserService.getObject(record);
    }
}
