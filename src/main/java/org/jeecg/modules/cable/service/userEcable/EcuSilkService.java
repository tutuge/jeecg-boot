package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;

import java.util.List;

public interface EcuSilkService  {

    List<EcuSilk> getList(EcuSilk record);


    EcuSilk getObject(EcuSilk record);

    IPage<EcuSilk> selectPage(Page<EcuSilk> page, EcuSilk ecSilk);

    List<EcuSilk> getListByCompanyId(Integer ecCompanyId, Boolean startType);

    void insert(EcuSilk ecuSilk);

    void updateById(EcuSilk record);

    List<EcuSilk> list(EcuSilk ecuSilk);

    void save(EcuSilk ecuSilk);

    void removeById(EcuSilk record);
}
