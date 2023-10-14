 package org.jeecg.modules.online.cgform.controller;

 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
 import java.util.Arrays;
 import java.util.List;
 import jakarta.servlet.http.HttpServletRequest;
 import org.jeecg.common.api.vo.Result;
 import org.jeecg.common.constant.CommonConstant;
 import org.jeecg.common.system.query.QueryGenerator;
 import org.jeecg.modules.online.cgform.entity.OnlCgformIndex;
 import org.jeecg.modules.online.cgform.service.IOnlCgformIndexService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.PutMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;

 @RestController("onlCgformIndexController")
 @RequestMapping({"/online/cgform/index"})
 public class e {
   private static final Logger a = LoggerFactory.getLogger(e.class);

   @Autowired
   private IOnlCgformIndexService onlCgformIndexService;

   @GetMapping({"/listByHeadId"})
   public Result<?> a(@RequestParam("headId") String paramString) {
     QueryWrapper queryWrapper = new QueryWrapper();
     queryWrapper.eq("cgform_head_id", paramString);
     queryWrapper.eq("del_flag", CommonConstant.DEL_FLAG_0);
     queryWrapper.orderByDesc("create_time");
     List list = this.onlCgformIndexService.list((Wrapper)queryWrapper);
     return Result.ok(list);
   }

   @GetMapping({"/list"})
   public Result<IPage<OnlCgformIndex>> a(OnlCgformIndex paramOnlCgformIndex, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
     Result<IPage<OnlCgformIndex>> result = new Result();
     QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgformIndex, paramHttpServletRequest.getParameterMap());
     Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
     IPage iPage = this.onlCgformIndexService.page((IPage)page, (Wrapper)queryWrapper);
     result.setSuccess(true);
     result.setResult(iPage);
     return result;
   }

   @PostMapping({"/add"})
   public Result<OnlCgformIndex> a(@RequestBody OnlCgformIndex paramOnlCgformIndex) {
     Result<OnlCgformIndex> result = new Result();
     try {
       this.onlCgformIndexService.save(paramOnlCgformIndex);
       result.success("添加成功！");
     } catch (Exception exception) {
       a.error(exception.getMessage(), exception);
       result.error500("操作失败");
     }
     return result;
   }

   @PutMapping({"/edit"})
   public Result<OnlCgformIndex> b(@RequestBody OnlCgformIndex paramOnlCgformIndex) {
     Result<OnlCgformIndex> result = new Result();
     OnlCgformIndex onlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramOnlCgformIndex.getId());
     if (onlCgformIndex == null) {
       result.error500("未找到对应实体");
     } else {
       boolean bool = this.onlCgformIndexService.updateById(paramOnlCgformIndex);
       if (bool)
         result.success("修改成功!");
     }
     return result;
   }

   @DeleteMapping({"/delete"})
   public Result<OnlCgformIndex> b(@RequestParam(name = "id", required = true) String paramString) {
     Result<OnlCgformIndex> result = new Result();
     OnlCgformIndex onlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramString);
     if (onlCgformIndex == null) {
       result.error500("未找到对应实体");
     } else {
       boolean bool = this.onlCgformIndexService.removeById(paramString);
       if (bool)
         result.success("删除成功!");
     }
     return result;
   }

   @DeleteMapping({"/deleteBatch"})
   public Result<OnlCgformIndex> c(@RequestParam(name = "ids", required = true) String paramString) {
     Result<OnlCgformIndex> result = new Result();
     if (paramString == null || "".equals(paramString.trim())) {
       result.error500("参数不识别！");
     } else {
       this.onlCgformIndexService.removeByIds(Arrays.asList(paramString.split(",")));
       result.success("删除成功!");
     }
     return result;
   }

   @GetMapping({"/queryById"})
   public Result<OnlCgformIndex> d(@RequestParam(name = "id", required = true) String paramString) {
     Result<OnlCgformIndex> result = new Result();
     OnlCgformIndex onlCgformIndex = (OnlCgformIndex)this.onlCgformIndexService.getById(paramString);
     if (onlCgformIndex == null) {
       result.error500("未找到对应实体");
     } else {
       result.setResult(onlCgformIndex);
       result.setSuccess(true);
     }
     return result;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\c\e.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
