package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.util.List;

public interface EcbulUnitService {

    List<EcbulUnit> getList(EcbulUnit record);


    long getCount(EcbulUnit record);

    /**
     * 根据主键ID查询
     * @param ecbuluId 主键Id
     * @return
     */
    EcbulUnit getObjectById(Integer ecbuluId);


    EcbulUnit getObject(EcbulUnit record);


    Integer insert(EcbulUnit record);

    //update
    Integer update(EcbulUnit record);

    Integer delete(EcbulUnit record);

    
    List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record);

    //getObjectPassLengthName
    EcbulUnit getObjectPassLengthName(EcbulUnit record);


    EcbulUnit getLatestObject(EcbulUnit record);

    void reduceSort(Integer ecbuluId, Integer sortId);

}
