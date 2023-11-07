package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;

import java.util.List;

public interface EcbuMicaTapeService extends IService<EcbuMicaTape> {
    EcbuMicaTape getObject(EcbuMicaTape record);

    Integer insert(EcbuMicaTape record);

    Integer update(EcbuMicaTape record);

    List<EcbuMicaTape> getList(EcbuMicaTape record);

    Integer delete(EcbuMicaTape record);
}
