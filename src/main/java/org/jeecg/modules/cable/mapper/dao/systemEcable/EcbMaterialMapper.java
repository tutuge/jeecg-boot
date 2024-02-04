package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterial;

import java.util.List;

@Mapper
public interface EcbMaterialMapper extends BaseMapper<EcbMaterial> {


    EcbMaterial getSysObject(EcbMaterial record);

    List<EcbMaterial> getSysList(EcbMaterial record);

    long getSysCount(EcbMaterial record);


    //    -----------------下面是用户相关------------------------
    List<EcbMaterial> getList(EcbMaterial record);// 获取数据列表

    List<EcbMaterial> getListStart(EcbMaterial record);

    long getCount();// 获取总数

    void reduceSort(@Param("sortId") Integer sortId);
}
