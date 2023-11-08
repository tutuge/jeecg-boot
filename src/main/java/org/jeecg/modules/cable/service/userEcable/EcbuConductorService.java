package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

import java.util.List;

public interface EcbuConductorService extends IService<EcbuConductor> {
    EcbuConductor getObject(EcbuConductor record);

    Integer insert(EcbuConductor record);

    Integer update(EcbuConductor record);

    List<EcbuConductor> getList(EcbuConductor record);

    Integer delete(EcbuConductor record);
}
