package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;

import java.util.List;

public interface EcbuAxleService {

    List<EcbuAxle> getList(EcbuAxle record);


    long getCount(EcbuAxle record);


    EcbuAxle getObject(EcbuAxle record);


    Integer insert(EcbuAxle record);


    Integer updateByPrimaryKeySelective(EcbuAxle record);


    Integer deleteByPrimaryKey(Integer ecbuaId);

    
    List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record);


    EcbuAxle getObjectPassAxleName(EcbuAxle record);


    EcbuAxle getLatestObject(EcbuAxle record);

    EcbuAxle getById(Integer ecbuaId);

    void deletePassEcCompanyId(Integer ecCompanyId);
}
