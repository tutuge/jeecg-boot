package org.jeecg.modules.cable.service.price.Impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.entity.price.EcuQuoted;
import org.jeecg.modules.cable.mapper.dao.price.EcuQuotedMapper;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.jeecg.modules.cable.service.price.EcuQuotedService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EcuQuotedServiceImpl implements EcuQuotedService {
    @Resource
    EcuQuotedMapper ecuQuotedMapper;
    @Resource
    private EcProvinceService provinceService;


    @Override
    public List<EcuQuoted> getList(EcuQuoted record) {
        return ecuQuotedMapper.getList(record);
    }


    @Override
    public Long getCount(EcuQuoted record) {
        return ecuQuotedMapper.getCount(record);
    }

    @Override
    public Long selectCount(EcuQuoted record) {
        LambdaQueryWrapper<EcuQuoted> eq = Wrappers.lambdaQuery(EcuQuoted.class)
                .eq(EcuQuoted::getDeliveryStoreId, record.getDeliveryStoreId());
        return ecuQuotedMapper.selectCount(eq);
    }


    @Override
    public EcuQuoted getObject(EcuQuoted record) {
        return ecuQuotedMapper.getObject(record);
    }


    @Override
    public EcuQuoted getLatestObject(EcuQuoted record) {
        return ecuQuotedMapper.getLatestObject(record);
    }


    @Override
    public Integer insert(EcuQuoted record) {
        return ecuQuotedMapper.insert(record);
    }

    @Override
    public Integer update(EcuQuoted record) {
        return ecuQuotedMapper.updateById(record);
    }


    /**
     * @param ecuqId        报价单id
     * @param nbuptMoney    不开票总计
     * @param buptMoney     开票总计
     * @param deliveryMoney 快递金额
     * @param totalWeight   总重量
     */
    @Override
    public void dealMoney(Integer ecuqId, BigDecimal nbuptMoney, BigDecimal buptMoney, BigDecimal deliveryMoney, BigDecimal totalWeight) {
        EcuQuoted record = new EcuQuoted();
        record.setEcuqId(ecuqId);
        record.setNbuptMoney(nbuptMoney);
        record.setBuptMoney(buptMoney);
        record.setDeliveryMoney(deliveryMoney);
        record.setTotalWeight(totalWeight);
        update(record);
    }

    @Override
    public EcuQuoted getObjectById(Integer ecuqId) {
        EcuQuoted quoted = ecuQuotedMapper.selectById(ecuqId);
        if (ObjUtil.isNotNull(quoted) && ObjUtil.isNotNull(quoted.getEcpId())) {
            EcProvince province = provinceService.getObjectById(quoted.getEcpId());
            if (ObjUtil.isNotNull(province)) {
                quoted.setProvinceName(province.getProvinceName());
            }
        } else {
            throw new RuntimeException("当前订单不存在，无法操作！");
        }
        return quoted;
    }
}
