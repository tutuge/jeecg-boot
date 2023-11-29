package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.util.List;

@Mapper
public interface EcbulUnitMapper extends BaseMapper<EcbulUnit> {
    List<EcbulUnit> getList(EcbulUnit record);

    long getCount(EcbulUnit record);

    EcbulUnit getObject(EcbulUnit record);

    Integer deleteRecord(EcbulUnit record);


    List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record);

    //getObjectPassLengthName
    EcbulUnit getObjectPassLengthName(EcbulUnit record);


    EcbulUnit getLatestObject(EcbulUnit record);

    /**
     * 给单位重新排序
     *
     * @param ecbuluId
     * @param sortId
     */
    void reduceSort(@Param("ecbuluId") Integer ecbuluId, @Param("sortId") Integer sortId);
}
