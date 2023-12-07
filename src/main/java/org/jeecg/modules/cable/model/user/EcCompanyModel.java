package org.jeecg.modules.cable.model.user;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.user.user.bo.EcuUserRegisterBo;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.jeecg.modules.cable.service.user.EcCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
public class EcCompanyModel {
    @Resource
    EcCompanyService ecCompanyService;



}
