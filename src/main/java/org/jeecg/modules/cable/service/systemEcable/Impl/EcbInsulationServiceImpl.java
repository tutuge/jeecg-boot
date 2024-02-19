//package org.jeecg.modules.cable.service.systemEcable.Impl;
//
//import jakarta.annotation.Resource;
//import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
//import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbInsulationMapper;
//import org.jeecg.modules.cable.service.systemEcable.EcbInsulationService;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EcbInsulationServiceImpl implements EcbInsulationService {
//    @Resource
//    EcbInsulationMapper ecbInsulationMapper;
//
//    @Override
//    public List<EcbInsulation> getList(EcbInsulation record) {// 插入
//        return ecbInsulationMapper.getList(record);
//    }
//
//    @Override
//    public List<EcbInsulation> getListStart(EcbInsulation record) {
//        return ecbInsulationMapper.getListStart(record);
//    }
//
//    @Override
//    public long getCount() {
//        return ecbInsulationMapper.getCount();
//    }
//
//    @Override
//    public EcbInsulation getObject(EcbInsulation record) {
//        return ecbInsulationMapper.getObject(record);
//    }
//}
