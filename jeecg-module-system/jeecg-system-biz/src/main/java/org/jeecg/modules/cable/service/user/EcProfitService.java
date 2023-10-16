package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcProfit;

import java.util.List;

public interface EcProfitService {
    EcProfit getObject(EcProfit record);

    List<EcProfit> getList(EcProfit record);

    long getCount(EcProfit record);

    int insert(EcProfit record);

    int update(EcProfit record);

    int delete(EcProfit record);
}
