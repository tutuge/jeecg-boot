//package org.jeecg.modules.cable.service.systemEcable.Impl;
//
//import jakarta.annotation.Resource;
//import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
//import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbShieldMapper;
//import org.jeecg.modules.cable.service.systemEcable.EcbShieldService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EcbShieldServiceImpl implements EcbShieldService {
//    @Resource
//    EcbShieldMapper ecbShieldMapper;
//
//    @Override
//    public List<EcbShield> getList(EcbShield record) {
//        return ecbShieldMapper.getList(record);
//    }
//
//    @Override
//    public List<EcbShield> getSysList(EcbShield record) {
//        return ecbShieldMapper.getSysList(record);
//    }
//
//    @Override
//    public List<EcbShield> getListStart(EcbShield record) {
//        return ecbShieldMapper.getListStart(record);
//    }
//
//    @Override
//    public long getCount() {
//        return ecbShieldMapper.getCount();
//    }
//
//    @Override
//    public EcbShield getObject(EcbShield record) {
//        return ecbShieldMapper.getObject(record);
//    }
//
//    @Override
//    public long getSysCount(EcbShield record) {
//        return ecbShieldMapper.getSysCount(record);
//    }
//
//    @Override
//    public void insert(EcbShield record) {
//        ecbShieldMapper.insert(record);
//    }
//
//    @Override
//    public void updateById(EcbShield record) {
//        ecbShieldMapper.updateById(record);
//    }
//
//    @Override
//    public void deleteById(Integer ecbsId) {
//        ecbShieldMapper.deleteById(ecbsId);
//    }
//}
