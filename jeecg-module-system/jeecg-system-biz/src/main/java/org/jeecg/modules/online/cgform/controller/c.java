 package org.jeecg.modules.online.cgform.controller;

 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
 import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.baomidou.mybatisplus.core.toolkit.Wrappers;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

 import java.util.Arrays;
 import java.util.List;
 import jakarta.servlet.http.HttpServletRequest;
 import org.jeecg.common.api.vo.Result;
 import org.jeecg.common.system.query.QueryGenerator;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.cgform.entity.OnlCgformHead;
 import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
 import org.jeecg.modules.online.cgform.service.IOnlCgformHeadService;
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

 @RestController("onlCgformFieldController")
 @RequestMapping({"/online/cgform/field"})
 public class c {
   private static final Logger a = LoggerFactory.getLogger(c.class);

   @Autowired
   private IOnlCgformHeadService onlCgformHeadService;

   @Autowired
   private IOnlCgformFieldService onlCgformFieldService;

   @GetMapping({"/listByHeadCode"})
   public Result<?> a(@RequestParam("headCode") String paramString) {
     LambdaQueryWrapper lambdaQueryWrapper = Wrappers.lambdaQuery(OnlCgformHead.class).eq(OnlCgformHead::getTableName, paramString);
     OnlCgformHead onlCgformHead = this.onlCgformHeadService.getOne((Wrapper)lambdaQueryWrapper);
     if (onlCgformHead == null)
       return Result.error("表名[" + paramString + "]不存在！");
     return b(onlCgformHead.getId());
   }

   @GetMapping({"/listByHeadId"})
   public Result<?> b(@RequestParam("headId") String paramString) {
     QueryWrapper queryWrapper = new QueryWrapper();
     queryWrapper.eq("cgform_head_id", paramString);
     queryWrapper.orderByAsc("order_num");
     List list = this.onlCgformFieldService.list((Wrapper)queryWrapper);
     return Result.ok(list);
   }

   @GetMapping({"/list"})
   public Result<IPage<OnlCgformField>> a(OnlCgformField paramOnlCgformField, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
     Result<IPage<OnlCgformField>> result = new Result();
     QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgformField, paramHttpServletRequest.getParameterMap());
     Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
     IPage iPage = this.onlCgformFieldService.page((IPage)page, queryWrapper);
     result.setSuccess(true);
     result.setResult(iPage);
     return result;
   }

   @PostMapping({"/add"})
   public Result<OnlCgformField> a(@RequestBody OnlCgformField paramOnlCgformField) {
     Result<OnlCgformField> result = new Result();
     try {
       this.onlCgformFieldService.save(paramOnlCgformField);
       result.success("添加成功！");
     } catch (Exception exception) {
       a.error(exception.getMessage(), exception);
       result.error500("操作失败");
     }
     return result;
   }

   @PutMapping({"/edit"})
   public Result<OnlCgformField> b(@RequestBody OnlCgformField paramOnlCgformField) {
     Result<OnlCgformField> result = new Result();
     OnlCgformField onlCgformField = (OnlCgformField)this.onlCgformFieldService.getById(paramOnlCgformField.getId());
     if (onlCgformField == null) {
       result.error500("未找到对应实体");
     } else {
       boolean bool = this.onlCgformFieldService.updateById(paramOnlCgformField);
       if (bool)
         result.success("修改成功!");
     }
     return result;
   }

   @DeleteMapping({"/delete"})
   public Result<OnlCgformField> c(@RequestParam(name = "id", required = true) String paramString) {
     Result<OnlCgformField> result = new Result();
     OnlCgformField onlCgformField = (OnlCgformField)this.onlCgformFieldService.getById(paramString);
     if (onlCgformField == null) {
       result.error500("未找到对应实体");
     } else {
       boolean bool = this.onlCgformFieldService.removeById(paramString);
       if (bool)
         result.success("删除成功!");
     }
     return result;
   }

   @DeleteMapping({"/deleteBatch"})
   public Result<OnlCgformField> d(@RequestParam(name = "ids", required = true) String paramString) {
     Result<OnlCgformField> result = new Result();
     if (paramString == null || "".equals(paramString.trim())) {
       result.error500("参数不识别！");
     } else {
       this.onlCgformFieldService.removeByIds(Arrays.asList(paramString.split(",")));
       result.success("删除成功!");
     }
     return result;
   }

   @GetMapping({"/queryById"})
   public Result<OnlCgformField> e(@RequestParam(name = "id", required = true) String paramString) {
     Result<OnlCgformField> result = new Result();
     OnlCgformField onlCgformField = (OnlCgformField)this.onlCgformFieldService.getById(paramString);
     if (onlCgformField == null) {
       result.error500("未找到对应实体");
     } else {
       result.setResult(onlCgformField);
       result.setSuccess(true);
     }
     return result;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\c\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
