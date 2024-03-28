package org.jeecg.modules.cable.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.mapper.dao.user.EcCustomerMapper;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCustomerServiceImpl implements EcCustomerService {
    @Resource
    EcCustomerMapper ecCustomerMapper;

    @Override
    public List<EcCustomer> getList(EcCustomer record) {
        return ecCustomerMapper.getList(record);
    }

    @Override
    public long getCount(EcCustomer record) {
        return ecCustomerMapper.getCount(record);
    }

    @Override
    public EcCustomer getObject(EcCustomer record) {
        return ecCustomerMapper.getObject(record);
    }

    @Override
    public EcCustomer getObjectById(Integer eccuId) {
        return ecCustomerMapper.selectById(eccuId);
    }

    @Override
    public Integer insert(EcCustomer record) {
        return ecCustomerMapper.insert(record);
    }

    @Override
    public Integer update(EcCustomer record) {
        return ecCustomerMapper.updateById(record);
    }

    @Override
    public void deleteById(Integer eccuId) {
        ecCustomerMapper.deleteById(eccuId);
    }

    @Override
    public IPage<EcCustomer> page(Page<EcCustomer> page, QueryWrapper<EcCustomer> queryWrapper) {
        return ecCustomerMapper.selectPage(page,queryWrapper);
    }

}
