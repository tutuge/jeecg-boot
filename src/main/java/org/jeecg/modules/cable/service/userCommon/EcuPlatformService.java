package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.cable.entity.userCommon.EcuPlatform;

import java.util.List;

public interface EcuPlatformService {
    IPage<EcuPlatform> page(Page<EcuPlatform> page, QueryWrapper<EcuPlatform> queryWrapper);

    void save(EcuPlatform platform);

    EcuPlatform getById(Integer platformId);

    boolean updateById(EcuPlatform platform);

    void removeById(Integer id);

    void removeByIds(List<String> list);

    List<EcuPlatform> list(QueryWrapper<EcuPlatform> queryWrapper);

    EcuPlatform getOne(LambdaQueryWrapper<EcuPlatform> eq);
}
