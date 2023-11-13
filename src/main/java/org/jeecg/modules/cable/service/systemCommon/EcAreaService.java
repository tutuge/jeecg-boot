package org.jeecg.modules.cable.service.systemCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemCommon.EcArea;

public interface EcAreaService extends IService<EcArea> {

    /**
     *
     * @param areaStr 平米数
     * @return
     */
    EcArea getByArea(String areaStr);
}
