package org.jeecg.modules.online.cgform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;

public interface IOnlCgformButtonService extends IService<OnlCgformButton> {
    void saveButton(OnlCgformButton paramOnlCgformButton);
}
