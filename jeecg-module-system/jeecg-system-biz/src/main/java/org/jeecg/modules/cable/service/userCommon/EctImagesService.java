package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EctImages;

public interface EctImagesService {
    int insert(EctImages record);

    EctImages getObject(EctImages record);

    int delete(EctImages record);
}
