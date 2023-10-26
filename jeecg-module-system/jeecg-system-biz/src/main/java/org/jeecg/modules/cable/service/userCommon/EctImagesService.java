package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EctImages;

public interface EctImagesService {
    Integer insert(EctImages record);

    EctImages getObject(EctImages record);

    Integer delete(EctImages record);
}
