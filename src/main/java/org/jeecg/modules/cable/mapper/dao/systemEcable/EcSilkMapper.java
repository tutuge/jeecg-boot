package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

@Mapper
public interface EcSilkMapper extends BaseMapper<EcSilk> {
    List<EcSilk> getList(EcSilk record);// 获取数据列表


    EcSilk getObject(EcSilk record);// 通过EcSilk获取EcSilk

    IPage<EcSilk> select(Page<EcSilk> page, @Param("ecSilk") EcSilk ecSilk);
}
