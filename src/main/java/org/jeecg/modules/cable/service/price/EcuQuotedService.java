package org.jeecg.modules.cable.service.price;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.util.List;

public interface EcuQuotedService extends IService<EcuQuoted> {

    List<EcuQuoted> getList(EcuQuoted record);


    Long getCount(EcuQuoted record);

    Long selectCount(EcuQuoted record);


    EcuQuoted getObject(EcuQuoted record);


    EcuQuoted getLatestObject(EcuQuoted record);


    Integer insert(EcuQuoted record);


    Integer deleteByPrimaryKey(Integer ecuqId);

    Integer update(EcuQuoted record);
}
