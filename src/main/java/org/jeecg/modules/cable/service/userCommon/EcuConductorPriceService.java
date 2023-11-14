package org.jeecg.modules.cable.service.userCommon;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.userCommon.EcuConductorPrice;

import java.math.BigDecimal;
import java.util.List;

public interface EcuConductorPriceService extends IService<EcuConductorPrice> {

    /**
     * 创建或者更新对应报价单单独设置的导体价格信息
     *
     * @param ecuqId     报价单ID
     * @param ecbucId    导体ID
     * @param cunitPrice 导体价格
     */
    void saveOrUpdateByEcuqId(Integer ecuqId, Integer ecbucId, BigDecimal cunitPrice);

    /**
     * 根据报价单id和导体id查询价格
     *
     * @param ecuqId  报价单ID
     * @param ecbucId 导体ID
     * @return
     */
    EcuConductorPrice selectByEcuqIdEcbucId(Integer ecuqId, Integer ecbucId);

    /**
     * 根据报价单Id获取导体的金额信息
     *
     * @param ecuqId
     * @return
     */
    List<EcuConductorPrice> listByQuotedId(Integer ecuqId);
}
