package org.jeecg.modules.online.auth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.auth.entity.OnlAuthData;
import org.jeecg.modules.online.auth.entity.OnlAuthPage;
import org.jeecg.modules.online.auth.entity.OnlAuthRelation;
import org.jeecg.modules.online.auth.service.IOnlAuthDataService;
import org.jeecg.modules.online.auth.service.IOnlAuthPageService;
import org.jeecg.modules.online.auth.service.IOnlAuthRelationService;
import org.jeecg.modules.online.auth.vo.AuthColumnVO;
import org.jeecg.modules.online.auth.vo.AuthPageVO;
import org.jeecg.modules.online.cgform.dConstants.bConstant;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.entity.OnlCgformField;
import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("onlCgformAuthController")
@RequestMapping({"/online/cgform/api"})
public class OnlCgformAuthController {
    private static final Logger a = LoggerFactory.getLogger(OnlCgformAuthController.class);

    @Autowired
    private IOnlCgformFieldService onlCgformFieldService;

    @Autowired
    private IOnlAuthDataService onlAuthDataService;

    @Autowired
    private IOnlAuthPageService onlAuthPageService;

    @Autowired
    private IOnlCgformButtonService onlCgformButtonService;

    @Autowired
    private IOnlAuthRelationService onlAuthRelationService;

    @Autowired
    private IOnlCgformHeadService onlCgformHeadService;

