package org.jeecg.modules.cable.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.mapper.dao.user.EcCustomerMapper;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcCustomerServiceImpl extends ServiceImpl<EcCustomerMapper, EcCustomer> implements EcCustomerService {
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

}
