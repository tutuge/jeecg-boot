package org.jeecg.modules.cable.service.userCommon.Impl;

import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.mapper.dao.userCommon.EcbuPcompanyMapper;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.service.userCommon.EcbuPcompanyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcbuPcompanyServiceImpl implements EcbuPcompanyService {
    @Resource
    EcbuPcompanyMapper ecbuPcompanyMapper;


    @Override
    public List<EcbuPCompanyVo> getList(EcbuPcompany record) {
        return ecbuPcompanyMapper.getList(record);
    }


    @Override
    public long getCount(EcbuPcompany record) {
        return ecbuPcompanyMapper.getCount(record);
    }


    @Override
    public EcbuPCompanyVo getObject(EcbuPcompany record) {
        return ecbuPcompanyMapper.getObject(record);
    }


    @Override
    public Integer insert(EcbuPcompany record) {
        return ecbuPcompanyMapper.insert(record);
    }

    //update
    @Override
    public Integer update(EcbuPcompany record) {
        return ecbuPcompanyMapper.update(record);
    }

    @Override
    public Integer delete(EcbuPcompany record) {
        return ecbuPcompanyMapper.delete(record);
    }

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    @Override
    public List<EcbuPcompany> getListGreaterThanSortId(EcbuPcompany record) {
        return ecbuPcompanyMapper.getListGreaterThanSortId(record);
    }

    //getObjectPassPcName
    @Override
    public EcbuPcompany getObjectPassPcName(EcbuPcompany record) {
        return ecbuPcompanyMapper.getObjectPassPcName(record);
    }


    @Override
    public EcbuPcompany getLatestObject(EcbuPcompany record) {
        return ecbuPcompanyMapper.getLatestObject(record);
    }

}
