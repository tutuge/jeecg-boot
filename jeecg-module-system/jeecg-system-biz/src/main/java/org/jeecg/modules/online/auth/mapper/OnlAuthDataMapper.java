package org.jeecg.modules.online.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.system.vo.SysPermissionDataRuleModel;
import org.jeecg.modules.online.auth.entity.OnlAuthData;

import java.util.List;

public interface OnlAuthDataMapper extends BaseMapper<OnlAuthData> {
    List<SysPermissionDataRuleModel> queryRoleAuthData(@Param("userId") String paramString1, @Param("cgformId") String paramString2);

    List<SysPermissionDataRuleModel> queryDepartAuthData(@Param("userId") String paramString1, @Param("cgformId") String paramString2);

    List<SysPermissionDataRuleModel> queryUserAuthData(@Param("userId") String paramString1, @Param("cgformId") String paramString2);
}
