package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;

import java.util.List;

public interface EcbuInsulationService extends IService<EcbuInsulation> {
    EcbuInsulation getObject(EcbuInsulation record);

    Integer insert(EcbuInsulation record);

    Integer update(EcbuInsulation record);

    List<EcbuInsulation> getList(EcbuInsulation record);

    Integer delete(EcbuInsulation record);
}
