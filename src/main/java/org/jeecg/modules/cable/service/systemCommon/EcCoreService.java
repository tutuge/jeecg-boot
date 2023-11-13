package org.jeecg.modules.cable.service.systemCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemCommon.EcCore;

public interface EcCoreService extends IService<EcCore> {
    EcCore getByCore(String coreStr);
}
