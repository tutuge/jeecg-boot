package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;

public interface EcuQualifiedService extends IService<EcuQualified> {

    EcuQualified getVoById(Integer id);

    IPage<EcuQualified> selectPage(Page<EcuQualified> page, EcuQualified ecuQualified);
}
