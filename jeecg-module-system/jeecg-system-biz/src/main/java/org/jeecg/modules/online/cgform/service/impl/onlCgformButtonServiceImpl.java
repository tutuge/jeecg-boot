package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.mapper.OnlCgformButtonMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.springframework.stereotype.Service;

@Service("onlCgformButtonServiceImpl")
public class onlCgformButtonServiceImpl extends ServiceImpl<OnlCgformButtonMapper, OnlCgformButton> implements IOnlCgformButtonService {
    public void saveButton(OnlCgformButton onlCgformButton) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformButton.class)
                .eq(OnlCgformButton::getButtonCode, onlCgformButton.getButtonCode())
                .eq(OnlCgformButton::getCgformHeadId, onlCgformButton.getCgformHeadId());
        Long long_ = this.baseMapper.selectCount(lambdaQueryWrapper);
        if (long_ == null || long_ == 0L)
            save(onlCgformButton);
    }
}
