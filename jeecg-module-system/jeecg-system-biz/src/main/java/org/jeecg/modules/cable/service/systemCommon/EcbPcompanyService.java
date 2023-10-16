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
    int insert(EcbPcompany record);
    //update
    int update(EcbPcompany record);
    //delete
    int delete(EcbPcompany record);
}
