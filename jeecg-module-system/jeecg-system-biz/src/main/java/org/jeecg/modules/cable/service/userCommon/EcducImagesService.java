package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcducImages;

import java.util.List;

public interface EcducImagesService {
    List<EcducImages> getList(EcducImages record);

    EcducImages getObject(EcducImages record);

    int insert(EcducImages record);

    int delete(EcducImages record);
}
