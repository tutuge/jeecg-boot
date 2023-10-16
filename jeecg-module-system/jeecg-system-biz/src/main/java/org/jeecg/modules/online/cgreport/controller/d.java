package org.jeecg.modules.online.cgreport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController("onlCgreportParamController")
@RequestMapping({"/online/cgreport/param"})
public class d {
    private static final Logger a = LoggerFactory.getLogger(d.class);

    @Autowired
    private IOnlCgreportParamService onlCgreportParamService;

    @GetMapping({"/listByHeadId"})
    public Result<?> a(@RequestParam("headId") String paramString) {
        QueryWrapper<OnlCgreportParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cgrhead_id", paramString);
        queryWrapper.orderByAsc("order_num");
        List<OnlCgreportParam> list = this.onlCgreportParamService.list(queryWrapper);
        Result<List<OnlCgreportParam>> result = new Result();
        result.setSuccess(true);
        result.setResult(list);
        return result;
    }

    @GetMapping({"/list"})
    public Result<IPage<OnlCgreportParam>> a(OnlCgreportParam paramOnlCgreportParam, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
        Result<IPage<OnlCgreportParam>> result = new Result();
        QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgreportParam, paramHttpServletRequest.getParameterMap());
        Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
        IPage iPage = this.onlCgreportParamService.page((IPage) page, queryWrapper);
        result.setSuccess(true);
        result.setResult(iPage);
        return result;
    }

    @PostMapping({"/add"})
    public Result<?> a(@RequestBody OnlCgreportParam paramOnlCgreportParam) {
        this.onlCgreportParamService.save(paramOnlCgreportParam);
        return Result.ok("添加成功!");
    }

    @PutMapping({"/edit"})
    public Result<?> b(@RequestBody OnlCgreportParam paramOnlCgreportParam) {
        this.onlCgreportParamService.updateById(paramOnlCgreportParam);
        return Result.ok("编辑成功!");
    }

    @DeleteMapping({"/delete"})
    public Result<?> b(@RequestParam(name = "id", required = true) String paramString) {
        this.onlCgreportParamService.removeById(paramString);
        return Result.ok("删除成功!");
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<?> c(@RequestParam(name = "ids", required = true) String paramString) {
        this.onlCgreportParamService.removeByIds(Arrays.asList(paramString.split(",")));
        return Result.ok("批量删除成功!");
    }

    @GetMapping({"/queryById"})
    public Result<OnlCgreportParam> d(@RequestParam(name = "id", required = true) String paramString) {
        Result<OnlCgreportParam> result = new Result();
        OnlCgreportParam onlCgreportParam = this.onlCgreportParamService.getById(paramString);
        result.setResult(onlCgreportParam);
        return result;
    }
}
