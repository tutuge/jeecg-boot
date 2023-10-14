package org.jeecg.modules.online.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthDataMapper;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service("onlAuthDataServiceImpl")
public class onlAuthDataServiceImpl extends ServiceImpl<OnlAuthDataMapper, OnlAuthData> implements IOnlAuthDataService {
    @Autowired
    private OnlAuthRelationMapper onlAuthRelationMapper;

    public void deleteOne(String id) {
        removeById(id);
        LambdaQueryWrapper<OnlAuthRelation> eq = Wrappers.lambdaQuery(OnlAuthRelation.class).eq(OnlAuthRelation::getAuthId, id);
        this.onlAuthRelationMapper.delete(eq);
    }

    public List<SysPermissionDataRuleModel> queryUserOnlineAuthData(String userId, String cgformId) {
        List<SysPermissionDataRuleModel> list1 = this.baseMapper.queryRoleAuthData(userId, cgformId);
        List<SysPermissionDataRuleModel> list2 = this.baseMapper.queryDepartAuthData(userId, cgformId);
        List<SysPermissionDataRuleModel> list3 = this.baseMapper.queryUserAuthData(userId, cgformId);
        HashMap<Object, SysPermissionDataRuleModel> hashMap = new HashMap<>(5);
        for (SysPermissionDataRuleModel sysPermissionDataRuleModel : list1) {
            String str = sysPermissionDataRuleModel.getId();
            if (hashMap.get(str) == null)
                hashMap.put(str, sysPermissionDataRuleModel);
        }
        for (SysPermissionDataRuleModel sysPermissionDataRuleModel : list2) {
            String str = sysPermissionDataRuleModel.getId();
            if (hashMap.get(str) == null)
                hashMap.put(str, sysPermissionDataRuleModel);
        }
        for (SysPermissionDataRuleModel sysPermissionDataRuleModel : list3) {
            String str = sysPermissionDataRuleModel.getId();
            if (hashMap.get(str) == null)
                hashMap.put(str, sysPermissionDataRuleModel);
        }
        Collection<? extends SysPermissionDataRuleModel> collection = hashMap.values();
        if (collection == null || collection.size() == 0)
            return null;
        return new ArrayList<>(collection);
    }

    public void createAiTestAuthData(JSONObject json) {
        ArrayList<OnlAuthData> arrayList = new ArrayList();
        JSONArray jSONArray = json.getJSONArray("data");
        if (jSONArray != null && jSONArray.size() > 0)
            for (byte b = 0; b < jSONArray.size(); b++) {
                JSONObject jSONObject = jSONArray.getJSONObject(b);
                OnlAuthData onlAuthData = (OnlAuthData) JSONObject.toJavaObject((JSON) jSONObject, OnlAuthData.class);
                arrayList.add(onlAuthData);
            }
        saveBatch(arrayList);
    }
}
