package org.jeecg.modules.online.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.vo.AuthPageVO;

import java.util.List;

public interface OnlAuthPageMapper extends BaseMapper<OnlAuthPage> {
    List<AuthPageVO> queryRoleAuthByFormId(@Param("roleId") String paramString1, @Param("cgformId") String paramString2, @Param("type") int paramInt);

    List<AuthPageVO> queryAuthColumnByFormId(@Param("cgformId") String paramString);

    List<AuthPageVO> queryAuthButtonByFormId(@Param("cgformId") String paramString);

    List<AuthPageVO> queryRoleDataAuth(@Param("roleId") String paramString1, @Param("cgformId") String paramString2);

    List<String> queryRoleNoAuthCode(@Param("userId") String paramString1, @Param("cgformId") String paramString2, @Param("control") Integer paramInteger1, @Param("page") Integer paramInteger2, @Param("type") Integer paramInteger3);
}
