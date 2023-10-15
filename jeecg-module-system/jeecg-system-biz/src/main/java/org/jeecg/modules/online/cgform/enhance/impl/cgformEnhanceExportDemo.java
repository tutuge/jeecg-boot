 package org.jeecg.modules.online.cgform.enhance.impl;

 import java.util.List;
 import java.util.Map;
 import java.util.stream.Collectors;
 import org.jeecg.common.system.api.ISysBaseAPI;
 import org.jeecg.common.system.vo.SysCategoryModel;
 import org.jeecg.common.util.oConvertUtils;
 import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaListInter;
 import org.jeecg.modules.online.cgform.entity.OnlCgformField;
 import org.jeecg.modules.online.cgform.service.IOnlCgformFieldService;
 import org.jeecg.modules.online.config.exception.BusinessException;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Lazy;
 import org.springframework.stereotype.Component;

 @Component("cgformEnhanceExportDemo")
 public class cgformEnhanceExportDemo implements CgformEnhanceJavaListInter {
   private static final Logger a = LoggerFactory.getLogger(cgformEnhanceExportDemo.class);

   @Lazy
   @Autowired
   ISysBaseAPI sysBaseAPI;

   @Autowired
   IOnlCgformFieldService onlCgformFieldService;

   public void execute(String tableName, List<Map<String, Object>> data) throws BusinessException {
     List<SysCategoryModel> list = this.sysBaseAPI.queryAllSysCategory();
     for (Map<String, Object> map : data) {
       String str1 = oConvertUtils.getString(map.get("fen_tree"));
       if (oConvertUtils.isEmpty(str1))
         continue;
       List<SysCategoryModel> list1 = list.stream().filter(paramSysCategoryModel ->
               paramSysCategoryModel.getId().equals(str1)).collect(Collectors.toList());
       if (list1 != null && list1.size() != 0)
         map.put("fen_tree", ((SysCategoryModel)list1.get(0)).getName());
       String str2 = oConvertUtils.getString(map.get("sel_search"));
       if (oConvertUtils.isEmpty(str2))
         continue;
       OnlCgformField onlCgformField = this.onlCgformFieldService.queryFormFieldByTableNameAndField(tableName, "sel_search");
       if (onlCgformField == null || oConvertUtils.isEmpty(onlCgformField.getDictTable()))
         continue;
       List<String> list2 = this.sysBaseAPI.queryTableDictByKeys(onlCgformField.getDictTable(), onlCgformField.getDictText(), onlCgformField.getDictField(), new String[] { str2 });
       if (list2 != null && list2.size() > 0)
         map.put("sel_search", list2.get(0));
     }
   }
 }
