//package org.jeecg.modules.cable.service.systemEcable.Impl;
//
//import cn.hutool.core.lang.Pair;
//import cn.hutool.core.util.ObjUtil;
//import cn.hutool.core.util.StrUtil;
//import jakarta.annotation.Resource;
//import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
//import org.jeecg.modules.cable.mapper.dao.systemEcable.EcbMicaTapeMapper;
//import org.jeecg.modules.cable.service.systemEcable.EcbMicaTapeService;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class EcbMicaTapeServiceImpl implements EcbMicaTapeService {
//    @Resource
//    EcbMicaTapeMapper ecbMicaTapeMapper;
//
//    @Override
//    public List<EcbMicaTape> getList(EcbMicaTape record) {// 插入
//        return ecbMicaTapeMapper.getList(record);
//    }
//
//    @Override
//    public List<EcbMicaTape> getListStart(EcbMicaTape record) {
//        return ecbMicaTapeMapper.getListStart(record);
//    }
//
//    @Override
//    public long getCount() {
//        return ecbMicaTapeMapper.getCount();
//    }
//
//    @Override
//    public EcbMicaTape getObject(EcbMicaTape record) {
//        return ecbMicaTapeMapper.getObject(record);
//    }
//
//    @Override
//    public Pair<Map<String, Integer>, Map<String, Integer>> getObjectPassMicaTape() {
//        EcbMicaTape record = new EcbMicaTape();
//        record.setStartType(true);
//        List<EcbMicaTape> list = getListStart(record);
//        Map<String, Integer> abbreviationMap = new HashMap<>();
//        Map<String, Integer> fullNameMap = new HashMap<>();
//        for (EcbMicaTape ecbMicaTape : list) {
//            Integer ecbmId = ecbMicaTape.getEcbmId();
//            if (ObjUtil.isNotNull(ecbMicaTape)) {
//                String abbreviation = ecbMicaTape.getAbbreviation();
//                if (StrUtil.isNotBlank(abbreviation)) {
//                    abbreviationMap.put(abbreviation, ecbmId);
//                }
//                String fullName = ecbMicaTape.getFullName();
//                if (StrUtil.isNotBlank(fullName)) {
//                    fullNameMap.put(fullName, ecbmId);
//                }
//            }
//        }
//        return new Pair<>(abbreviationMap, fullNameMap);
//    }
//}
