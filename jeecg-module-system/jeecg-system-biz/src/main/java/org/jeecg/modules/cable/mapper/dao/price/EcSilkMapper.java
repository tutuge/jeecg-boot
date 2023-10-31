package org.jeecg.modules.cable.mapper.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.controller.systemEcable.silk.vo.SilkVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

@Mapper
public interface EcSilkMapper extends BaseMapper<EcSilk> {
    List<EcSilk> getList(EcSilk record);// 获取数据列表

    // getObject
    EcSilk getObject(EcSilk record);// 通过EcSilk获取EcSilk

    IPage<SilkVo> select(Page<EcSilk> page, EcSilk ecSilk);
}
