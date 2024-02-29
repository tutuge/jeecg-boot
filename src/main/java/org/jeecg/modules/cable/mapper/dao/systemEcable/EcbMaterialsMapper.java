package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;

import java.util.List;

@Mapper
public interface EcbMaterialsMapper extends BaseMapper<EcbMaterials> {
    List<EcbMaterials> getList(EcbMaterials record);// 获取数据列表

    long getCount();// 获取总数

    EcbMaterials getObject(EcbMaterials record);

    EcbMaterials getSysObject(EcbMaterials record);

    List<EcbMaterials> getSysList(EcbMaterials record);

    long getSysCount(EcbMaterials record);
}
