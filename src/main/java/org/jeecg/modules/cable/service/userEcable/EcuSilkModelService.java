package org.jeecg.modules.cable.service.userEcable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.controller.userEcable.SilkModel.vo.SilkModelVo;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;

import java.util.List;
import java.util.Set;

public interface EcuSilkModelService{

    IPage<SilkModelVo> selectPage(Page<EcuSilkModel> page, EcuSilkModel ecuSilkModel);

    SilkModelVo getVoById(Integer id);

    List<EcuSilkModel> listByIds(Set<Integer> ids);

    EcuSilkModel getObjectById(Integer silkModelId);

    void insert(EcuSilkModel ecuSilkModel);

    boolean updateById(EcuSilkModel ecuSilkModel);

    void removeById(String id);

    void removeByIds(List<String> list);

    List<EcuSilkModel> list(QueryWrapper<EcuSilkModel> queryWrapper);
}
