package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;

import java.util.List;

public interface EcbulUnitService extends IService<EcbulUnit> {

    List<EcbulUnit> getList(EcbulUnit record);


    long getCount(EcbulUnit record);


    EcbulUnit getObject(EcbulUnit record);


    Integer insert(EcbulUnit record);

    //update
    Integer update(EcbulUnit record);

    Integer delete(EcbulUnit record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbulUnit> getListGreaterThanSortId(EcbulUnit record);

    //getObjectPassLengthName
    EcbulUnit getObjectPassLengthName(EcbulUnit record);


    EcbulUnit getLatestObject(EcbulUnit record);
}
