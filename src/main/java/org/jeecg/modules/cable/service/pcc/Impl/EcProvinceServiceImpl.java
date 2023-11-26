package org.jeecg.modules.cable.service.pcc.Impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.jeecg.common.redis.CacheUtils;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.mapper.dao.pcc.EcProvinceMapper;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.jeecg.modules.cable.constants.CustomerCacheConstant.BASE_PROVINCE_CACHE;

@Service
public class EcProvinceServiceImpl implements EcProvinceService {
    @Resource
    EcProvinceMapper ecProvinceMapper;

    @Override
    public List<EcProvince> getList(EcProvince record) {
        return ecProvinceMapper.getList(record);
    }

    @Override
    public EcProvince getObjectByName(EcProvince record) {
        return ecProvinceMapper.getObjectByName(record);
    }

    @Cacheable(value = {BASE_PROVINCE_CACHE}, key = "#ecpId", unless = "#result == null ")
    @Override
    public EcProvince getObjectById(Integer ecpId) {
        return ecProvinceMapper.selectById(ecpId);
    }

    @Override
    public boolean updateById(EcProvince ecProvince) {
        CacheUtils.evict(BASE_PROVINCE_CACHE, ecProvince.getEcpId());
        ecProvince.setUpdateTime(new Date());
        return ecProvinceMapper.updateById(ecProvince) > 0;
    }

    @Override
    public void removeById(Integer id) {
        CacheUtils.evict(BASE_PROVINCE_CACHE, id);
        ecProvinceMapper.deleteById(id);
    }

    @Override
    public void save(EcProvince ecProvince) {
        ecProvince.setAddTime(new Date());
        ecProvinceMapper.insert(ecProvince);
    }

    @Override
    public IPage<EcProvince> selectPageData(Page<EcProvince> page, EcProvince ecProvince) {
        LambdaQueryWrapper<EcProvince> like = Wrappers.lambdaQuery(EcProvince.class)
                .like(StrUtil.isNotBlank(ecProvince.getProvinceName()), EcProvince::getProvinceName, ecProvince.getProvinceName());
        return ecProvinceMapper.selectPage(page, like);
    }

}
