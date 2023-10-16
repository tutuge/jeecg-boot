package org.jeecg.modules.online.cgreport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.mapper.OnlCgreportParamMapper;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.springframework.stereotype.Service;

@Service("onlCgreportParamServiceImpl")
public class OnlCgreportParamServiceImpl extends ServiceImpl<OnlCgreportParamMapper, OnlCgreportParam> implements IOnlCgreportParamService {
}
