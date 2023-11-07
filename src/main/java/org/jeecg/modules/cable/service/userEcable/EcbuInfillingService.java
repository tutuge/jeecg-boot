package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;

import java.util.List;

public interface EcbuInfillingService extends IService<EcbuInfilling> {
    EcbuInfilling getObject(EcbuInfilling record);

    Integer insert(EcbuInfilling record);

    Integer update(EcbuInfilling record);

    List<EcbuInfilling> getList(EcbuInfilling record);

    Integer delete(EcbuInfilling record);
}
