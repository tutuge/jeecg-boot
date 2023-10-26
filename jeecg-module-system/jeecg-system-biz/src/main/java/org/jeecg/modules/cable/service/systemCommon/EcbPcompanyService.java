package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;

import java.util.List;

public interface EcbPcompanyService {
    //getList
    List<EcbPcompany> getList(EcbPcompany record);
    //getCount
    long getCount(EcbPcompany record);
    //getObject
    EcbPcompany getObject(EcbPcompany record);
    //insert
    Integer insert(EcbPcompany record);
    //update
    Integer update(EcbPcompany record);
    //delete
    Integer delete(EcbPcompany record);
}
