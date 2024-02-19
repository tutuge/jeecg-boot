//package org.jeecg.modules.cable.service.userEcable.Impl;
//
//import jakarta.annotation.Resource;
//import org.jeecg.common.redis.CacheUtils;
//import org.jeecg.modules.cable.entity.userEcable.EcbuSteelBand;
//import org.jeecg.modules.cable.mapper.dao.userEcable.EcbuSteelBandMapper;
//import org.jeecg.modules.cable.service.userEcable.EcbuSteelbandService;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//import static org.jeecg.modules.cable.constants.CustomerCacheConstant.CUSTOMER_STEEL_BAND_CACHE;
//
//@Service
//public class EcbuSteelBandServiceImpl implements EcbuSteelbandService {
//    @Resource
//    private EcbuSteelBandMapper ecbuSteelBandMapper;
//
//    @Override
//    public EcbuSteelBand getObject(EcbuSteelBand record) {
//        return ecbuSteelBandMapper.getObject(record);
//    }
//
//    @Cacheable(value = {CUSTOMER_STEEL_BAND_CACHE}, key = "#ecbusId", unless = "#result == null ")
//    @Override
//    public EcbuSteelBand getObjectById(Integer ecbusId) {
//        return ecbuSteelBandMapper.selectById(ecbusId);
//    }
//
//    @Override
//    public Integer insert(EcbuSteelBand record) {
//        return ecbuSteelBandMapper.insert(record);
//    }
//
//    @Override
//    public Integer update(EcbuSteelBand record) {
//        List<EcbuSteelBand> list = ecbuSteelBandMapper.getList(record);
//        for (EcbuSteelBand ecbuSteelband : list) {
//            CacheUtils.evict(CUSTOMER_STEEL_BAND_CACHE, ecbuSteelband.getEcbusId());
//        }
//        return ecbuSteelBandMapper.updateById(record);
//    }
//
//    @Override
//    public List<EcbuSteelBand> getList(EcbuSteelBand record) {
//        return ecbuSteelBandMapper.getList(record);
//    }
//
//    @Override
//    public Integer deleteByEcCompanyId(EcbuSteelBand record) {
//        List<EcbuSteelBand> list = ecbuSteelBandMapper.getList(record);
//        for (EcbuSteelBand ecbuSteelband : list) {
//            CacheUtils.evict(CUSTOMER_STEEL_BAND_CACHE, ecbuSteelband.getEcbusId());
//        }
//        return ecbuSteelBandMapper.deleteByEcCompanyId(record);
//    }
//}