    @GetMapping({"/authData/{cgformId}"})
    public Result<List<OnlAuthData>> a(@PathVariable("cgformId") String paramString) {
        Result<List<OnlAuthData>> result = new Result();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlAuthData.class).eq(OnlAuthData::getCgformId, paramString);
        List list = this.onlAuthDataService.list((Wrapper) lambdaQueryWrapper);
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }

    @PostMapping({"/authData"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlAuthData> a(@RequestBody OnlAuthData paramOnlAuthData) {
        Result<OnlAuthData> result = new Result();
        try {
            this.onlAuthDataService.save(paramOnlAuthData);
            result.success("添加成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @PutMapping({"/authData"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlAuthData> b(@RequestBody OnlAuthData paramOnlAuthData) {
        Result<OnlAuthData> result = new Result();
        this.onlAuthDataService.updateById(paramOnlAuthData);
        result.success("编辑成功！");
        return result;
    }

    @DeleteMapping({"/authData/{id}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@PathVariable("id") String paramString) {
        this.onlAuthDataService.deleteOne(paramString);
        return Result.ok("删除成功!");
    }

    @PostMapping({"/createAiTestAuthData"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> a(@RequestBody JSONObject paramJSONObject) {
        Result<?> result = new Result();
        try {
            this.onlAuthDataService.createAiTestAuthData(paramJSONObject);
            result.success("添加成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @GetMapping({"/authButton/{cgformId}"})
    public Result<Map<String, Object>> c(@PathVariable("cgformId") String paramString) {
        Result<Map<String, Object>> result = new Result();
        LambdaQueryWrapper lambdaQueryWrapper1 = Wrappers.lambdaQuery(OnlCgformButton.class)
                .eq(OnlCgformButton::getCgformHeadId, paramString)
                .eq(OnlCgformButton::getButtonStatus, "1")
                .select(OnlCgformButton::getButtonCode, OnlCgformButton::getButtonName, OnlCgformButton::getButtonStyle);
        List list1 = this.onlCgformButtonService.list(lambdaQueryWrapper1);
        LambdaQueryWrapper lambdaQueryWrapper2 = Wrappers.lambdaQuery(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, paramString)
                .eq(OnlAuthPage::getType, Integer.valueOf(2));
        List list2 = this.onlAuthPageService.list(lambdaQueryWrapper2);
        HashMap<String, Object> hashMap = new HashMap<>(5);
        hashMap.put("buttonList", list1);
        hashMap.put("authList", list2);
        result.setResult(hashMap);
        result.setSuccess(true);
        return result;
    }

    @PostMapping({"/authButton"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlAuthPage> a(@RequestBody OnlAuthPage paramOnlAuthPage) {
        Result<OnlAuthPage> result = new Result();
        try {
            String str = paramOnlAuthPage.getId();
            boolean bool = false;
            if (oConvertUtils.isNotEmpty(str)) {
                OnlAuthPage onlAuthPage = (OnlAuthPage) this.onlAuthPageService.getById(str);
                if (onlAuthPage != null) {
                    bool = true;
                    onlAuthPage.setStatus(Integer.valueOf(1));
                    this.onlAuthPageService.updateById(onlAuthPage);
                }
            }
            if (!bool) {
                paramOnlAuthPage.setStatus(Integer.valueOf(1));
                this.onlAuthPageService.save(paramOnlAuthPage);
            }
            result.setResult(paramOnlAuthPage);
            result.success("操作成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @PutMapping({"/authButton/{id}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> d(@PathVariable("id") String paramString) {
        LambdaUpdateWrapper lambdaUpdateWrapper = Wrappers.lambdaUpdate(OnlAuthPage.class)
                .eq(OnlAuthPage::getId, paramString)
                .set(OnlAuthPage::getStatus, 0);
        this.onlAuthPageService.update(lambdaUpdateWrapper);
        return Result.ok("操作成功");
    }

    @GetMapping({"/authColumn/{cgformId}"})
    public Result<List<AuthColumnVO>> e(@PathVariable("cgformId") String paramString) {
        Result<List<AuthColumnVO>> result = new Result();
        LambdaQueryWrapper<OnlCgformField> lambdaQueryWrapper1 = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, paramString)
                .orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper1);
        if (list == null || list.isEmpty())
            Result.error("未找到对应字段信息!");
        LambdaQueryWrapper lambdaQueryWrapper2 = (LambdaQueryWrapper) (Wrappers.lambdaQuery(OnlAuthPage.class)
                .eq(OnlAuthPage::getCgformId, paramString))
                .eq(OnlAuthPage::getType, 1);
        List<OnlAuthPage> list1 = this.onlAuthPageService.list(lambdaQueryWrapper2);
        ArrayList<AuthColumnVO> arrayList = new ArrayList<>();
        for (OnlCgformField onlCgformField : list) {
            AuthColumnVO authColumnVO = new AuthColumnVO(onlCgformField);
            Integer integer = Integer.valueOf(0);
            boolean bool1 = false, bool2 = false, bool3 = false;
            for (byte b = 0; b < list1.size(); b++) {
                OnlAuthPage onlAuthPage = list1.get(b);
                if (onlCgformField.getDbFieldName().equals(onlAuthPage.getCode())) {
                    integer = onlAuthPage.getStatus();
                    if (onlAuthPage.getPage().intValue() == 3 && onlAuthPage.getControl().intValue() == 5)
                        bool1 = true;
                    if (onlAuthPage.getPage().intValue() == 5)
                        if (onlAuthPage.getControl().intValue() == 5) {
                            bool2 = true;
                        } else if (onlAuthPage.getControl().intValue() == 3) {
                            bool3 = true;
                        }
                }
            }
            authColumnVO.setStatus(integer);
            authColumnVO.setListShow(bool1);
            authColumnVO.setFormShow(bool2);
            authColumnVO.setFormEditable(bool3);
            arrayList.add(authColumnVO);
        }
        result.setResult(arrayList);
        Result.ok("加载字段权限数据完成");
        return result;
    }

    @PutMapping({"/authColumn"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> a(@RequestBody AuthColumnVO paramAuthColumnVO) {
        Result<?> result = new Result();
        try {
            if (paramAuthColumnVO.getStatus().intValue() == 1) {
                this.onlAuthPageService.enableAuthColumn(paramAuthColumnVO);
            } else {
                this.onlAuthPageService.disableAuthColumn(paramAuthColumnVO);
            }
            result.success("操作成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @PostMapping({"/authColumn"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@RequestBody AuthColumnVO paramAuthColumnVO) {
        Result<?> result = new Result();
        try {
            this.onlAuthPageService.switchAuthColumn(paramAuthColumnVO);
            result.success("操作成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @GetMapping({"/authPage/{cgformId}/{type}"})
    public Result<List<AuthPageVO>> a(@PathVariable("cgformId") String paramString, @PathVariable("type") Integer paramInteger) {
        Result<List<AuthPageVO>> result = new Result();
        List list = this.onlAuthPageService.queryAuthByFormId(paramString, paramInteger.intValue());
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }

    @GetMapping({"/validAuthData/{cgformId}"})
    public Result<List<OnlAuthData>> f(@PathVariable("cgformId") String paramString) {
        Result<List<OnlAuthData>> result = new Result();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlAuthData.class).eq(OnlAuthData::getCgformId, paramString)
                .eq(OnlAuthData::getStatus, 1)
                .select(OnlAuthData::getId, OnlAuthData::getRuleName);
        List list = this.onlAuthDataService.list(lambdaQueryWrapper);
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }

    @GetMapping({"/roleAuth"})
    public Result<List<OnlAuthRelation>> a(@RequestParam("roleId") String paramString1, @RequestParam("cgformId") String paramString2, @RequestParam("type") Integer paramInteger, @RequestParam("authMode") String paramString3) {
        Result<List<OnlAuthRelation>> result = new Result();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlAuthRelation.class)
                .eq(OnlAuthRelation::getRoleId, paramString1)
                .eq(OnlAuthRelation::getCgformId, paramString2)
                .eq(OnlAuthRelation::getType, paramInteger)
                .eq(OnlAuthRelation::getAuthMode, paramString3)
                .select(OnlAuthRelation::getAuthId);
        List list = this.onlAuthRelationService.list(lambdaQueryWrapper);
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }

    @PostMapping({"/roleColumnAuth/{roleId}/{cgformId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> a(@PathVariable("roleId") String paramString1, @PathVariable("cgformId") String paramString2, @RequestBody JSONObject paramJSONObject) {
        Result<?> result = new Result();
        JSONArray jSONArray = paramJSONObject.getJSONArray("authId");
        String str = paramJSONObject.getString("authMode");
        List list = jSONArray.toJavaList(String.class);
        this.onlAuthRelationService.saveRoleAuth(paramString1, paramString2, 1, str, list);
        result.setSuccess(true);
        return result;
    }

    @PostMapping({"/roleButtonAuth/{roleId}/{cgformId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> b(@PathVariable("roleId") String paramString1, @PathVariable("cgformId") String paramString2, @RequestBody JSONObject paramJSONObject) {
        Result<?> result = new Result();
        JSONArray jSONArray = paramJSONObject.getJSONArray("authId");
        String str = paramJSONObject.getString("authMode");
        List list = jSONArray.toJavaList(String.class);
        this.onlAuthRelationService.saveRoleAuth(paramString1, paramString2, 2, str, list);
        result.setSuccess(true);
        return result;
    }

    @PostMapping({"/roleDataAuth/{roleId}/{cgformId}"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<?> c(@PathVariable("roleId") String paramString1, @PathVariable("cgformId") String paramString2, @RequestBody JSONObject paramJSONObject) {
        Result<?> result = new Result();
        JSONArray jSONArray = paramJSONObject.getJSONArray("authId");
        String str = paramJSONObject.getString("authMode");
        List list = jSONArray.toJavaList(String.class);
        this.onlAuthRelationService.saveRoleAuth(paramString1, paramString2, 3, str, list);
        result.setSuccess(true);
        return result;
    }

    @GetMapping({"/getAuthColumn/{desformCode}"})
    public Result<List<AuthColumnVO>> g(@PathVariable("desformCode") String paramString) {
        OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, paramString));
        if (onlCgformHead == null)
            Result.error("未找到对应字段信息!");
        Result<List<AuthColumnVO>> result = new Result();
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformField.class)
                .eq(OnlCgformField::getCgformHeadId, onlCgformHead.getId())
                .orderByAsc(OnlCgformField::getOrderNum);
        List<OnlCgformField> list = this.onlCgformFieldService.list(lambdaQueryWrapper);
        if (list == null || list.isEmpty())
            Result.error("未找到对应字段信息!");
        ArrayList<AuthColumnVO> arrayList = new ArrayList<>();
        for (OnlCgformField onlCgformField : list) {
            if (bConstant.i(onlCgformField.getDbFieldName()))
                continue;
            AuthColumnVO authColumnVO = new AuthColumnVO(onlCgformField);
            authColumnVO.setTableName(onlCgformHead.getTableName());
            authColumnVO.setTableNameTxt(onlCgformHead.getTableTxt());
            authColumnVO.setIsMain(Boolean.valueOf(true));
            arrayList.add(authColumnVO);
        }
        if (oConvertUtils.isNotEmpty(onlCgformHead.getSubTableStr()))
            for (String str : onlCgformHead.getSubTableStr().split(",")) {
                OnlCgformHead onlCgformHead1 = this.onlCgformHeadService.getOne(Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, str));
                if (onlCgformHead1 != null) {
                    LambdaQueryWrapper lambdaQueryWrapper1 = Wrappers.lambdaQuery(OnlCgformField.class).eq(OnlCgformField::getCgformHeadId, onlCgformHead1.getId());
                    List<OnlCgformField> list1 = this.onlCgformFieldService.list(lambdaQueryWrapper1);
                    if (list1 != null)
                        for (OnlCgformField onlCgformField : list1) {
                            if (bConstant.i(onlCgformField.getDbFieldName()))
                                continue;
                            AuthColumnVO authColumnVO = new AuthColumnVO(onlCgformField);
                            authColumnVO.setTableName(onlCgformHead1.getTableName());
                            authColumnVO.setTableNameTxt(onlCgformHead1.getTableTxt());
                            authColumnVO.setIsMain(Boolean.valueOf(false));
                            arrayList.add(authColumnVO);
                        }
                }
            }
        result.setResult(arrayList);
        Result.ok("加载字段权限数据完成");
        return result;
    }
}
