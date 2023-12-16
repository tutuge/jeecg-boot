package org.jeecg.modules.cable.service.systemCommon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;

import java.util.List;

public interface EcSpecificationsService {


    List<EcSpecifications> selectListByName(Boolean special,String abbreviation);

    void insert(Boolean special,String s0, String s1);

    void updateByName(boolean b, String s0, String s1);

    IPage<EcSpecifications> page(Page<EcSpecifications> page, QueryWrapper<EcSpecifications> queryWrapper);

    void save(EcSpecifications specifications);

    EcSpecifications getById(Integer specificationsId);

    boolean updateById(EcSpecifications specifications);

    void removeById(String id);

    void removeByIds(List<String> list);

    List<EcSpecifications> list(QueryWrapper<EcSpecifications> queryWrapper);
}
