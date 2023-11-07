package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

@Mapper
public interface EcuSilkModelMapper extends BaseMapper<EcuSilkModel> {

    IPage<SilkModelVo> selectPageData(Page<EcuSilkModel> page, @Param("model") EcuSilkModel ecuSilkModel);

    SilkModelVo getVoById(@Param("id") Integer id);
}
