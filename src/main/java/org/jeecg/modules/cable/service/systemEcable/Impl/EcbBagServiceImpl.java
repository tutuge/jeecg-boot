//package org.jeecg.modules.cable.service.systemEcable.Impl;
//
//import jakarta.annotation.Resource;
//import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
//import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbBagMapper;
//import org.jeecg.modules.cable.service.systemEcable.EcbBagService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EcbBagServiceImpl implements EcbBagService {
//    @Resource
//    EcbBagMapper bagMapper;
//
//    @Override
//    public List<EcbBag> getList(EcbBag record) {//插入
//        return bagMapper.getList(record);
//    }
//
//    @Override
//    public List<EcbBag> getListStart(EcbBag record) {
//        return bagMapper.getListStart(record);
//    }
//
//    @Override
//    public long getCount() {
//        return bagMapper.getCount();
//    }
//
//    @Override
//    public EcbBag getObject(EcbBag record) {
//        return bagMapper.getSysObject(record);
//    }
//}
