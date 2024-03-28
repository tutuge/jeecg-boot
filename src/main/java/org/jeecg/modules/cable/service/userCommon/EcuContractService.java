package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userCommon.EcuContract;

public interface EcuContractService extends IService<EcuContract> {

    EcuContract getVoById(Integer id);

    IPage<EcuContract> selectPage(Page<EcuContract> page, EcuContract ecuContract);
}
