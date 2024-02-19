//package org.jeecg.modules.cable.service.userEcable.Impl;
//
//import jakarta.annotation.Resource;
//import org.jeecg.common.redis.CacheUtils;
//import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
//import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuInfillingMapper;
//import org.jeecg.modules.cable.service.userEcable.EcbuInfillingService;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_INFILL_CACHE;
//
//@Service
//public class EcbuInfillingServiceImpl implements EcbuInfillingService {
//    @Resource
//    private EcbuInfillingMapper ecbuInfillingMapper;
//
//    @Override
//    public EcbuInfilling getObject(EcbuInfilling record) {
//        return ecbuInfillingMapper.getObject(record);
//    }
//
//
//    @Override
//    public Integer insert(EcbuInfilling record) {
//        return ecbuInfillingMapper.insert(record);
//    }
//
//    @Override
//    public Integer update(EcbuInfilling record) {
//        List<EcbuInfilling> list = ecbuInfillingMapper.getList(record);
//        for (EcbuInfilling ecbuInfilling : list) {
//            CacheUtils.evict(CUSTOMER_INFILL_CACHE, ecbuInfilling.getEcbuiId());
//        }
//        return ecbuInfillingMapper.updateById(record);
//    }
//
//    @Override
//    public List<EcbuInfilling> getList(EcbuInfilling record) {
//        return ecbuInfillingMapper.getList(record);
//    }
//
//    @Override
//    public Integer deleteByEcCompanyId(EcbuInfilling record) {
//        List<EcbuInfilling> list = ecbuInfillingMapper.getList(record);
//        for (EcbuInfilling ecbuInfilling : list) {
//            CacheUtils.evict(CUSTOMER_INFILL_CACHE, ecbuInfilling.getEcbuiId());
//        }
//        return ecbuInfillingMapper.deleteByEcCompanyId(record);
//    }
//
//
//    @Cacheable(value = {CUSTOMER_INFILL_CACHE}, key = "#ecbuinId", unless = "#result == null ")
//    @Override
//    public EcbuInfilling getObjectById(Integer ecbuinId) {
//        return ecbuInfillingMapper.selectById(ecbuinId);
//    }
//}
