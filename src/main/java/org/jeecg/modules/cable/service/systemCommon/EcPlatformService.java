package org.jeecg.modules.cable.service.systemCommon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemCommon.EcPlatform;

import java.util.List;

public interface EcPlatformService  {
    List<EcPlatform> getListStart();

    IPage<EcPlatform> page(Page<EcPlatform> page, QueryWrapper<EcPlatform> queryWrapper);

    void save(EcPlatform platform);

    EcPlatform getById(Integer platformId);

    boolean updateById(EcPlatform platform);

    void removeById(Integer id);

    void removeByIds(List<String> list);

    List<EcPlatform> list(QueryWrapper<EcPlatform> queryWrapper);

    EcPlatform getOne(LambdaQueryWrapper<EcPlatform> eq);
}
