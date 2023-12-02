package org.jeecg.modules.cable.service.userPcc.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;
import org.jeecg.modules.cable.entity.userPcc.EcuProvince;
import org.jeecg.modules.cable.mapper.dao.userPcc.EcuProvinceMapper;
import org.jeecg.modules.cable.service.userPcc.EcuProvinceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EcuProvinceServiceImpl implements EcuProvinceService {
    @Resource
    private EcuProvinceMapper ecuProvinceMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchInsert(List<EcProvince> listProvince, Integer ecCompanyId) {
        LambdaQueryWrapper<EcuProvince> eq = Wrappers.lambdaQuery(EcuProvince.class)
                .eq(EcuProvince::getEcCompanyId, ecCompanyId);
        ecuProvinceMapper.delete(eq);
        for (EcProvince province : listProvince) {
            EcuProvince ecuProvince = new EcuProvince();
            BeanUtils.copyProperties(province, ecuProvince);
            ecuProvince.setEcCompanyId(ecCompanyId);
            ecuProvince.setAddTime(new Date());
            ecuProvince.setUpdateTime(new Date());
            ecuProvinceMapper.insert(ecuProvince);
        }
    }

    @Override
    public List<EcuProvince> selectByCompanyId(Integer ecCompanyId) {
        LambdaUpdateWrapper<EcuProvince> eq = Wrappers.lambdaUpdate(EcuProvince.class)
                .eq(EcuProvince::getStartType, true)
                .eq(EcuProvince::getEcCompanyId, ecCompanyId);
        return ecuProvinceMapper.selectList(eq);
    }

    @Override
    public List<EcuProvince> selectPccByCompanyId(Integer ecCompanyId) {
        return ecuProvinceMapper.selectPccByCompanyId(ecCompanyId);
    }

    @Override
    public EcuProvince insertProvinceName(String provinceName, @NotNull Integer ecCompanyId) {
        EcuProvince province = ecuProvinceMapper.getEcpIdLastOne(ecCompanyId);
        int ecpId = 1;
        if (ObjUtil.isNotNull(province) && ObjUtil.isNotNull(province.getEcpId())) {
            ecpId = province.getEcpId();
        }
        EcuProvince p = new EcuProvince();
        p.setProvinceName(provinceName);
        p.setStartType(true);
        p.setEcpId(ecpId);
        p.setEcCompanyId(ecCompanyId);
        p.setAddTime(new Date());
        p.setUpdateTime(new Date());
        ecuProvinceMapper.insert(p);
        return p;
    }

    @Override
    public void updateProvinceName(@NotBlank String provinceName, @NotNull Integer ecpId, @NotNull Integer ecCompanyId) {
        LambdaUpdateWrapper<EcuProvince> set = Wrappers.lambdaUpdate(EcuProvince.class)
                .eq(EcuProvince::getEcCompanyId, ecCompanyId)
                .eq(EcuProvince::getEcpId, ecpId)
                .set(EcuProvince::getProvinceName, provinceName);
        ecuProvinceMapper.update(set);
    }

    @Override
    public void deleteByEcpId(@NotNull Integer ecpId, @NotNull Integer ecCompanyId) {
        LambdaQueryWrapper<EcuProvince> eq = Wrappers.lambdaQuery(EcuProvince.class)
                .eq(EcuProvince::getEcpId, ecpId)
                .eq(EcuProvince::getEcCompanyId, ecCompanyId);
        ecuProvinceMapper.delete(eq);
    }
}
