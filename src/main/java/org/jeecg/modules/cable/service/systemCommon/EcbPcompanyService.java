package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;

import java.util.List;

public interface EcbPcompanyService {

    List<EcbPcompanyVo> getList(EcbPcompany record);

    long getCount(EcbPcompany record);

    EcbPcompanyVo getObject(EcbPcompany record);

    Integer insert(EcbPcompany record);
    //update
    Integer update(EcbPcompany record);
    //delete
    Integer delete(EcbPcompany record);
}
