package org.jeecg.modules.online.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.mapper.OnlAuthPageMapper;
import org.jeecg.modules.online.auth.mapper.OnlAuthRelationMapper;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("onlAuthPageServiceImpl")
public class onlAuthPageServiceImpl extends ServiceImpl<OnlAuthPageMapper, OnlAuthPage> implements IOnlAuthPageService {
    @Autowired
    private OnlAuthRelationMapper onlAuthRelationMapper;

    public void disableAuthColumn(AuthColumnVO authColumnVO) {
        LambdaUpdateWrapper lambdaUpdateWrapper = Wrappers.lambdaUpdate(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, authColumnVO.getCgformId())
                .eq(OnlAuthPage::getCode, authColumnVO.getCode())
                .eq(OnlAuthPage::getType, Integer.valueOf(1))
                .set(OnlAuthPage::getStatus, Integer.valueOf(0));
        update(lambdaUpdateWrapper);
    }

    @Transactional
    public void enableAuthColumn(AuthColumnVO authColumnVO) {
        String str1 = authColumnVO.getCgformId();
        String str2 = authColumnVO.getCode();
        LambdaQueryWrapper lambdaQueryWrapper =  Wrappers.lambdaQuery(OnlAuthPage.class).eq(OnlAuthPage::getCgformId, str1)
                .eq(OnlAuthPage::getCode, str2)
                .eq(OnlAuthPage::getType, Integer.valueOf(1));
        List<OnlAuthPage> list = list( lambdaQueryWrapper);
        if (list != null && list.size() > 0) {
            LambdaUpdateWrapper lambdaUpdateWrapper = Wrappers.lambdaUpdate(OnlAuthPage.class).eq(OnlAuthPage::getCgformId, str1)
                    .eq(OnlAuthPage::getCode, str2)
                    .eq(OnlAuthPage::getType, Integer.valueOf(1))
                    .set(OnlAuthPage::getStatus, Integer.valueOf(1));
            update(lambdaUpdateWrapper);
        } else {
            list = new ArrayList<>();
            list.add(new OnlAuthPage(str1, str2, 3, 5));
            list.add(new OnlAuthPage(str1, str2, 5, 5));
            list.add(new OnlAuthPage(str1, str2, 5, 3));
            saveBatch(list);
        }
    }

    public void switchAuthColumn(AuthColumnVO authColumnVO) {
        String str1 = authColumnVO.getCgformId();
        String str2 = authColumnVO.getCode();
        int i = authColumnVO.getSwitchFlag();
        if (i == 1) {
            switchListShow(str1, str2, authColumnVO.isListShow());
        } else if (i == 2) {
            switchFormShow(str1, str2, authColumnVO.isFormShow());
        } else if (i == 3) {
            switchFormEditable(str1, str2, authColumnVO.isFormEditable());
        }
    }

    @Transactional
    public void switchFormShow(String cgformId, String code, boolean flag) {
        a(cgformId, code, 5, 5, flag);
    }

    @Transactional
    public void switchFormEditable(String cgformId, String code, boolean flag) {
        a(cgformId, code, 3, 5, flag);
    }

    @Transactional
    public void switchListShow(String cgformId, String code, boolean flag) {
        a(cgformId, code, 5, 3, flag);
    }

    public List<AuthPageVO> queryRoleAuthByFormId(String roleId, String cgformId, int type) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleAuthByFormId(roleId, cgformId, type);
    }

    public List<AuthPageVO> queryRoleDataAuth(String roleId, String cgformId) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleDataAuth(roleId, cgformId);
    }

    public List<AuthPageVO> queryAuthByFormId(String cgformId, int type) {
        if (type == 1)
            return ((OnlAuthPageMapper) this.baseMapper).queryAuthColumnByFormId(cgformId);
        return ((OnlAuthPageMapper) this.baseMapper).queryAuthButtonByFormId(cgformId);
    }

    public List<String> queryRoleNoAuthCode(String cgformId, Integer control, Integer page) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str = loginUser.getId();
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleNoAuthCode(str, cgformId, control, page, null);
    }

    public List<String> queryFormDisabledCode(String cgformId) {
        return queryRoleNoAuthCode(cgformId, Integer.valueOf(3), Integer.valueOf(5));
    }

    public List<String> queryHideCode(String userId, String cgformId, boolean isList) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleNoAuthCode(userId, cgformId, Integer.valueOf(5), Integer.valueOf(isList ? 3 : 5), null);
    }

    public List<String> queryListHideColumn(String userId, String cgformId) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleNoAuthCode(userId, cgformId, Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(1));
    }

    public List<String> queryFormHideColumn(String userId, String cgformId) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleNoAuthCode(userId, cgformId, Integer.valueOf(5), Integer.valueOf(5), Integer.valueOf(1));
    }

    public List<String> queryFormHideButton(String userId, String cgformId) {
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleNoAuthCode(userId, cgformId, Integer.valueOf(5), Integer.valueOf(5), Integer.valueOf(2));
    }

    public List<String> queryHideCode(String cgformId, boolean isList) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String str = loginUser.getId();
        return ((OnlAuthPageMapper) this.baseMapper).queryRoleNoAuthCode(str, cgformId, Integer.valueOf(5), Integer.valueOf(isList ? 3 : 5), null);
    }

    public List<String> queryListHideButton(String userId, String cgformId) {
        if (userId == null) {
            LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            userId = loginUser.getId();
        }
        return this.baseMapper.queryRoleNoAuthCode(userId, cgformId, 5, 3, 2);
    }

    private void a(String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlAuthPage.class).eq(OnlAuthPage::getCgformId, paramString1)
                .eq(OnlAuthPage::getCode, paramString2)
                .eq(OnlAuthPage::getControl, paramInt1)
                .eq(OnlAuthPage::getPage, paramInt2)
                .eq(OnlAuthPage::getType, 1);
        OnlAuthPage onlAuthPage = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (paramBoolean) {
            if (onlAuthPage == null) {
                OnlAuthPage onlAuthPage1 = new OnlAuthPage();
                onlAuthPage1.setCgformId(paramString1);
                onlAuthPage1.setCode(paramString2);
                onlAuthPage1.setControl(paramInt1);
                onlAuthPage1.setPage(Integer.valueOf(paramInt2));
                onlAuthPage1.setType(Integer.valueOf(1));
                onlAuthPage1.setStatus(1);
                this.baseMapper.insert(onlAuthPage1);
            } else if (onlAuthPage.getStatus() == 0) {
                onlAuthPage.setStatus(1);
                this.baseMapper.updateById(onlAuthPage);
            }
        } else if (!paramBoolean && onlAuthPage != null) {
            String str = onlAuthPage.getId();
            this.baseMapper.deleteById(str);
            this.onlAuthRelationMapper.delete(Wrappers.lambdaQuery(OnlAuthRelation.class).eq(OnlAuthRelation::getAuthId, str));
        }
    }
}
