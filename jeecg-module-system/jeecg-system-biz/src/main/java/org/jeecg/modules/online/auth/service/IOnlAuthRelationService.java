package org.jeecg.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;

import java.util.List;

public interface IOnlAuthRelationService extends IService<OnlAuthRelation> {
    void saveRoleAuth(String paramString1, String paramString2, int paramInt, String paramString3, List<String> paramList);
}
