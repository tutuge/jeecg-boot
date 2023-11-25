package org.jeecg.modules.cable.service.systemDelivery;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.model.systemEcable.EcSilkServiceModel;

import java.util.List;

public interface EcSilkModelService{

    IPage<EcSilkModelVo> selectPageData(Page<EcSilkModel> page, EcSilkModel ecSilkModel);

    EcSilkModelVo getVoById(Integer id);

    List<EcSilkModel> selectListBySilkId(Integer ecsId);

    void save(EcSilkModel ecSilkModel);

    EcSilkModel getById(Integer ecSilkId);

    boolean updateById(EcSilkModel ecSilkModel);

    void removeById(Integer id);

    List<EcSilkModel> list(QueryWrapper<EcSilkModel> queryWrapper);

    void removeByIds(List<String> list);
}
