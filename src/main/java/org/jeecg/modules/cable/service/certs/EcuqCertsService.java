package org.jeecg.modules.cable.service.certs;


import org.jeecg.modules.cable.entity.certs.EcuqCerts;

import java.util.List;

public interface EcuqCertsService {
    EcuqCerts getObject(EcuqCerts record);

    List<EcuqCerts> getList(EcuqCerts record);

    long getCount(EcuqCerts record);

    Integer insert(EcuqCerts record);

    Integer update(EcuqCerts record);

    Integer delete(EcuqCerts record);
}
