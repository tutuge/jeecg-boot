 package org.jeecg.modules.online.cgreport.controller;

 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
 import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

 import java.util.Arrays;
 import java.util.List;
 import jakarta.servlet.http.HttpServletRequest;
 import org.jeecg.common.api.vo.Result;
 import org.jeecg.common.exception.JeecgBootException;
 import org.jeecg.common.system.query.QueryGenerator;
 import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
 import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
 import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
 import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
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

 @RestController("onlCgreportItemController")
 @RequestMapping({"/online/cgreport/item"})
 public class c {
   private static final Logger a = LoggerFactory.getLogger(c.class);

   @Autowired
   private IOnlCgreportItemService onlCgreportItemService;

   @Autowired
   private IOnlCgreportHeadService onlCgreportHeadService;

   @GetMapping({"/listByHeadId"})
   public Result<?> a(@RequestParam("headId") String paramString) {
     QueryWrapper queryWrapper = new QueryWrapper();
     queryWrapper.eq("cgrhead_id", paramString);
     queryWrapper.orderByAsc("order_num");
     List list = this.onlCgreportItemService.list((Wrapper)queryWrapper);
     Result<?> result = new Result();
     result.setSuccess(true);
     result.setResult(list);
     return result;
   }

   @GetMapping({"/listByHeadCode"})
   public Result<?> b(@RequestParam("headCode") String paramString) {
     LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
     lambdaQueryWrapper.eq(OnlCgreportHead::getCode, paramString);
     OnlCgreportHead onlCgreportHead = (OnlCgreportHead)this.onlCgreportHeadService.getOne((Wrapper)lambdaQueryWrapper);
     if (onlCgreportHead == null)
       throw new JeecgBootException("该报表不存在");
     QueryWrapper queryWrapper = new QueryWrapper();
     queryWrapper.eq("cgrhead_id", onlCgreportHead.getId());
     queryWrapper.orderByAsc("order_num");
     List list = this.onlCgreportItemService.list((Wrapper)queryWrapper);
     Result<?> result = new Result();
     result.setSuccess(true);
     result.setResult(list);
     return result;
   }

   @GetMapping({"/list"})
   public Result<IPage<OnlCgreportItem>> a(OnlCgreportItem paramOnlCgreportItem, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
     Result<IPage<OnlCgreportItem>> result = new Result();
     QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgreportItem, paramHttpServletRequest.getParameterMap());
     Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
     IPage iPage = this.onlCgreportItemService.page((IPage)page, (Wrapper)queryWrapper);
     result.setSuccess(true);
     result.setResult(iPage);
     return result;
   }

   @PostMapping({"/add"})
   public Result<?> a(@RequestBody OnlCgreportItem paramOnlCgreportItem) {
     this.onlCgreportItemService.save(paramOnlCgreportItem);
     return Result.ok("添加成功!");
   }

   @PutMapping({"/edit"})
   public Result<?> b(@RequestBody OnlCgreportItem paramOnlCgreportItem) {
     this.onlCgreportItemService.updateById(paramOnlCgreportItem);
     return Result.ok("编辑成功!");
   }

   @DeleteMapping({"/delete"})
   public Result<?> c(@RequestParam(name = "id", required = true) String paramString) {
     this.onlCgreportItemService.removeById(paramString);
     return Result.ok("删除成功!");
   }

   @DeleteMapping({"/deleteBatch"})
   public Result<?> d(@RequestParam(name = "ids", required = true) String paramString) {
     this.onlCgreportItemService.removeByIds(Arrays.asList(paramString.split(",")));
     return Result.ok("批量删除成功!");
   }

   @GetMapping({"/queryById"})
   public Result<OnlCgreportItem> e(@RequestParam(name = "id", required = true) String paramString) {
     Result<OnlCgreportItem> result = new Result();
     OnlCgreportItem onlCgreportItem = (OnlCgreportItem)this.onlCgreportItemService.getById(paramString);
     result.setResult(onlCgreportItem);
     result.setSuccess(true);
     return result;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\a\c.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
