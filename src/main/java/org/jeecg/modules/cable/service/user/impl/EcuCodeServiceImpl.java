package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.util.IpUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.cable.entity.user.EcuCode;
import org.jeecg.modules.cable.mapper.dao.user.EcuCodeMapper;
import org.jeecg.modules.cable.service.user.EcuCodeService;
import org.springframework.stereotype.Service;

@Service
public class EcuCodeServiceImpl implements EcuCodeService {
    @Resource
    private EcuCodeMapper ecuCodeMapper;


    @Override
    public void save(String phone, String code, Boolean success) {
        EcuCode ecuCode = new EcuCode();
        ecuCode.setSendPhone(phone);
        ecuCode.setCode(code);
        ecuCode.setSuccess(success);
        try {
            //获取request
            HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
            //设置IP地址
            ecuCode.setIp(IpUtils.getIpAddr(request));
        } catch (Exception e) {
            ecuCode.setIp("127.0.0.1");
        }
        ecuCodeMapper.insert(ecuCode);
    }
}
