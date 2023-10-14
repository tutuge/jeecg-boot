package org.jeecg.modules.online.cgform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceJava;
import org.jeecg.modules.online.cgform.entity.OnlCgformEnhanceSql;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceJavaMapper;
import org.jeecg.modules.online.cgform.mapper.OnlCgformEnhanceSqlMapper;
import org.jeecg.modules.online.cgform.service.IOnlCgformEnhanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("onlCgformEnhanceService")
public class onlCgformEnhanceService implements IOnlCgformEnhanceService {
    @Autowired
    private OnlCgformEnhanceJavaMapper onlCgformEnhanceJavaMapper;

    @Autowired
    private OnlCgformEnhanceSqlMapper onlCgformEnhanceSqlMapper;

    public List<OnlCgformEnhanceJava> queryEnhanceJavaList(String cgformId) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformEnhanceJava.class)
                .eq(OnlCgformEnhanceJava::getCgformHeadId, cgformId);
        return this.onlCgformEnhanceJavaMapper.selectList(lambdaQueryWrapper);
    }

    public void saveEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        this.onlCgformEnhanceJavaMapper.insert(onlCgformEnhanceJava);
    }

    public void updateEnhanceJava(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        this.onlCgformEnhanceJavaMapper.updateById(onlCgformEnhanceJava);
    }

    public void deleteEnhanceJava(String id) {
        this.onlCgformEnhanceJavaMapper.deleteById(id);
    }

    public void deleteBatchEnhanceJava(List<String> idList) {
        this.onlCgformEnhanceJavaMapper.deleteBatchIds(idList);
    }

    public boolean checkOnlyEnhance(OnlCgformEnhanceJava onlCgformEnhanceJava) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformEnhanceJava.class)
                .eq(OnlCgformEnhanceJava::getButtonCode, onlCgformEnhanceJava.getButtonCode())
                .eq(OnlCgformEnhanceJava::getCgformHeadId, onlCgformEnhanceJava.getCgformHeadId())
                .eq(OnlCgformEnhanceJava::getEvent, onlCgformEnhanceJava.getEvent());
        Long long_ = this.onlCgformEnhanceJavaMapper.selectCount(lambdaQueryWrapper);
        if (long_ != null) {
            if (long_.longValue() == 1L && oConvertUtils.isEmpty(onlCgformEnhanceJava.getId()))
                return false;
            if (long_.longValue() == 2L)
                return false;
        }
        return true;
    }

    public boolean checkOnlyEnhance(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformEnhanceSql.class)
                .eq(OnlCgformEnhanceSql::getButtonCode, onlCgformEnhanceSql.getButtonCode())
                .eq(OnlCgformEnhanceSql::getCgformHeadId, onlCgformEnhanceSql.getCgformHeadId());
        Long long_ = this.onlCgformEnhanceSqlMapper.selectCount(lambdaQueryWrapper);
        if (long_ != null) {
            if (long_ == 1L && oConvertUtils.isEmpty(onlCgformEnhanceSql.getId()))
                return false;
            if (long_ > 1L)
                return false;
        }
        return true;
    }

    public List<OnlCgformEnhanceSql> queryEnhanceSqlList(String cgformId) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformEnhanceSql.class)
                .eq(OnlCgformEnhanceSql::getCgformHeadId, cgformId);
        return this.onlCgformEnhanceSqlMapper.selectList(lambdaQueryWrapper);
    }

    public void saveEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        this.onlCgformEnhanceSqlMapper.insert(onlCgformEnhanceSql);
    }

    public void updateEnhanceSql(OnlCgformEnhanceSql onlCgformEnhanceSql) {
        this.onlCgformEnhanceSqlMapper.updateById(onlCgformEnhanceSql);
    }

    public void deleteEnhanceSql(String id) {
        this.onlCgformEnhanceSqlMapper.deleteById(id);
    }

    public void deleteBatchEnhanceSql(List<String> idList) {
        this.onlCgformEnhanceSqlMapper.deleteBatchIds(idList);
    }
}
