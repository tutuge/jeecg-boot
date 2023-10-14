package org.jeecg.modules.online.cgform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgform.entity.OnlCgformButton;
import org.jeecg.modules.online.cgform.service.IOnlCgformButtonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController("onlCgformButtonController")
@RequestMapping({"/online/cgform/button"})
public class b {
    private static final Logger a = LoggerFactory.getLogger(b.class);

    @Autowired
    private IOnlCgformButtonService onlCgformButtonService;

    @GetMapping({"/list/{code}"})
    public Result<IPage<OnlCgformButton>> a(OnlCgformButton paramOnlCgformButton, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest, @PathVariable("code") String paramString) {
        Result<IPage<OnlCgformButton>> result = new Result();
        paramOnlCgformButton.setCgformHeadId(paramString);
        QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgformButton, paramHttpServletRequest.getParameterMap());
        Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
        IPage iPage = this.onlCgformButtonService.page((IPage) page, (Wrapper) queryWrapper);
        result.setSuccess(true);
        result.setResult(iPage);
        return result;
    }

    @PostMapping({"/add"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlCgformButton> a(@RequestBody OnlCgformButton paramOnlCgformButton) {
        Result<OnlCgformButton> result = new Result();
        try {
            this.onlCgformButtonService.save(paramOnlCgformButton);
            result.success("添加成功！");
        } catch (Exception exception) {
            a.error(exception.getMessage(), exception);
            result.error500("操作失败");
        }
        return result;
    }

    @PostMapping({"/aitest"})
    public Result<OnlCgformButton> a(@RequestBody JSONArray paramJSONArray) {
        Result<OnlCgformButton> result = new Result();
        try {
            for (byte b1 = 0; b1 < paramJSONArray.size(); b1++) {
                JSONObject jSONObject = paramJSONArray.getJSONObject(b1);
                OnlCgformButton onlCgformButton = (OnlCgformButton) JSONObject.parseObject(jSONObject.toJSONString(), OnlCgformButton.class);

                this.onlCgformButtonService.saveButton(onlCgformButton);
            }

            result.success("添加成功！");

        } catch (Exception exception) {

            a.error(exception.getMessage(), exception);

            result.error500("操作失败");
        }

        return result;
    }

    @PutMapping({"/edit"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlCgformButton> b(@RequestBody OnlCgformButton paramOnlCgformButton) {

        Result<OnlCgformButton> result = new Result();

        OnlCgformButton onlCgformButton = (OnlCgformButton) this.onlCgformButtonService.getById(paramOnlCgformButton.getId());

        if (onlCgformButton == null) {

            result.error500("未找到对应实体");
        } else {

            boolean bool = this.onlCgformButtonService.updateById(paramOnlCgformButton);

            if (bool)
                result.success("修改成功!");
        }

        return result;
    }

    @DeleteMapping({"/delete"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlCgformButton> a(@RequestParam(name = "id", required = true) String paramString) {

        Result<OnlCgformButton> result = new Result();

        OnlCgformButton onlCgformButton = (OnlCgformButton) this.onlCgformButtonService.getById(paramString);

        if (onlCgformButton == null) {

            result.error500("未找到对应实体");
        } else {

            boolean bool = this.onlCgformButtonService.removeById(paramString);

            if (bool)
                result.success("删除成功!");
        }

        return result;
    }

    @DeleteMapping({"/deleteBatch"})
    @CacheEvict(value = {"sys:cache:online:list", "sys:cache:online:form"}, allEntries = true, beforeInvocation = true)
    public Result<OnlCgformButton> b(@RequestParam(name = "ids", required = true) String paramString) {

        Result<OnlCgformButton> result = new Result();

        if (paramString == null || "".equals(paramString.trim())) {

            result.error500("参数不识别！");
        } else {

            this.onlCgformButtonService.removeByIds(Arrays.asList(paramString.split(",")));

            result.success("删除成功!");
        }

        return result;
    }

    @GetMapping({"/queryById"})
    public Result<OnlCgformButton> c(@RequestParam(name = "id", required = true) String paramString) {

        Result<OnlCgformButton> result = new Result();

        OnlCgformButton onlCgformButton = this.onlCgformButtonService.getById(paramString);

        if (onlCgformButton == null) {

            result.error500("未找到对应实体");
        } else {

            result.setResult(onlCgformButton);

            result.setSuccess(true);
        }

        return result;
    }
}
