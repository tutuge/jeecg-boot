package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;

import java.util.List;

public interface EcbPcompanyService {

    List<EcbPcompanyVo> getList(EcbPcompany record);
    //getCount
    long getCount(EcbPcompany record);
    //getObject
    EcbPcompanyVo getObject(EcbPcompany record);
    //insert
    Integer insert(EcbPcompany record);
    //update
    Integer update(EcbPcompany record);
    //delete
    Integer delete(EcbPcompany record);
}
