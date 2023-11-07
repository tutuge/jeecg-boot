package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;

@Mapper
public interface EcSilkModelMapper extends BaseMapper<EcSilkModel> {


    IPage<EcSilkModelVo> selectPageData(Page<EcSilkModel> page, @Param("model") EcSilkModel ecuSilkModel);

    EcSilkModelVo getVoById(@Param("id") Integer id);
}
