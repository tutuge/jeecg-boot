package org.jeecg.modules.cable.model.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.entity.user.EcuCode;
import org.jeecg.modules.cable.service.user.EcuCodeService;
import org.jeecg.modules.cable.tools.CommonFunction;
import org.jeecg.modules.cable.tools.MessageUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EcuCodeModel {
    @Resource
    EcuCodeService ecuCodeService;
    @Resource
    EcUserModel ecUserModel;//用户

    //dealRegister
    public Map<String, Object> dealRegister(HttpServletRequest request) {

        String ecPhone = request.getParameter("ecPhone");
        EcUser ecUser = ecUserModel.getObjectPassEcPhone(ecPhone);
        if (ecUser != null) {
            status = 3;//手机号已占用
            code = "103";
            msg = "手机号已占用";
        } else {
            String codeSend = String.valueOf(CommonFunction.getRandom(1000, 9999));
            log.info("ecPhone + " + ecPhone);
            log.info("codeSend + " + codeSend);
            String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
            EcuCode record = new EcuCode();
            record.setSendPhone(ecPhone);
            EcuCode ecuCode = ecuCodeService.getObject(record);
            record.setCode(codeSendMd5);
            record.setIp(0L);
            record.setIpAddress("");
            record.setAddTime(System.currentTimeMillis());
            if (ecuCode == null) {//插入
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuCodeService.insert(record);
            } else {//更新
                ecuCodeService.update(record);
            }
            MessageUtils.registerEcUserSendCode(ecPhone, codeSend);
            status = 4;//操作数据成功
            code = "200";
            msg = "操作数据成功";
        }

    }

    //dealLogin 登录时发送验证码
    public Map<String, Object> dealLogin(HttpServletRequest request) {

        String ecPhone = request.getParameter("ecPhone");
        EcUser ecUser = ecUserModel.getObjectPassEcPhone(ecPhone);
        if (ecUser == null) {
            status = 3;//账号不存在
            code = "103";
            msg = "账号不存在";
        } else {
            String codeSend = String.valueOf(CommonFunction.getRandom(1000, 9999));
            log.info("ecPhone + " + ecPhone);
            log.info("codeSend + " + codeSend);
            String codeSendMd5 = CommonFunction.getMd5Str(CommonFunction.getMd5Str(codeSend));
            EcuCode record = new EcuCode();
            record.setSendPhone(ecPhone);
            EcuCode ecuCode = ecuCodeService.getObject(record);
            record.setCode(codeSendMd5);
            record.setIp(0L);
            record.setIpAddress("");
            record.setAddTime(System.currentTimeMillis());
            if (ecuCode == null) {//插入
                log.info("record + " + CommonFunction.getGson().toJson(record));
                ecuCodeService.insert(record);
            } else {//更新
                ecuCodeService.update(record);
            }
            MessageUtils.loginEcUserSendCode(ecPhone, codeSend);
            status = 4;//操作数据成功
            code = "200";
            msg = "操作数据成功";
        }

    }
}
