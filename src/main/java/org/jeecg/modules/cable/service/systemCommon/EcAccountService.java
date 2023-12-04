package org.jeecg.modules.cable.service.systemCommon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.cable.entity.systemCommon.EcAccount;

import java.util.List;

public interface EcAccountService {

    IPage<EcAccount> page(Page<EcAccount> page, QueryWrapper<EcAccount> queryWrapper);

    void save(EcAccount ecAccount);

    EcAccount getById(Integer id);

    boolean updateById(EcAccount ecAccount);

    void removeById(String id);

    void removeByIds(List<String> split);
}
