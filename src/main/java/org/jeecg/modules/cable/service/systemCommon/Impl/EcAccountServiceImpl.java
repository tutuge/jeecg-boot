package org.jeecg.modules.cable.service.systemCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.systemCommon.EcAccount;
import org.jeecg.modules.cable.mapper.dao.systemCommon.EcAccountMapper;
import org.jeecg.modules.cable.service.systemCommon.EcAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcAccountServiceImpl implements EcAccountService {
    @Resource
    private EcAccountMapper ecAccountMapper;


    @Override
    public IPage<EcAccount> page(Page<EcAccount> page, QueryWrapper<EcAccount> queryWrapper) {
        return ecAccountMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void save(EcAccount ecAccount) {
        int sortId = 1;
        EcAccount account = ecAccountMapper.selectLastObject();
        if (ObjUtil.isNotNull(account)) {
            sortId = account.getSortId() + 1;
        }
        ecAccount.setSortId(sortId);
        ecAccountMapper.insert(ecAccount);
    }

    @Override
    public EcAccount getById(Integer id) {
        return ecAccountMapper.selectById(id);
    }

    @Override
    public boolean updateById(EcAccount ecAccount) {
        return ecAccountMapper.updateById(ecAccount) > 0;
    }

    @Override
    public void removeById(String id) {
        ecAccountMapper.deleteById(id);
    }

    @Override
    public void removeByIds(List<String> split) {
        ecAccountMapper.deleteBatchIds(split);
    }
}
