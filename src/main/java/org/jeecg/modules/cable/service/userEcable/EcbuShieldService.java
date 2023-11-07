package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;

import java.util.List;

public interface EcbuShieldService extends IService<EcbuShield> {
    EcbuShield getObject(EcbuShield record);

    Integer insert(EcbuShield record);

    Integer update(EcbuShield record);

    List<EcbuShield> getList(EcbuShield record);

    Integer delete(EcbuShield record);
}
