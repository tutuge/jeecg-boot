package org.jeecg.modules.cable.service.systemEcable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;
import java.util.Map;

public interface EcSilkService  {
    List<EcSilk> getList(EcSilk record);


    EcSilk getObject(EcSilk record);


    IPage<EcSilk> selectPage(Page<EcSilk> page, EcSilk ecSilk);

    Map<String, Integer> silkModelMap();

    void save(EcSilk ecSilk);

    void updateById(EcSilk ec);

    void removeById(Integer ecsId);

    EcSilk getObjectById(Integer ecsId);
}
