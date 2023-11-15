package org.jeecg.modules.cable.service.quality.Impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.mapper.dao.quality.EcuAreaMapper;
import org.jeecg.modules.cable.service.quality.EcuAreaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuAreaServiceImpl implements EcuAreaService {
    @Resource
    EcuAreaMapper ecuAreaMapper;


    @Override
    public List<EcuArea> getList(EcuArea record) {
        return ecuAreaMapper.getList(record);
    }


    @Override
    public long getCount(EcuArea record) {
        return ecuAreaMapper.getCount(record);
    }


    @Override
    public EcuArea getObject(EcuArea record) {
        return ecuAreaMapper.getObject(record);
    }


    @Override
    public Integer insert(EcuArea record) {
        return ecuAreaMapper.insert(record);
    }

    //updateByPrimaryKeySelective
    @Override
    public Integer updateByPrimaryKeySelective(EcuArea record) {
        return ecuAreaMapper.updateById(record);
    }


    @Override
    public Integer deleteByPrimaryKey(Integer ecuaId) {
        return ecuAreaMapper.deleteById(ecuaId);
    }

    
    @Override
    public List<EcuArea> getListGreaterThanSortId(EcuArea record) {
        return ecuAreaMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassName
    @Override
    public EcuArea getObjectPassAreaStr(EcuArea record) {
        return ecuAreaMapper.getObjectPassAreaStr(record);
    }


    @Override
    public EcuArea getLatestObject(EcuArea record) {
        return ecuAreaMapper.getLatestObject(record);
    }

    //deletePassEcqulId
    @Override
    public Integer deletePassEcqulId(Integer ecqulId) {
        return ecuAreaMapper.deletePassEcqulId(ecqulId);
    }

    @Override
    public void batchInsert(List<EcuArea> areas) {
        ecuAreaMapper.batchInsert(areas);
    }

}
