package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;

import java.util.List;

@Mapper
public interface EcbMaterialTypeMapper extends BaseMapper<EcbMaterialType> {


    EcbMaterialType getSysObject(EcbMaterialType record);

    List<EcbMaterialType> getSysList(EcbMaterialType record);

    long getSysCount(EcbMaterialType record);


    //    -----------------下面是用户相关------------------------
    List<EcbMaterialType> getList(EcbMaterialType record);// 获取数据列表

    List<EcbMaterialType> getListStart(EcbMaterialType record);

    long getCount();// 获取总数

    void reduceSort(@Param("sortId") Integer sortId);
}
