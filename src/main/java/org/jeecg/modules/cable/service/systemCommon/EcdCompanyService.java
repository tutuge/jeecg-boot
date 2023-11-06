package org.jeecg.modules.cable.service.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;

import java.util.List;

public interface EcdCompanyService {
    List<EcdCompany> getList(EcdCompany record);

    long getCount(EcdCompany record);

    EcdCompany getObject(EcdCompany record);

    Integer insert(EcdCompany record);

    Integer update(EcdCompany record);

    Integer delete(EcdCompany record);
}
