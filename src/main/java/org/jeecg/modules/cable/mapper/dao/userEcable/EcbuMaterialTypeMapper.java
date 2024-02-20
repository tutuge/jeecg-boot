package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;

import java.util.List;

@Mapper
public interface EcbuMaterialTypeMapper extends BaseMapper<EcbuMaterialType> {

    EcbuMaterialType getObject(EcbuMaterialType record);

    List<EcbuMaterialType> getSysList(EcbuMaterialType record);

    long getSysCount(EcbuMaterialType record);


    //    -----------------下面是用户相关------------------------
    List<EcbuMaterialType> getList(EcbuMaterialType record);// 获取数据列表

    List<EcbuMaterialType> getListStart(EcbuMaterialType record);

    long getCount();// 获取总数

    void reduceSort(@Param("sortId") Integer sortId);

    void deleteByEcCompanyId(Integer ecCompanyId);
}
