package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;

import java.util.List;

@Mapper
public interface EcbPcompanyMapper extends BaseMapper<EcbPcompany> {

    List<EcbPcompanyVo> getList(EcbPcompany record);


    long getCount(EcbPcompany record);


    EcbPcompanyVo getObject(EcbPcompany record);

}
