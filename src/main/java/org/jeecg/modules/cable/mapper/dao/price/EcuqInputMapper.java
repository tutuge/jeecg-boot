package org.jeecg.modules.cable.mapper.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.price.EcuqInput;

import java.util.List;

@Mapper
public interface EcuqInputMapper extends BaseMapper<EcuqInput> {

    List<EcuqInput> getList(EcuqInput record);

    Long getCount(EcuqInput record);

    //getListGreaterThanSortId
    List<EcuqInput> getListGreaterThanSortId(EcuqInput record);

    EcuqInput getLatestObject(EcuqInput record);

    /**
     * 同一报价单重新排序
     *
     * @param ecuqId
     * @param sortId
     */
    void reduceSort(@Param("ecuqId") Integer ecuqId, @Param("sortId") Integer sortId);
}
