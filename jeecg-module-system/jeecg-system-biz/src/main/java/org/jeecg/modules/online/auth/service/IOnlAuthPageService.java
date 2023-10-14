package org.jeecg.modules.online.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;

import java.util.List;

public interface IOnlAuthPageService extends IService<OnlAuthPage> {
    void disableAuthColumn(AuthColumnVO paramAuthColumnVO);

    void enableAuthColumn(AuthColumnVO paramAuthColumnVO);

    void switchAuthColumn(AuthColumnVO paramAuthColumnVO);

    void switchFormShow(String paramString1, String paramString2, boolean paramBoolean);

    void switchFormEditable(String paramString1, String paramString2, boolean paramBoolean);

    void switchListShow(String paramString1, String paramString2, boolean paramBoolean);

    List<AuthPageVO> queryRoleAuthByFormId(String paramString1, String paramString2, int paramInt);

    List<AuthPageVO> queryRoleDataAuth(String paramString1, String paramString2);

    List<AuthPageVO> queryAuthByFormId(String paramString, int paramInt);

    List<String> queryRoleNoAuthCode(String paramString, Integer paramInteger1, Integer paramInteger2);

    List<String> queryFormDisabledCode(String paramString);

    List<String> queryHideCode(String paramString1, String paramString2, boolean paramBoolean);

    List<String> queryListHideColumn(String paramString1, String paramString2);

    List<String> queryFormHideColumn(String paramString1, String paramString2);

    List<String> queryFormHideButton(String paramString1, String paramString2);

    List<String> queryHideCode(String paramString, boolean paramBoolean);

    List<String> queryListHideButton(String paramString1, String paramString2);
}
