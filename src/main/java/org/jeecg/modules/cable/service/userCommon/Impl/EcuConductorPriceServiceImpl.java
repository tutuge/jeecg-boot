package org.jeecg.modules.cable.service.userCommon.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.cable.entity.userCommon.EcuConductorPrice;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcuConductorPriceMapper;
import org.jeecg.modules.cable.service.userCommon.EcuConductorPriceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class EcuConductorPriceServiceImpl extends ServiceImpl<EcuConductorPriceMapper, EcuConductorPrice> implements EcuConductorPriceService {
    @Override
    public void saveOrUpdateByEcuqId(Integer ecuqId, Integer ecbucId, BigDecimal cunitPrice) {
        EcuConductorPrice ecuConductorPrice = selectByEcuqIdEcbucId(ecuqId, ecbucId);
        if (ObjUtil.isNull(ecuConductorPrice)) {
            EcuConductorPrice price = new EcuConductorPrice();
            price.setEcuqId(ecuqId);
            price.setEcbucId(ecbucId);
            price.setCunitPrice(cunitPrice);
            price.setAddTime(new Date());
            price.setUpdateTime(new Date());
            baseMapper.insert(price);
        } else {
            ecuConductorPrice.setCunitPrice(cunitPrice);
            ecuConductorPrice.setUpdateTime(new Date());
            baseMapper.updateById(ecuConductorPrice);
        }
    }

    @Override
    public EcuConductorPrice selectByEcuqIdEcbucId(Integer ecuqId, Integer ecbucId) {
        LambdaQueryWrapper<EcuConductorPrice> eq = Wrappers.lambdaQuery(EcuConductorPrice.class)
                .eq(EcuConductorPrice::getEcuqId, ecuqId)
                .eq(EcuConductorPrice::getEcbucId, ecbucId);
        return baseMapper.selectOne(eq);
    }

    @Override
    public List<EcuConductorPrice> listByQuotedId(Integer ecuqId) {
        LambdaQueryWrapper<EcuConductorPrice> eq = Wrappers.lambdaQuery(EcuConductorPrice.class)
                .eq(EcuConductorPrice::getEcuqId, ecuqId).orderByDesc(true,EcuConductorPrice::getUpdateTime);
        return baseMapper.selectList(eq);
    }
}
