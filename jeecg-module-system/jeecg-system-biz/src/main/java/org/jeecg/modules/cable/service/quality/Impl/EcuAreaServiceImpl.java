package org.jeecg.modules.cable.service.quality.Impl;

import org.jeecg.modules.cable.mapper.dao.quality.EcuAreaDao;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.service.quality.EcuAreaService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuAreaServiceImpl implements EcuAreaService {
    @Resource
    EcuAreaDao ecuAreaDao;
    //getList
    @Override
    public List<EcuArea> getList(EcuArea record) {
        return ecuAreaDao.getList(record);
    }
    //getCount
    @Override
    public long getCount(EcuArea record) {
        return ecuAreaDao.getCount(record);
    }
    //getObject
    @Override
    public EcuArea getObject(EcuArea record)
    {
        return ecuAreaDao.getObject(record);
    }
    //insert
    @Override
    public int insert(EcuArea record)
    {
        return ecuAreaDao.insert(record);
    }
    //updateByPrimaryKeySelective
    @Override
    public int updateByPrimaryKeySelective(EcuArea record) {
        return ecuAreaDao.updateByPrimaryKeySelective(record);
    }
    //deleteByPrimaryKey
    @Override
    public int deleteByPrimaryKey(int ecuaId) {
       return ecuAreaDao.deleteByPrimaryKey(ecuaId);
    }
    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcuArea> getListGreaterThanSortId(EcuArea record){
        return ecuAreaDao.getListGreaterThanSortId(record);
    }
    //getObjectPassName
    @Override
    public EcuArea getObjectPassAreaStr(EcuArea record)
    {
        return ecuAreaDao.getObjectPassAreaStr(record);
    }
    //getLatestObject
    @Override
    public EcuArea getLatestObject(EcuArea record) {
        return ecuAreaDao.getLatestObject(record);
    }
    //deletePassEcqulId
    @Override
    public int deletePassEcqulId(int ecqulId)
    {
        return ecuAreaDao.deletePassEcqulId(ecqulId);
    }

}
