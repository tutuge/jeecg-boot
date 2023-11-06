package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.userEcable.EcuSilk;

import java.util.List;

@Mapper
public interface EcuSilkMapper extends BaseMapper<EcuSilk> {

    List<EcuSilk> getList(EcuSilk record);

    EcuSilk getObject(EcuSilk record);

    IPage<EcuSilk> select(Page<EcuSilk> page,@Param("ecuSilk") EcuSilk ecuSilk);
}
