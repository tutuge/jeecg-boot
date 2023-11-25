package org.jeecg.modules.cable.service.systemDelivery.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.mapper.dao.systemEcable.EcSilkModelMapper;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcSilkModelServiceImpl implements EcSilkModelService {
    @Resource
    EcSilkModelMapper ecSilkModelMapper;

    @Override
    public IPage<EcSilkModelVo> selectPageData(Page<EcSilkModel> page, EcSilkModel ecSilkModel) {
        return ecSilkModelMapper.selectPageData(page, ecSilkModel);
    }

    @Override
    public EcSilkModelVo getVoById(Integer id) {
        return ecSilkModelMapper.getVoById(id);
    }

    @Override
    public List<EcSilkModel> selectListBySilkId(Integer ecsId) {
        LambdaQueryWrapper<EcSilkModel> eq = Wrappers.lambdaQuery(EcSilkModel.class)
                .eq(EcSilkModel::getEcSilkId, ecsId);
        return ecSilkModelMapper.selectList(eq);
    }

    @Override
    public void save(EcSilkModel ecSilkModel) {
        ecSilkModelMapper.insert(ecSilkModel);
    }

    @Override
    public EcSilkModel getById(Integer ecSilkId) {
        return ecSilkModelMapper.selectById(ecSilkId);
    }

    @Override
    public boolean updateById(EcSilkModel ecSilkModel) {
        return ecSilkModelMapper.updateById(ecSilkModel) > 0;
    }

    @Override
    public void removeById(Integer id) {
         ecSilkModelMapper.deleteById(id);
    }

    @Override
    public List<EcSilkModel> list(QueryWrapper<EcSilkModel> queryWrapper) {
        return ecSilkModelMapper.selectList(queryWrapper);
    }

    @Override
    public void removeByIds(List<String> list) {
        ecSilkModelMapper.deleteBatchIds(list);
    }
}
