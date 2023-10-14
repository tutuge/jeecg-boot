 package org.jeecg.modules.online.cgreport.a;

 import com.baomidou.mybatisplus.core.conditions.Wrapper;
 import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
 import com.baomidou.mybatisplus.core.metadata.IPage;
 import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import jakarta.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;
 import org.jeecg.common.api.vo.Result;
 import org.jeecg.common.aspect.annotation.AutoLowApp;
 import org.jeecg.common.constant.enums.LowAppAopEnum;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.system.query.QueryGenerator;
 import org.jeecg.common.system.vo.DynamicDataSourceModel;
 import org.jeecg.common.util.SqlInjectionUtil;
 import org.jeecg.modules.base.service.BaseCommonService;
 import org.jeecg.modules.online.cgform.dConstants.bConstant;
 import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
 import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
 import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;
 import org.jeecg.modules.online.cgreport.model.OnlCgreportModel;
 import org.jeecg.modules.online.cgreport.service.IOnlCgreportHeadService;
 import org.jeecg.modules.online.cgreport.service.IOnlCgreportItemService;
 import org.jeecg.modules.online.cgreport.service.IOnlCgreportParamService;
 import org.jeecg.modules.online.config.c.a;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.cache.annotation.CacheEvict;
 import org.springframework.context.annotation.Lazy;
 import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.PutMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.RestController;

 @RestController("onlCgreportHeadController")
 @RequestMapping({"/online/cgreport/head"})
 public class b {
   private static final Logger a = LoggerFactory.getLogger(b.class);

   @Lazy
   @Autowired
   private ISysBaseAPI sysBaseAPI;

   @Autowired
   private IOnlCgreportHeadService onlCgreportHeadService;

   @Autowired
   private IOnlCgreportParamService onlCgreportParamService;

   @Autowired
   private IOnlCgreportItemService onlCgreportItemService;

   @Autowired
   private BaseCommonService baseCommonService;

   @Autowired
   private a onlReportQueryBlackListHandler;

   @GetMapping({"/parseSql"})
   public Result<?> a(@RequestParam(name = "sql") String paramString1, @RequestParam(name = "dbKey", required = false) String paramString2) {
     if (StringUtils.isNotBlank(paramString2)) {
       DynamicDataSourceModel dynamicDataSourceModel = this.sysBaseAPI.getDynamicDbSourceByCode(paramString2);
       if (dynamicDataSourceModel == null)
         return Result.error("数据源不存在");
     }
     HashMap<Object, Object> hashMap = new HashMap<>(5);
     ArrayList<OnlCgreportItem> arrayList = new ArrayList();
     ArrayList<OnlCgreportParam> arrayList1 = new ArrayList();
     List list1 = null;
     List list2 = null;
     try {
       this.baseCommonService.addLog("Online报表，sql解析：" + paramString1, Integer.valueOf(2), Integer.valueOf(2));
       if (!this.onlReportQueryBlackListHandler.isPass(paramString1))
         return Result.error(this.onlReportQueryBlackListHandler.getError());
       SqlInjectionUtil.specialFilterContentForOnlineReport(paramString1);
       list1 = this.onlCgreportHeadService.getSqlFields(paramString1, paramString2);
       list2 = this.onlCgreportHeadService.getSqlParams(paramString1);
       byte b1 = 1;
       for (String str : list1) {
         OnlCgreportItem onlCgreportItem = new OnlCgreportItem();
         onlCgreportItem.setFieldName(str.toLowerCase());
         onlCgreportItem.setFieldTxt(str);
         onlCgreportItem.setIsShow(Integer.valueOf(1));
         onlCgreportItem.setOrderNum(Integer.valueOf(b1));
         onlCgreportItem.setId(bConstant.a7());
         onlCgreportItem.setFieldType("String");
         arrayList.add(onlCgreportItem);
         b1++;
       }
       for (String str : list2) {
         OnlCgreportParam onlCgreportParam = new OnlCgreportParam();
         onlCgreportParam.setParamName(str);
         onlCgreportParam.setParamTxt(str);
         arrayList1.add(onlCgreportParam);
       }
       hashMap.put("fields", arrayList);
       hashMap.put("params", arrayList1);
     } catch (Exception exception) {
       a.error(exception.getMessage(), exception);
       String str = "解析失败，";
       int i = exception.getMessage().indexOf("Connection refused: connect");
       if (i != -1) {
         str = str + "数据源连接失败.";
       } else if (exception.getMessage().indexOf("值可能存在SQL注入风险") != -1) {
         str = str + "SQL可能存在SQL注入风险.";
       } else if (exception.getMessage().indexOf("该报表sql没有数据") != -1) {
         str = str + "报表sql查询数据为空，无法解析字段.";
       } else if (exception.getMessage().indexOf("SqlServer不支持SQL内排序") != -1) {
         str = str + "SqlServer不支持SQL内排序.";
       } else {
         str = str + "SQL语法错误.";
       }
       return Result.error(str);
     }
     return Result.ok(hashMap);
   }

   @GetMapping({"/list"})
   public Result<IPage<OnlCgreportHead>> a(OnlCgreportHead paramOnlCgreportHead, @RequestParam(name = "pageNo", defaultValue = "1") Integer paramInteger1, @RequestParam(name = "pageSize", defaultValue = "10") Integer paramInteger2, HttpServletRequest paramHttpServletRequest) {
     Result<IPage<OnlCgreportHead>> result = new Result();
     QueryWrapper queryWrapper = QueryGenerator.initQueryWrapper(paramOnlCgreportHead, paramHttpServletRequest.getParameterMap());
     Page page = new Page(paramInteger1.intValue(), paramInteger2.intValue());
     IPage iPage = this.onlCgreportHeadService.page((IPage)page, (Wrapper)queryWrapper);
     result.setSuccess(true);
     result.setResult(iPage);
     return result;
   }

   @AutoLowApp(action = LowAppAopEnum.ADD, bizType = "cgreport")
   @PostMapping({"/add"})
   public Result<?> a(@RequestBody OnlCgreportModel paramOnlCgreportModel) {
     Result<?> result = new Result();
     try {
       String str = bConstant.a7();
       OnlCgreportHead onlCgreportHead = paramOnlCgreportModel.getHead();
       List list1 = paramOnlCgreportModel.getParams();
       List list2 = paramOnlCgreportModel.getItems();
       onlCgreportHead.setId(str);
       for (OnlCgreportParam onlCgreportParam : list1) {
         onlCgreportParam.setId(null);
         onlCgreportParam.setCgrheadId(str);
       }
       for (OnlCgreportItem onlCgreportItem : list2) {
         onlCgreportItem.setId(null);
         onlCgreportItem.setFieldName(onlCgreportItem.getFieldName().trim().toLowerCase());
         onlCgreportItem.setCgrheadId(str);
       }
       this.onlCgreportHeadService.save(onlCgreportHead);
       this.onlCgreportParamService.saveBatch(list1);
       this.onlCgreportItemService.saveBatch(list2);
       result.success("添加成功！");
     } catch (Exception exception) {
       a.error(exception.getMessage(), exception);
       result.error500("操作失败");
     }
     return result;
   }

   @PutMapping({"/editAll"})
   @CacheEvict(value = {"sys:cache:online:rp"}, allEntries = true, beforeInvocation = true)
   public Result<?> b(@RequestBody OnlCgreportModel paramOnlCgreportModel) {
     try {
       return this.onlCgreportHeadService.editAll(paramOnlCgreportModel);
     } catch (Exception exception) {
       a.error(exception.getMessage(), exception);
       return Result.error("操作失败");
     }
   }

   @AutoLowApp(action = LowAppAopEnum.DELETE, bizType = "cgreport")
   @DeleteMapping({"/delete"})
   public Result<?> a(@RequestParam(name = "id", required = true) String paramString) {
     return this.onlCgreportHeadService.delete(paramString);
   }

   @AutoLowApp(action = LowAppAopEnum.DELETE, bizType = "cgreport")
   @DeleteMapping({"/deleteBatch"})
   public Result<?> b(@RequestParam(name = "ids", required = true) String paramString) {
     return this.onlCgreportHeadService.bathDelete(paramString.split(","));
   }

   @GetMapping({"/queryById"})
   public Result<OnlCgreportHead> c(@RequestParam(name = "id", required = true) String paramString) {
     Result<OnlCgreportHead> result = new Result();
     OnlCgreportHead onlCgreportHead = (OnlCgreportHead)this.onlCgreportHeadService.getById(paramString);
     result.setResult(onlCgreportHead);
     return result;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\a\b.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
