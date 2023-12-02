package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuNotice;

import java.util.List;

public interface EcuNoticeService {
    EcuNotice getObject(EcuNotice record);

    List<EcuNotice> getList(EcuNotice record);

    long getCount(EcuNotice record);

    Integer insert(EcuNotice record);

    Integer update(EcuNotice record);

    Integer delete(EcuNotice record);

    EcuNotice getObjectForQuoted(Integer ecuId);
}
