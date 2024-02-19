//package org.jeecg.modules.cable.service.systemEcable.Impl;
//
//import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbSheathMapper;
//import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
//import org.jeecg.modules.cable.service.systemEcable.EcbSheathService;
//import jakarta.annotation.Resource;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class EcbSheathServiceImpl implements EcbSheathService {
//    @Resource
//    EcbSheathMapper ecbSheathMapper;
//
//    @Override
//    public List<EcbSheath> getList(EcbSheath record) {
//        return ecbSheathMapper.getList(record);
//    }
//
//    @Override
//    public List<EcbSheath> getListStart(EcbSheath record) {
//        return ecbSheathMapper.getListStart(record);
//    }
//
//    @Override
//    public long getCount() {
//        return ecbSheathMapper.getCount();
//    }
//
//    @Override
//    public EcbSheath getObject(EcbSheath record) {
//        return ecbSheathMapper.getObject(record);
//    }
//}
