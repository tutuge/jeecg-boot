package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;

import java.util.List;

@Mapper
public interface EcbuMaterialsMapper extends BaseMapper<EcbuMaterials> {
    List<EcbuMaterials> getList(EcbuMaterials record);// 获取数据列表

    List<EcbuMaterials> getListStart(EcbuMaterials record);// 获取启用列表

    long getCount();// 获取总数

    EcbuMaterials getObject(EcbuMaterials record);

    EcbuMaterials getSysObject(EcbuMaterials record);

    List<EcbuMaterials> getSysList(EcbuMaterials record);

    long getSysCount(EcbuMaterials record);

}
