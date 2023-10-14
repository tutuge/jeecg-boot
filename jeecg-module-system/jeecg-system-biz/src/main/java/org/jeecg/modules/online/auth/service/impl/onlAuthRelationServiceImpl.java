package org.jeecg.modules.online.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("onlAuthRelationServiceImpl")
public class onlAuthRelationServiceImpl extends ServiceImpl<OnlAuthRelationMapper, OnlAuthRelation> implements IOnlAuthRelationService {
    public void saveRoleAuth(String roleId, String cgformId, int type, String authMode, List<String> authIds) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlAuthRelation.class)
                .eq(OnlAuthRelation::getCgformId, cgformId)
                .eq(OnlAuthRelation::getType, Integer.valueOf(type))
                .eq(OnlAuthRelation::getAuthMode, authMode)
                .eq(OnlAuthRelation::getRoleId, roleId);
        this.baseMapper.delete(lambdaQueryWrapper);
        ArrayList<OnlAuthRelation> arrayList = new ArrayList();
        for (String str : authIds) {
            OnlAuthRelation onlAuthRelation = new OnlAuthRelation();
            onlAuthRelation.setAuthId(str);
            onlAuthRelation.setCgformId(cgformId);
            onlAuthRelation.setRoleId(roleId);
            onlAuthRelation.setType(Integer.valueOf(type));
            onlAuthRelation.setAuthMode(authMode);
            arrayList.add(onlAuthRelation);
        }
        saveBatch(arrayList);
    }
}
